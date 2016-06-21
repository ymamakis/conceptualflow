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
 * A paragraph bean
 * @author Yorgos Mamakis
 */
public class Paragraph {
    
   private List<String> sentences;
    
    private int index;
    

    /**
     * Gets the original document sentences comprising this paragraph
     * @return The original document sentences
     */
    protected List<String> getSentences() {
        return sentences;
    }

    /**
     * Sets the sentences of the paragraph
     * @param sentences The sentences of the paragraph
     */
    protected void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

    /**
     * Gets the position of the paragraph in the document
     * @return The position of the paragraph in the document 
     */
    protected int getIndex() {
        return index;
    }

    /**
     * Sets the position of the paragraph in the document
     * @param index The position of the paragraph in the document
     */
    protected void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets the text of the paragraph (The only punctuation mark retrieved is a dot).
     * @return The text of the paragraph
     */
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (String str : sentences) {
            sb.append(str);
            sb.append("\\. ");
        }
        return sb.toString();
    }
}
