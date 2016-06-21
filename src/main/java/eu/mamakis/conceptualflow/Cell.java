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
 * The basic unit of calculation holding the relation between 2 words. 
 * Words are always ordered by number of appearances and not by order of appearance
 * 
 * @author Yorgos Mamakis
 */
public class Cell {
    
   
    private int row;
    
    
    private int column;
    
  
    private float similarity;

    /**
     * Get the index of the second word in the comparison. 
     * @return The index in the matrix of the least popular word
     */
    protected int getColumn() {
        return column;
    }

    /**
     * Setter of the least popular word index
     * @param column The index of the least popular word
     */
    protected void setColumn(int column) {
        this.column = column;
    }

    /**
     * Get the index of the most popular word
     * @return The index of the most popular word
     */
    public int getRow() {
        return row;
    }

    /**
     * Set the index of the most popular word
     * @param row The index of the most popular word
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the calculated similarity of two (different) words 
     * @return The similarity of the two words
     */
    protected float getSimilarity() {
        return similarity;
    }

    /**
     * Sets the similarity of the two words
     * @param similarity The similarity of the two words
     */
    protected void setSimilarity(float similarity) {
        this.similarity = similarity;
    }
   
}
