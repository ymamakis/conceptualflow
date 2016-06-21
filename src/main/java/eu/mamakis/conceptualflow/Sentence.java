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

import java.util.List;

/**
 * Sentence bean
 * @author Yorgos Mamakis
 */
public class Sentence {
    

    private List<String> words;
    
    private int localIndex;
    
    private int globalIndex;
    
    private float multiplier;
    
    private float weight;
    

    /**
     * Get the weight of the sentence
     * @return The weight of the sentence
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the sentence
     * @param weight The weight of the sentence
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Gets the position of the sentence in the document
     * @return The position of the sentence in the document
     */
    public int getGlobalIndex() {
        return globalIndex;
    }

    /**
     * Sets the position of the sentence in the document
     * @param globalIndex The position of the sentence in the document
     */
    public void setGlobalIndex(int globalIndex) {
        this.globalIndex = globalIndex;
    }

    /**
     * Gets the position of the sentence in the paragraph
     * @return The position of the sentence in the paragraph
     */
    public int getLocalIndex() {
        return localIndex;
    }

       /**
        * Get the stems of nouns that make up the sentence
        * @return The stems of nouns of the sentence
        */
    public List<String> getWords() {
        return words;
    }

    /**
     * Sets the stems of nouns that make up the sentence
     * @param words The stem of nouns of the sentence
     */
    public void setWords(List<String> words) {
        this.words = words;
    }

    /**
     * Sets the position of the sentence in the paragraph
     * @param localIndex The position of the sentence in the paragraph
     */
    public void setLocalIndex(int localIndex) {
        this.localIndex = localIndex;
    }

    /**
     * Gets the multiplier of the sentence according to its position in the paragraph
     * @return The multiplier of the sentence
     */
    public float getMultiplier() {
        return multiplier;
    }

    /**
     * Sets the multiplier of the sentence according to its position in the paragraph
     * @param multiplier The multiplier of the sentence
     */
    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    
}
