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

import java.util.ArrayList;
import java.util.List;

/**
 *  Sentence Detector Heuristics
 * @author Yorgos Mamakis
 */
public class SentenceDetector {
 
    /**
     * Heuristics for finding sentences
     * @param txt The text to segment
     * @return The identified sentences
     */
    public static String[] findSentences(String txt){
        StringBuilder sb = new StringBuilder();
        String[] words = txt.split(" ");
        String prev="";
        int i=0;
        List<String> sentenceList = new ArrayList<String>();
       for(String word:words){
            if (prev.length()>0){
                if(SentenceMark.containsMark(word)){
                   if(i<words.length-1){
                      if( !SentenceMark.containsMark(words[i+1])){
                          sb.append(word);
                          sb.append(" ");
                          if(prev.length()>1&&word.length()>3){
                            sentenceList.add(sb.toString());
                            sb = new StringBuilder();
                          }
                      } else {
                          sb.append(word);
                          sb.append(" ");
                          prev = word;
                      }
                   } else {
                       sb.append(word);
                       sb.append(" ");
                       sentenceList.add(sb.toString());
                       sb=new StringBuilder();
                   }
                } else {
                    sb.append(word);
                    sb.append(" ");
                    prev=word;
                }
            } else {
                  sb.append(word);
                  sb.append(" ");
                  prev = word;
            }
            
            i++;
        }
        
        
        return sentenceList.toArray(new String[sentenceList.size()]);
    }
    
    
    /**
     * The marks that denote sentences
     */
    private enum SentenceMark{
        
        DOT("."),
        EXCLAMATION("!"),
        QUESTION(";");
        
                
        String ch;
        
        private SentenceMark(String ch){
            this.ch = ch;
        }
        /**
         * Find if a word ends with one of the sentence marks
         * @param word
         * @return 
         */
        public static boolean containsMark(String word){
            for(SentenceMark sMark:SentenceMark.values()){
                if (word.endsWith(sMark.getChar())){
                    return true;
                }
            }
            return false;
        }
        
        public String getChar(){
            return ch;
        }
    }
}
