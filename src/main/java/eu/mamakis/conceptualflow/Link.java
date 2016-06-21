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

/**
 *  Bean holding the similarity between to sentences
 * @author Yorgos Mamakis
 */
public class Link {
    
    
    private int indexA;
    
    
    private int indexB;
    
    
    private float similarity;

    /**
     * Get the position of the first sentence in the document
     * @return The position of the sentence in the document
     */
    protected int getIndexA() {
        return indexA;
    }

    /**
     * Set the position of the first sentence in the document
     * @param indexA The position of the sentence in the document
     */
    protected void setIndexA(int indexA) {
        this.indexA = indexA;
    }

    /**
     * Get the position of the second sentence in the document
     * @return The position of the second sentence in the document
     */
    protected int getIndexB() {
        return indexB;
    }

    /**
     * Set the position of the second sentence of the link in the document
     * @param indexB The position of the second sentence in the document
     */
    protected void setIndexB(int indexB) {
        this.indexB = indexB;
    }

    /**
     * Get the similarity of the two sentences
     * @return The similarity of the two sentences
     */
    protected float getSimilarity() {
        return similarity;
    }

    /**
     * Set the similarity of the two sentences
     * @param similarity The similarity of the two sentences
     */
    protected void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
    
}
