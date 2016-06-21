/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mamakis.conceptualflow;

import java.util.Map;

/**
 * The main interface for the linguistic rules per language. In the current
 * development Greek is implemented using Grammatical Analysis while English
 * syntactical analysis. Every subsequent class should implement this interface.
 * It is up to the implementation to specify the way nouns are created per
 * language.
 *
 * @author Yorgos Mamakis
 */
public interface SentenceCreator {

    /**
     * Method that splits the document in sentence objects and extracts the nouns per
     * sentence. It returns a Sentence vector holding all the words of the
     * sentence
     *
     * @param par Paragraph that will split into sentences
     * @return Sentences nouns in form of Sentence
     */
    public Map<Integer, Sentence> split(Paragraph par);
    
    /**
     * Method that splits the document into sentence strings
     * @param text The text to split
     * @return The sentences
     */
    public String[] splitIntoSentences(String paragraph);
}
