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
package eu.mamakis.conceptualflow;

//import eu.mamakis.conceptualflow.greek.GreekSpecialTone;
import eu.mamakis.conceptualflow.util.StringArrayUtils;
import java.util.Map.Entry;
import java.util.*;

/**
 * The main document class for Document summarization implementing the GUTS
 * algorithm in Greek language. The public methods are summarize(), and 3 statistics specific methods
 * while the  document to be summarized, is provided at Constructor invocation along with
 * the percentage of required summary.
 *
 *
 * @author Yorgos Mamakis
 */
public class Document {

    private String text;
    private List<Paragraph> paragraphs;
    private Map<Integer, Sentence> sentences;
    private Matrix matrix;
    private float percentage;
    private final float VERY_BIG_NUMBER = 1000000;
    private SentenceCreator sentenceCreator;
    private List<Sentence> summ;
    /**
     * Constructor
     *
     * @param txt The input text
     * @param percentage The percentage of summary required
     */
    public Document(String txt, float percentage, SentenceCreator sentenceCreator) {


       

        this.percentage = percentage;

        this.sentenceCreator = sentenceCreator;
        
        this.text = txt;
        //Split into paragraphs
        paragraphs = splitIntoParagraphs(text);

        //Split the paragraphs to sentences
        sentences = splitIntoSentences(paragraphs);

        //Calculate word occurrence
        matrix = computeDiagonal(sentences);
        //Calcualte sentence weights based on word occurrence
        calculateSentenceWeights(matrix, sentences);
        //Create the word relations
        matrix = calculateOccurences(matrix, sentences);

    }
/**
 * Summarization method
 * @return Summary
 */
    public String summarize() {
        int limit = Math.round(sentences.size() * percentage);
        limit = limit<1?limit+1:limit;
        //Find links between consecutive sentences
        List<Link> links = computIntraDocumentLinks();
        //Calculate the similarity threshold
        final float similarityThreshold = getThreshold(links);
        //Cluster the sentences
        List<Cluster> clusters = getClusters(links, similarityThreshold);
        //Create the first summary
        List<Sentence> firstSummary = produceSummary(clusters, percentage);
        //Store it temporarily
        summ = firstSummary;
        
        //If the summary is bigger than expected
        if (firstSummary.size() > limit) {
            //Compute cross document links
            
            List<Link> linkSecondStep = computeInterDocumentLinks(firstSummary);
            
            //cluster
            List<Cluster> clustersSecondStep = getSecondClusters(linkSecondStep, similarityThreshold, summ);
            
            //Produce second summary
            List<Sentence> secondSummary = produceSummary(clustersSecondStep, percentage);
            
            //Replace the first summary with the second
            summ = secondSummary;
            
            // If still the required summary is not reached go by the word weight
            if (secondSummary.size() > limit) {
                Collections.sort(secondSummary, new SentenceWeightComparator());
                List<Sentence> finalSummary = secondSummary.subList(0, limit);
                Collections.sort(finalSummary, new SentenceByIndexComparator());
                summ = finalSummary;
            }

        }

        return getSummary(summ, paragraphs);
    }

    /**
     * Create the Paragraphs of the document which hold the original sentences
     *
     * @param text The document to be summarized
     * @return The list of paragraphs
     */
    private List<Paragraph> splitIntoParagraphs(String text) {
        
        List<Paragraph> pars = new ArrayList<Paragraph>();
        String[] paragraphArr = text.split("\\. \n");
        int i = 0;
        for (String paragraph : paragraphArr) {
            Paragraph par = new Paragraph();
            par.setIndex(i);
            par.setSentences(StringArrayUtils.toStringList(sentenceCreator.splitIntoSentences(paragraph)));
            pars.add(par);
        }
        return pars;
    }

    /**
     * Creates the sentence list with the stems of the words from each paragraph
     *
     * @param paragraphs The paragraph list to be stemmed
     * @return A list of sentences that hold word stems per sentence
     */
    private Map<Integer, Sentence> splitIntoSentences(List<Paragraph> paragraphs) {
        Map<Integer, Sentence> sents = new TreeMap<Integer, Sentence>();
        int i = 0;
        
        for (Paragraph par : paragraphs) {
            Map<Integer, Sentence> sentTemp = sentenceCreator.split(par);
           
            for (Sentence sent : sentTemp.values()) {
                sent.setGlobalIndex(i);
                sents.put(i, sent);
                i++;
            }
           
        }
       
        return sents;
    }

    /**
     * Calculate the word occurrence in the text
     *
     * @param sentences
     * @return
     */
    private Matrix computeDiagonal(Map<Integer, Sentence> sentences) {
        Map<String, Float> matrixMap = new HashMap<String, Float>();
        for (Sentence sentence : sentences.values()) {
            List<String> words = sentence.getWords();
            for (String word : words) {
                if (matrixMap.get(word) != null) {
                    matrixMap.put(word, matrixMap.get(word) + 1);
                } else {
                    matrixMap.put(word, 1f);
                }
            }
        }

        return sort(matrixMap);
    }

    /**
     * Sort the word occurrence map descending
     *
     * @param matrixMap
     * @return
     */
    private Matrix sort(Map<String, Float> matrixMap) {
        List<Word> words = new ArrayList<Word>();
        for (Entry<String, Float> entry : matrixMap.entrySet()) {
            words.add(new Word(entry.getKey(), entry.getValue()));
        }

        Collections.sort(words);
        Matrix mat = new Matrix();
        String[] wordStrings = new String[words.size()];
        Float[] occurrences = new Float[words.size()];
        int i = 0;
        for (Word word : words) {
            wordStrings[i] = word.getWord();
            occurrences[i] = word.getOccurrences();
            i++;
        }
        mat.setDiagonal(occurrences);
        mat.setWords(wordStrings);
        return mat;
    }

    /**
     * Calculate the biased co-occurrence of words in sentences
     *
     * @param mat The matrix that holds the word
     * @param sents The sentences
     * @return The complete matrix to be used in summarization
     */
    private Matrix calculateOccurences(Matrix mat, Map<Integer, Sentence> sents) {
        int docSize = sents.size();
        Map<Integer, Cell> cellList = new HashMap<Integer, Cell>();

        for (int i = 0; i < mat.getWords().length - 1; i++) {
            String wordA = mat.getWords()[i];
            for (int k = i + 1; k < mat.getWords().length; k++) {
                float co_occurrence = 0;
                String wordB = mat.getWords()[k];
                Cell cell = new Cell();
                for (Sentence sent : sents.values()) {
                    if (sent.getWords().contains(wordA) && sent.getWords().contains(wordB)) {
                        co_occurrence++;
                    }
                }
                cell.setColumn(k);
                cell.setRow(i);
                float expectedCooccurrence = calculateExpectedCoOccurrence(mat.getDiagonal()[i], mat.getDiagonal()[k], docSize);
                float observedCooccurrence = calculateObservedCoOccurrence(co_occurrence, docSize);
                if (expectedCooccurrence < observedCooccurrence) {
                    cell.setSimilarity(observedCooccurrence - expectedCooccurrence);
                } else {
                    cell.setSimilarity(0f);
                }
                cellList.put((mat.getDiagonal().length - 1) * i + k, cell);

            }
        }
        mat.setCells(cellList);
        return mat;
    }

    /**
     * Calculate the Expected Co-occurrence
     *
     * @param occA WordA occurrence
     * @param occB WordB occurrence
     * @param docSize Document size
     * @return The exected co-occurrence
     */
    private float calculateExpectedCoOccurrence(Float occA, Float occB, int docSize) {
        return (float) ((float) occA * (float) occB) / (float) docSize * ((float) occA + (float) occB);
    }

    /**
     * Calculate the Normalized Observed Co-occurrence
     *
     * @param co_occurrence The co_occurrence of words a and b
     * @param docSize The document size
     * @return The normalized observed co_occurrence
     */
    private float calculateObservedCoOccurrence(Float co_occurrence, int docSize) {
        return (float) ((float) co_occurrence / (float) docSize);
    }

    /**
     * Calculate sentence weights
     *
     * @param mat The matrix to get the word occurrence from
     * @param sents The sentences
     */
    private void calculateSentenceWeights(Matrix mat, Map<Integer, Sentence> sents) {
        int i = 0;

        for (Entry<Integer, Sentence> sentEntry : sents.entrySet()) {
            Sentence sent = sentEntry.getValue();
            float weight = 0;
            for (String word : sent.getWords()) {
                float tmp = mat.getDiagonal()[mat.getWordIndex(word)];
                if (tmp > 1) {
                    weight += tmp;
                }
            }
            sent.setWeight(sent.getMultiplier() * (weight / sent.getWords().size()));

            sents.put(i, sent);
            i++;
        }

    }

    /**
     * Compute the links between consecutive sentences
     *
     * @return A List of weighted Jaccard similarities between consecutive
     * sentences
     */
    private List<Link> computIntraDocumentLinks() {
        List<Link> links = new ArrayList<Link>();
        for (int i = 0; i < sentences.size() - 1; i++) {
            float similarity = 0f;
            Sentence sentA = sentences.get(i);
            Sentence sentB = sentences.get(i + 1);

            for (String wordA : sentA.getWords()) {
                for (String wordB : sentB.getWords()) {
                    similarity += matrix.getCellValue(matrix.getWordIndex(wordA), matrix.getWordIndex(wordB));
                }
            }

            Link link = new Link();
            link.setIndexA(i);
            link.setIndexB(i + 1);
            link.setSimilarity(similarity / (sentA.getWords().size() + sentB.getWords().size()));
            links.add(link);
        }
        return links;
    }

    /**
     * Calculate the second summary based on the first
     *
     * @param firstSummary
     * @return
     */
    private List<Link> computeInterDocumentLinks(List<Sentence> firstSummary) {
        List<Link> links = new ArrayList<Link>();
        for (int i = 0; i < firstSummary.size() - 1; i++) {
            Sentence sentA = sentences.get(i);
            for (int k = i + 1; k < firstSummary.size(); k = k + 1) {
                float similarity = 0f;

                Sentence sentB = sentences.get(k);

                for (String wordA : sentA.getWords()) {
                    for (String wordB : sentB.getWords()) {
                        similarity += matrix.getCellValue(matrix.getWordIndex(wordA), matrix.getWordIndex(wordB));
                    }
                }

                Link link = new Link();
                link.setIndexA(i);
                link.setIndexB(k);
                link.setSimilarity(similarity / (sentA.getWords().size() + sentB.getWords().size()));
                links.add(link);
            }
        }
        return links;
    }

    /**
     * Compute the Similarity Threshold
     *
     * @param links The list of sentence links
     * @return The similarity threshold
     */
    private float getThreshold(List<Link> links) {
        float avg = 0;
        float minimaFound = 0;
        for (int i = 0; i < links.size() - 1; i++) {

            Link link = links.get(i);
            if (i == 0) {
                if (link.getSimilarity() < VERY_BIG_NUMBER && link.getSimilarity() < links.get(i + 1).getSimilarity()) {
                    avg += link.getSimilarity();
                    minimaFound++;
                }

            } else {
                if (link.getSimilarity() < links.get(i - 1).getSimilarity() && link.getSimilarity() < links.get(i + 1).getSimilarity()) {
                    avg += link.getSimilarity();
                    minimaFound++;
                }
            }
        }
        return avg / minimaFound;
    }

    /**
     * Cluster the document sentences according to the links of sentences and
     * the computed threshold
     *
     * @param links
     * @param similarityThreshold
     * @return
     */
    private List<Cluster> getClusters(List<Link> links, float similarityThreshold) {
        List<Cluster> clusters = new ArrayList<Cluster>();
        List<Sentence> sents = new ArrayList<Sentence>();
        sents.add(getSentenceByGlobalIndex(links.get(0).getIndexA()));
        for (Link link : links) {

            if (link.getSimilarity() < similarityThreshold) {
                Cluster cluster = new Cluster(sents);
                clusters.add(cluster);
                sents = new ArrayList<Sentence>();
            }
            sents.add(getSentenceByGlobalIndex(link.getIndexB()));
        }
        Cluster cluster = new Cluster(sents);
        clusters.add(cluster);
        return clusters;
    }

    private List<Cluster> getSecondClusters(List<Link> links, float similarityThreshold, List<Sentence> summ) {
        List<Cluster> clusters = new ArrayList<Cluster>();
        
        //Sentence, state
        Map<Integer, Boolean> state = new HashMap<Integer, Boolean>();
        for (Sentence sentence : summ) {
            if (!state.containsKey(sentence.getGlobalIndex())) {
                List<Sentence> sents = new ArrayList<Sentence>();
                List<Link> linkslocal = getLinksByIndex(links, sentence.getGlobalIndex());
                state.put(sentence.getGlobalIndex(), true);
                sents.add(sentence);
                for (Link link : linkslocal) {
                    if (link.getSimilarity() > similarityThreshold&& !state.containsKey(link.getIndexB())) {
                       sents.add(getSentenceByGlobalIndex(link.getIndexB()));
                       state.put(link.getIndexB(),true);
                    }
                }
                Cluster cluster = new Cluster(sents);
                clusters.add(cluster);
            }
        }

        return clusters;
    }

    //Get the sentence by position
    private Sentence getSentenceByGlobalIndex(int index) {
        return sentences.get(index);
    }

    /**
     * Produce the a summary from a pre-clustered document
     *
     * @param clusters
     * @param percentage
     * @return
     */
    private List<Sentence> produceSummary(List<Cluster> clusters, float percentage) {
        List<Sentence> summarySentences = new ArrayList<Sentence>();
        for (Cluster cluster : clusters) {
            //If the cluster has one sentence keep it
            if (cluster.getSentences().size() == 1) {
                summarySentences.add(cluster.getSentences().get(0));
            } //if the cluster sentences times percentage <1 keep the most importan sentence
            else if (cluster.getSentences().size() * percentage < 1) {
                Sentence retr = new Sentence();
                retr.setWeight(0);
                for (Sentence sent : cluster.getSentences()) {
                    if (sent.getWeight() > retr.getWeight()) {
                        retr = sent;
                    }
                }
                summarySentences.add(retr);
            } //else keep the required sentences
            else {
                int nOfSentences = Math.round(cluster.getSentences().size() * percentage);
                Collections.sort(cluster.getSentences(), new SentenceWeightComparator());
                summarySentences.addAll(cluster.getSentences().subList(0, nOfSentences));

            }
        }
        Collections.sort(summarySentences, new SentenceByIndexComparator());
        return summarySentences;
    }

    /**
     * Create the final summary from a list of paragraphs and the important
     * sentences
     *
     * @param summ
     * @param paragraphs
     * @return
     */
    private String getSummary(List<Sentence> summ, List<Paragraph> paragraphs) {
        StringBuilder sb = new StringBuilder();
        List<String> doc = new ArrayList<String>();
        for (Paragraph par : paragraphs) {
            doc.addAll(par.getSentences());
        }
        for (Sentence sent : summ) {
            sb.append(doc.get(sent.getGlobalIndex()));
            sb.append(". ");
        }
        return sb.toString();
    }

    private List<Link> getLinksByIndex(List<Link> links, int globalIndex) {
        List<Link> retLinks = new ArrayList<Link>();
        for (Link link : links) {
            if (link.getIndexA() == globalIndex) {
                retLinks.add(link);
            }
        }
        return retLinks;
    }

    /**
     * 
     * @return  The # of sentences of the summary
     */
    public int getSummarySentencesSize(){
        return summ.size();
    }
    
        /**
     * 
     * @return  The # of sentences of the original document
     */
    public int getOriginalSentencesSize(){
        return sentences.size();
    }
    
    /**
     * Retrieves the identified sentences of the document in a convenient form for segmentation identification
     * @return 
     */
    public String getSentences(){
        StringBuilder sb = new StringBuilder();
        int i=1;
        for(Paragraph par:paragraphs){
            for(String sentence:par.getSentences()){
                sb.append("Sentence ");
                sb.append(i);
                sb.append(": ");
                sb.append(sentence);
                sb.append("<br>");
                i++;
            }
            
        }
        return sb.toString();
    }
    /**
     * Natural sorting comparator
     */
    private class SentenceByIndexComparator implements Comparator<Sentence> {

        @Override
        public int compare(Sentence o1, Sentence o2) {
            return o1.getGlobalIndex() - o2.getGlobalIndex();
        }
    }

    /**
     * Comparator by sentence weight in reverse natural ordering
     */
    private class SentenceWeightComparator implements Comparator<Sentence> {

        @Override
        public int compare(Sentence o1, Sentence o2) {
            float w1 = o1.getWeight();
            float w2 = o2.getWeight();
            if (w1 > w2) {
                return -1;
            } else if (w1 == w2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    
    private class SentSort implements Comparable<SentSort>{
        
        private Integer index;
        private Sentence sentence;
        
        public SentSort(Integer index,Sentence sentence){
            this.index = index;
            this.sentence = sentence;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Sentence getSentence() {
            return sentence;
        }

        public void setSentence(Sentence sentence) {
            this.sentence = sentence;
        }
        
        @Override
        public int compareTo(SentSort o) {
            if (this.getIndex() > o.getIndex()) {
                return 1;
            } else if (this.getIndex() < o.getIndex()){
                return -1;
            }
            return 0;
        }
    }
    /**
     * Helper class to create the matrix occurrence diagonal
     */
    private class Word implements Comparable<Word> {

        private String word;
        private Float occurrences;

        public Word(String word, Float occurrences) {
            this.word = word;
            this.occurrences = occurrences;
        }
    
    /**
     * Set the number of word occurrence
     * @param occurrences The number of word occurrence
     */
        public Float getOccurrences() {
            return occurrences;
        }

        public void setOccurrences(Float occurrences) {
            this.occurrences = occurrences;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        /**
         * This class implements the reverse natural order
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(Word o) {
            if (o.getOccurrences() > this.occurrences) {
                return 1;
            } else if (o.getOccurrences() < this.occurrences) {
                return -1;
            }
            return 0;
        }
    }
}
