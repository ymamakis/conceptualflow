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
 * Cluster bean
 * @author Yorgos Mamakis
 */
public class Cluster  {
    
    private List<Sentence> sentences;
    /**
     * Constructor of the Cluster
     * @param sents The sentences forming the cluster
     */
    protected Cluster(List<Sentence> sents){
        this.sentences = sents;
    }
    /**
     * Get the sentences of the cluster
     * @return The sentences of the cluster
     */
    protected List<Sentence> getSentences() {
        return sentences;
    }

    
    
    
}
