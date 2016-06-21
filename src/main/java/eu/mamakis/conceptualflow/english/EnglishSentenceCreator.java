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
package eu.mamakis.conceptualflow.english;

import eu.mamakis.conceptualflow.Paragraph;
import eu.mamakis.conceptualflow.Sentence;
import eu.mamakis.conceptualflow.SentenceCreator;
import eu.mamakis.conceptualflow.greek.SentenceDetector;
import eu.mamakis.conceptualflow.util.StringArrayUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * English implementation of GUTS using POS tagging and Porter stemming.
 * POSTagging is handled by OpenNLP, while PorterStemming by the sample Porter
 * Stemmer provided at http://tartarus.org/martin/PorterStemmer/java.txt
 *
 * @author Yorgos Mamakis
 */
public class EnglishSentenceCreator implements SentenceCreator {

    /**
     *
     * @see SentenceCreator
     *
     */
    String[] sents;

    public Map<Integer, Sentence> split(Paragraph par) {
        try {
            if (sents == null) {
                SentenceModel sModel = new SentenceModel(Thread.currentThread().getContextClassLoader().getResourceAsStream("en-sent.bin"));
                SentenceDetectorME sentenceDetector = new SentenceDetectorME(sModel);
                sents = sentenceDetector.sentDetect(par.getText());
            }
            Map<Integer, String> sentences = StringArrayUtils.toMap(sents);
            return getNouns(sentences);
        } catch (IOException ex) {
            Logger.getLogger(EnglishSentenceCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Map<Integer, Sentence> getNouns(Map<Integer, String> sentences) {

        try {

            POSModel model = new POSModel(Thread.currentThread().getContextClassLoader().getResourceAsStream("en-pos-maxent.bin"));
            POSTaggerME tagger = new POSTaggerME(model);
            int i = 0;
            Map<Integer, Sentence> sentsMap = new TreeMap<Integer, Sentence>();
            for (String sentence : sentences.values()) {
                Sentence sent = new Sentence();
                String[] words = sentence.split(" ");
                String[] pos = tagger.tag(words);
                List<String> sentenceWords = new ArrayList<String>();
                for (String post : pos) {
                    if (post.contains("NN")) {
                        sentenceWords.add(post);
                    }

                }
                List<String> finalString = new ArrayList<String>();
                for (String noun : sentenceWords) {
                    PorterStemmer stemmer = new PorterStemmer();
                    stemmer.add(noun.toCharArray(), noun.toCharArray().length);
                    finalString.add(stemmer.toString());
                }
                sent.setWords(finalString);
                sent.setMultiplier(calculateMultiplier(i, sentences.size()));
                sent.setWeight(0f);
                sentsMap.put(i, sent);
                sent.setLocalIndex(i);
                i++;
            }
            return sentsMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private float calculateMultiplier(float index, float maxSize) {
        return (float) (maxSize - index) / maxSize;
    }

    @Override
    public String[] splitIntoSentences(String text) {
        if (sents == null) {
            try {
                SentenceModel sModel = new SentenceModel(Thread.currentThread().getContextClassLoader().getResourceAsStream("en-sent.bin"));
                SentenceDetectorME sentenceDetector = new SentenceDetectorME(sModel);
                sents = sentenceDetector.sentDetect(text);
            } catch (IOException ex) {
                Logger.getLogger(EnglishSentenceCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sents;
    }
}
