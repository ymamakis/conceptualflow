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
package eu.mamakis.conceptualflow.greek.enums;

/**
 * Punctuated characters
 * @author Yorgos Mamakis
 */
public enum PunctuatedChars {
    A('Ά','Α'),E('Έ','Ε'),H('Ή','Η'),I('Ί','Ι'),O('Ό','Ο'),Y('Ύ','Υ'),W('Ώ','Ω'), a('ά','α'), e('έ','ε'), h('ή','η'),i('ί','ι'),o('ό','ο'),y('ύ','υ'),w('ώ','ω');
    
    char punctuated;
    char notPunctuated;
    
    private PunctuatedChars(char in,char out){
        this.punctuated =in;
        this.notPunctuated=out;
    }
    
    /**
     * Get the punctuated character
     * @return the punctuated character
     */
    public char getPunctuated(){
        return this.punctuated;
    }
    
    /**
     * Get the un-punctuated character
     * @return the un-punctuated character
     */
    public char getNotPunctuated(){
        return this.notPunctuated;
    }
}
