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

import eu.mamakis.conceptualflow.exceptions.WordNotFoundException;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;

/**
 * The abstract semantic matrix. The diagonal holds the sorted word occurrence, 
 * while the cells the abstract semantic relations between words
 * @author Yorgos Mamakis
 */
public class Matrix {
    
    
    private String[] words;
    
    
    private Float[] diagonal;
    
    
    private Map<Integer,Cell> cells;

    /**
     * Get similarities of different words
     * @return The similarities of different words
     */
    protected Map<Integer,Cell> getCells() {
        return cells;
    }

    /**
     * Set the similarities of different words
     * @param cells The similarities of different words
     */
    protected void setCells(Map<Integer,Cell> cells) {
        this.cells = cells;
    }

    /**
     * Get the sorted word occurrence
     * @return The word occurrence
     */
    protected Float[] getDiagonal() {
        return diagonal;
    }

    /**
     * Sets the sorted word occurrence
     * @param diagonal The word occurrence
     */
    protected void setDiagonal(Float[] diagonal) {
        this.diagonal = diagonal;
    }
    /**
     * Get the unique stem of words sorted by occurrence
     * @return The stem of words
     */
    protected String[] getWords() {
        return words;
    }

    /**
     * Sets the unique stem of words sorted by occurrence
     * @param words The stem of words
     */
    protected void setWords(String[] words) {
        this.words = words;
    }
 
    /**
     * Retrieves the location of the word in the array of words
     * @param word The word to search for
     * @return The word location in the array
     * @throws An exception if the word is not found
     */
    protected int getWordIndex(String word) throws WordNotFoundException{
        int i=0;
        for(String str:words){
            if(StringUtils.equals(word, str)){
                return i;
            }
            i++;
        }
        throw new WordNotFoundException (word);
    }
    
    /**
     * Gets the link relation between two words. Since the word matrix only holds values above the diagonal (indexA< indexB)
     * this is indexed in the map of cells as: (no_of_words-1)*position_of_first_word+position_of_second_word.
     * Thus the co-occurrence of word 20 and word 25 in 30 identified words is indexed in the 29*20+25=605th position of
     * the Matrix.
     * @param indexA The position of word A in the semantic matrix
     * @param indexB The position of word B in the semantic matrix
     * @return The weighted word relation;
     */
    protected float getCellValue(int indexA, int indexB){
        if (indexA == indexB){
            return diagonal[indexA];
        }
        if (indexA>indexB){
            int tmp = indexA;
            indexA=indexB;
            indexB=tmp;
        }
        
        return cells.get((words.length-1)*indexA+indexB).getSimilarity();
        
        
    }
    
    /**
     * Formatted relation string
     * @return A string representation of the values of word links
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("IndexA|IndexB|Similarity|\n");
        for(Entry <Integer,Cell> entry:cells.entrySet()){
            sb.append(entry.getValue().getRow());
            sb.append("|");
            sb.append(entry.getValue().getColumn());
            sb.append("|");
            sb.append(entry.getValue().getSimilarity());
            sb.append(("\n"));
        }
        return sb.toString();
    }
}
