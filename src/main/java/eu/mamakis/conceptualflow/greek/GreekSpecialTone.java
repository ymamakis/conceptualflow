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

import eu.mamakis.conceptualflow.greek.enums.PunctuatedChars;
import org.apache.commons.lang.StringUtils;

/**
 * String heuristics
 *
 * @author Yorgos Mamakis
 */
public class GreekSpecialTone {

    /**
     * Enumeration for uniform token removal
     */
    private enum RemovalToken {

        EXCPAR("!)") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, _3DOTS("...") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        },
        COMMA(",") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        },
        HYPHENL("«") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, HYPHENR("»") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, DASH("-") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, PARL("(") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, PARR(")") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, DQUOTES("\"") {

            @Override
            public String replace(String txt) {
                return replaceWithNothing(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        },
        SEMICOLON(";") {

            @Override
            public String replace(String txt) {
                return replaceWithDot(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        },
        DDOT("..") {

            @Override
            public String replace(String txt) {
                return replaceWithDot(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, EXC("!") {

            @Override
            public String replace(String txt) {
                return replaceWithDot(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, DDOSPC(". .") {

            @Override
            public String replace(String txt) {
                return txt.replace(getToken(), ". ");
            }

            public String getToken() {
                return this.token;
            }
        },
        BC("π.Χ.") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, AD("μ.Χ.") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, MIL("εκατ.") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, MI("εκ.") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, BIL("δισ.") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, COS(" κος. ") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, C(" κ. ") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, CC(" κκ. ") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, CA(" κα. ") {

            @Override
            public String replace(String txt) {
                return removeAllDots(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, DENTER("\n\n") {

            @Override
            public String replace(String txt) {
                return replaceWithNewLine(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        }, DENTERSPACE("\n \n") {

            @Override
            public String replace(String txt) {
                return replaceWithNewLine(txt, getToken());
            }

            public String getToken() {
                return this.token;
            }
        };
        String token;
        static final String NOTHING = "";
        static final String DOT = ".";
        static final String NL = "\n";

        private RemovalToken(String token) {
            this.token = token;
        }

        /**
         * Replace the input text with the correct token
         *
         * @param txt The text to replace
         * @return The text after replacement
         */
        public abstract String replace(String txt);

        /**
         * Method returning the token.
         *
         * @return the token
         */
        public abstract String getToken();

        
        private static String replaceWithNothing(String txt, String token) {
            return StringUtils.replace(txt, token, NOTHING);
        }

        private static String replaceWithDot(String txt, String token) {
            return StringUtils.replace(txt, token, DOT);
        }

        private static String removeAllDots(String txt, String token) {
            return StringUtils.replace(txt, token, StringUtils.replace(token, DOT, NOTHING));
        }

        private static String replaceWithNewLine(String txt, String token) {
            return StringUtils.replace(txt, token, NL);
        }
    }

    /**
     * Normalize the input string
     * @param input - the input String
     * @return the normalized version of the string
     */
    public static String repairMarks(String input) {
        String output = "";

        for(PunctuatedChars  pChar : PunctuatedChars.values()){
           input = input.replace(pChar.getPunctuated(),pChar.getNotPunctuated());
        }
        for (RemovalToken token : RemovalToken.values()) {
            input = token.replace(input);
        }

        String[] words = StringUtils.split(input, " ");
        

        for(String word:words) {
            boolean ptr = false;
            if (word.contains(".")) {
                if (word.endsWith(".")) {
                    if (word.length() == 2 && !Character.isDigit(word.charAt(0))) {
                        word= word.substring(0, 1);
                        ptr = true;
                    }
                }
                if (!ptr) {
                    String[] dotremove = word.split("\\.");
                    int k = 0;
                    boolean ptr2 = true;
                    if (dotremove.length > 1) {

                        while (k < dotremove.length - 1) {
                            try {
                                if (dotremove[k].length() < 3 && dotremove[k + 1].length() < 3 && !Character.isDigit(dotremove[k].charAt(dotremove[k].length() - 1))) {
                                    dotremove[k] = dotremove[k].substring(0, dotremove[k].length());
                                    ptr2 = false;
                                    if (k + 1 == dotremove.length - 1) {
                                        dotremove[k + 1] = dotremove[k + 1].substring(0, dotremove[k + 1].length());
                                    }
                                } else {
                                    if (!Character.isDigit(dotremove[k].charAt(dotremove[k].length() - 1))) {
                                        dotremove[k] = dotremove[k].substring(0, dotremove[k].length()) + " ";
                                        ptr2 = false;
                                    } else {
                                        if (Character.isDigit(dotremove[k].charAt(dotremove[k].length() - 1)) && Character.isDigit(dotremove[k + 1].charAt(0))) {
                                            dotremove[k] = dotremove[k].substring(0, dotremove[k].length()) + ".";
                                            ptr2 = false;
                                        } else {
                                            dotremove[k] = dotremove[k].substring(0, dotremove[k].length());
                                            ptr2 = true;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            k++;
                        }
                    }
                    int y = 0;
                    String temp = "";
                    while (y < dotremove.length) {
                        temp += dotremove[y];
                        y++;
                    }
                    if (ptr2) {
                        temp += ".";
                    }
                   word = temp;
                }
            }
           
        }
        
        
        
        
        int j = 0;
        while (j < words.length) {
            output += words[j] + " ";

            j++;
        }
        output = output.replaceAll("\r", "");
        String[] newLine = output.split("\n");
        int u = 0;
        while (u < newLine.length) {
            if (newLine[u].length() > 0) {
                if (!newLine[u].trim().endsWith(".")) {
                    newLine[u] = newLine[u].trim() + ". ";
                }
            }
            u++;
        }
        u = 0;
        output = "";
        while (u < newLine.length) {
            if (newLine[u].length() > 0) {
                output += newLine[u] + "\n";
            }
            u++;
        }
        output = output.replaceAll("\n\n", "");
        return output.replaceAll("\\.\\.", ".");
    }
}
