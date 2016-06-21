/*Copyright 2012 Yorgos Mamakis
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 * 
 */
package eu.mamakis.conceptualflow.greek;

import eu.mamakis.conceptualflow.Paragraph;
import eu.mamakis.conceptualflow.Sentence;
import eu.mamakis.conceptualflow.SentenceCreator;
import eu.mamakis.conceptualflow.greek.enums.Marks;
import eu.mamakis.conceptualflow.util.StringArrayUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;

/**
 * SenceCreator class, supporting one public method (split()) a Paragraph bean;
 *
 * @author Yorgos Mamakis
 */
public class GreekSentenceCreator implements SentenceCreator {

    String[] sents;

    /**
     * Greek grammatic implementation for the sentence creator.
     *
     * @see SentenceCreator
     */
    public Map<Integer, Sentence> split(Paragraph par) {
        //Sentence detection here
        if (sents == null) {
            sents = SentenceDetector.findSentences(repairMarks(par.getText()));
        }
        Map<Integer, String> sentences = StringArrayUtils.toMap(sents);
        return getNouns(sentences);
    }

    @Override
    public String[] splitIntoSentences(String text) {
        if (sents == null) {
            sents = SentenceDetector.findSentences(repairMarks(text));
        }
        return sents;
    }

    /**
     * Method that retrieves the nouns per sentence. It results in a Sentence
     * Vector with all sentences. The requirements are that this method calculates
     * the local index of the sentence in the paragraph and the nouns of the sentence.
     *
     * @param s Total words of each sentence
     * @return nouns of each sentence
     */
    private Map<Integer, Sentence> getNouns(Map<Integer, String> s) {

        Map<Integer, Sentence> temp = stem(s);
        return temp;
    }

    /**
     * Method filtering the nouns of the sentences resulting in a x-size Vector
     * (x representing the number of sentence)
     *
     * @param sentence -- The words between two dots or the start of a document
     * and a dot
     * @return Sentence vector with nouns of all sentences organized in [[word1,
     * word2],[word3, word4]]
     */
    private Map<Integer, Sentence> stem(Map<Integer, String> sentences) {

        Map<Integer, Sentence> sents = new TreeMap<Integer, Sentence>();

        for (int index = 0; index < sentences.size(); index++) {
            Sentence sent = new Sentence();
            String[] wordArray = StringUtils.split(sentences.get(index), " ");
            List<String> sentenceNouns = new ArrayList<String>();
            for (String word : wordArray) {
                String cWord = removePoints(word);
                //TODO update heuristics here as well
                cWord = filter(cWord);
                if (cWord.length() > 0) {
                    if (StringArrayUtils.endsWith(GreekStopWord.NOUN_ENDINGS, cWord)) {
                        sentenceNouns.add(StringArrayUtils.stem(GreekStopWord.NOUN_ENDINGS, cWord));
                    }
                }
            }
            sent.setWords(sentenceNouns);
            sent.setLocalIndex(index);
            sent.setMultiplier(calculateMultiplier(index, sentences.size()));
            sent.setWeight(0f);
            sents.put(index, sent);

        }

        return sents;
    }

    /**
     * Calculate the sentence multiplier according to the localIndex
     *
     * @param index
     * @param maxSize
     * @return
     */
    private float calculateMultiplier(float index, float maxSize) {
        return (float) (maxSize - index) / maxSize;
    }

    /**
     * Method to remove marks from end of words
     *
     * @param myWord -- The word to check for trailing marks
     * @return Stripped off word from marks
     */
    private String removePoints(String myWord) {
        for (Marks mark : Marks.values()) {
            if (StringUtils.endsWith(myWord, mark.toString())) {
                myWord = StringUtils.substringBeforeLast(myWord, mark.toString());
            }
        }
        return myWord;
    }

    /**
     * Eliminate potential non-nouns from document
     *
     * @param myWord -- The word to check
     * @return Either the potential noun or zero-sized string
     */
    private String filter(String myWord) {
        if (StringArrayUtils.contains(GreekStopWord.STOPWORDS, myWord.trim())
                || StringArrayUtils.endsWith(GreekStopWord.VERB_ENDINGS, myWord.trim())) {
            return "";
        }
        return myWord.trim();

    }

    private String repairMarks(String txt) {
        return GreekSpecialTone.repairMarks(txt);
    }
}
