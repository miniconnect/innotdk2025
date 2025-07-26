package hu.webarticum.inno.paperdatabase.abstractgenerator;

import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class AbstractGenerator {
    
    public String generate() {
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);
        
        SPhraseSpec p = nlgFactory.createClause("the professor", "explain", "the theory");
        p.setFeature(Feature.TENSE, Tense.PAST);
        p.setFeature(Feature.PROGRESSIVE, true);
        p.addModifier("clearly");
        p.addComplement("during the lecture");
        
        NPPhraseSpec subject = nlgFactory.createNounPhrase("the", "student");
        subject.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        SPhraseSpec reason = nlgFactory.createClause(subject, "ask", "many questions");
        reason.setFeature(Feature.TENSE, Tense.PAST);

        CoordinatedPhraseElement complex = nlgFactory.createCoordinatedPhrase(p, reason);
        complex.setConjunction("because");

        return realiser.realiseSentence(complex);
    }

}
