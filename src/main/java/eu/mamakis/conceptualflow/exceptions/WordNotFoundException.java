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
package eu.mamakis.conceptualflow.exceptions;

/**
 * Exception when a word is not found (should never be thrown)
 * @author Yorgos Mamakis
 */
public class WordNotFoundException extends RuntimeException{
    
    String word;
    /**
     * An exception thrown when a word is not found. Since all words come from the matrix this MUST never happen
     * @param word The word that generated the problem
     */
    public WordNotFoundException(String word){
        super();
        this.word = word;
        
    }
    /**
     * Localized message
     * @return An explanation on which word generated the error
     */
    @Override
    public String getMessage(){
        
        return "Word " + this.word +" was not found in he document matrix. This should never happen.";
    }
}
