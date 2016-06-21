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
package eu.mamakis.conceptualflow.util;

import java.util.*;
import org.apache.commons.lang.StringUtils;

/**
 * String Array Utility methods
 * @author Yorgos Mamakis
 */
public class StringArrayUtils {
    
    /**
     * Checks if an array contains a string
     * @param arr The array
     * @param str The string to check
     * @return true if exists, false otherwise
     */
    public static boolean contains(String[] arr, String str){
        for(String arrStr:arr){
            if(StringUtils.equals(arrStr, str)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check if a an array of endings is contained in a String
     * @param arr The array
     * @param str The string to check
     * @return true if one of the endings is contained in the string, false otherwise
     */
    public static boolean endsWith(String[] arr, String str){
            return StringUtils.endsWithAny(str, arr);
    }
    
    /**
     * Converts an array to a List ensuring order
     * @param arr The array to convert
     * @return A linked list representing the array
     */
    public static List<String> toStringList(String[] arr){
       return new ArrayList<String>(Arrays.asList(arr));
    }
    
    /**
     * Converts an array of strings into a sequence of sentences
     * @param arr The array to convert
     * @return The map of sequential sentences
     */
    public static Map<Integer,String> toMap(String[] arr){
        int i=0;
        Map<Integer,String> sents = new TreeMap<Integer,String>();
        for(String str:arr){
            sents.put(i,str);
            i++;
        }
        return sents;
    }
    
    /**
     * Stems a word according to a given array of endings
     * @param arr The array of endings
     * @param str The string to stem
     * @return The word stem
     */
    public static String stem (String[] arr, String str){
        for(String arrStr:arr){
            if(StringUtils.endsWith(str, arrStr)){
                return StringUtils.substringBefore(str, arrStr);
            }
        }
        return str;
    }
}
