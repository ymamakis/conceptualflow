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

/**
 * Class containing the stopwords and endings
 * 
 * @author Yorgos Mamakis
 */
public class GreekStopWord {

    /**
     * Stop words
     */
    public static final String[] STOPWORDS= new String[]{
        "ο", "του", "στου", "το", "τον", "στον",
        "στο", "η", "της", "στης", "τη", "την", "στην", "στη",
        "οι", "των", "στων", "τους", "στους", "τις",
        "στις", "τα", "στα", "ενας", "ενος", "ενα", "μια",
        "μιας", "μια", "μιας", "του","και", "κι", "ουτε", "μητε", "ουδε", "μηδε", "η", "ειτε",
        "μα", "αλλα", "παρα", "ομως", "ωστοσο", "ενω", "αν", "μολονοτι",
        "μονο", "λοιπον", "ωστε", "αρα", "επομενως", "που", "θα",
        "δηλαδη", "πως", "οτι", "οταν", "εαν", "καθως", "αφου", "αφοτου",
        "πριν", "μολις", "προτου", "ωσπου", "ωσοτου", "οσο", "οποτε",
        "γιατι", "επειδη", "αμα", "να", "μη", "μην", "μηπως", "για", "ας", "δεν", "οχι", "ναι", "οπου", 
        "α", "μπα", "χμ", "ποπο", "ω", "αχ", "οχου", "αου", "αλι",
        "οχ", "αλιμονο", "ε", "ου", "αχαχουχα", "ειθε", "μακαρι",
        "αμποτε", "ευγε", "μπραβο", "ουφ", "πουφ", "πα πα πα",
        "αντε", "αμε", "μαρς", "αλτ", "στοπ", "σουτ", "στοπ", "αερα",
            "με", "συν", "σε", "πλην", "για", "επι", "ως", "δια", "προς",
        "μειον", "κατα", "υπερ", "μετα", "περι", "παρα", "εν", "αντι",
        "εκ", "απο", "εξ", "διχως", "υπο", "χωρις", "ισαμε",
        "εγω", "αυτοι", "εμενα", "αυτους", "εμεις", "αυτη", "αυτην",
        "εμας", "αυτης", "μου", "αυτες", "με", "τος", "μας", "του",
        "εσυ", "τον", "εσενα", "τα", "σου", "τους", "σε", "τη", "εσεις",
        "της", "εσας", "τη", "την", "σας", "το", "αυτος", "τες",
        "αυτου", "τις", "αυτο", "αυτον", "αυτων",
        "καποιος", "καποιου  ", "καποια", "καποιο",
        "που", "φετος", "καπου", "πως", "πουθενα", "καπως", "εδω", "αλλιως", "εκει", "ετσι",
        "αυτου", "μαζι", "παντου", "οπως", "οπου", "καθως", "οπουδηποτε", "ως", "πανω", "σαν",
        "κατω", "διαρκως", "καταγης", "μεμιας", "μεσα", "μονομιας", "εξω", "επισης", "εμπρος",
        "μπρος", "ιδιως", "πισω", "κυριως", "απεναντι", "ειδεμη", "γυρω", "τυχον", "ολογυρα",
        "καλως", "μεταξυ", "ακριβως", "αναμεταξυ", "εντελως", "περα", "ευτυχως", "αντιπερα", "εξης",
        "ποτε", "ποσο", "καποτε", "καμποσο", "αλλοτε", "τοσο", "τοτε", "οσο", "τωρα", "οσοδηποτε",
        "ποτε", "πολυ", "οποτε", "πιο", "οποτεδηποτε", "λιγακι", "αμεσως", "σχεδον", "κιολας",
        "τουλαχιστον", "ηδη", "περιπου", "πια", "καθολου", "μολις", "διολου", "ακομη", "ολωσδιολου",
        "ακομα", "ολοτελα", "παλι", "μαλλον", "ξανα", "εξισου", "συνηθως", "προτυτερα",
        "μαλιστα", "νωρις", "ορισμενως", "χτες", "ισως", "χθες", "ταχα", "σημερα", "δηθεν",
        "αποψε", "πιθανον", "αυριο", "αραγε", "μεθαυριο", "Οχι", "οχι", "ναι", "περσι", "δεν", "προπερσι",
        "εκεινος", "εκεινους", "εκεινη", "εκεινης", "εκεινο", "εκεινοι", "εκεινων", "εκεινες", "εκεινου", "εκεινα", "καθε", "οποιος",
        "οποια", "οποιο", "τετοιος", "τετοια", "τετοιου", "τοσο", "μη", "μην",
        "εχω", "ειμαι", "πρεπει", "εχεις", "εισαι", "προκειται",
        "εχεις", "ειναι", "συμφερει", "εχουμε", "ειμαστε", "συμφερει",
        "εχετε", "ειστε", "μελλει", "εχουν", "ημουν", "μελλεται",
        "ειχα", "ησουν", "ειχες", "ηταν", "ειχε", "ημασταν", "ειχαμε",
        "ησασταν", "ειχατε", "ηταν", "ειχαν"
    };
  
    /**
     * Noun endings
     */
    public static final String[] NOUN_ENDINGS = new String[]{"ας", "α", "αδες", "αδων", "ες", "ων",
        "ης", "η", "ηδες", "ηδων", "ες", "ων",
        "ες", "εδες", "εδων",
        "ους", "ουδες", "ουδων",
        "ος", "ου", "ο", "ε", "οι", "ων", "ους",
        "εας", "εα", "εις", "εων",
        "α", "ας", "ων", "ες", "αδες", "αδων",
        "η", "ης", "ων", "ες", "εις", "εων",
        "ω", "ως",
        "ος", "ου", "ο", "οι", "ων", "ους",
        "ο", "ου", "α", "ων", "ατα", "ατων",
        "ι", "ιου", "ια", "ιων",
        "υ", "ιου", "ια", "ιων",
        "ος", "ους", "η", "ων",
        "α", "ατος", "ατα", "ατων",
        "ας", "ατος", "ατα", "ατων",
        "ως", "ωτος", "ατα", "των",
        "ον", "οντος", "οντα", "οντων",
        "αν", "αντος", "αντα", "αντων",
        "εν", "εντα", "εντος", "εντων"};

    /**
     * Verb endings
     */
    public static final String[] VERB_ENDINGS = new String[]{"ω", "ονται", "ονται", "ομουν", "οσουν", "ουμε", 
        "ομασταν", "ετε", "ονταν", "ουν", "οσασταν",
        "ειται", "αμε", "ατε", "εμαι", "ουνται", "ηκα", "ειτε", "ηκες", "ουμαστε", "αστε", "ηκαμε",
        "ηκατε", "ηκαν", "ομαι", "εσαι", "τε", "εται", "ομαστε", "εστε", "ει"};
}
