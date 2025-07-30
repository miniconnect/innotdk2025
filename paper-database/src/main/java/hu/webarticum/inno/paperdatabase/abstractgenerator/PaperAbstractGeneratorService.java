package hu.webarticum.inno.paperdatabase.abstractgenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextTemplatesResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic.TopicEnum;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseCategory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class PaperAbstractGeneratorService {
    
    public String generateExample() {
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory factory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);
        
        SPhraseSpec p = factory.createClause("the professor", "explain", "the theory");
        p.setFeature(Feature.TENSE, Tense.PAST);
        p.setFeature(Feature.PROGRESSIVE, true);
        p.addModifier("clearly");
        p.addComplement("during the lecture");
        
        NlgPlaceholder placeholder = new NlgPlaceholder(PlaceholderType.SURNAME, PhraseCategory.NOUN_PHRASE, factory);
        
        NPPhraseSpec subject = factory.createNounPhrase("the", placeholder);
        subject.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        SPhraseSpec reason = factory.createClause(subject, "ask", "many questions");
        reason.setFeature(Feature.TENSE, Tense.PAST);

        CoordinatedPhraseElement complex = factory.createCoordinatedPhrase(p, reason);
        complex.setConjunction("because");
        
        new SubstitutorAlgorithm().substituteAll(t -> "student", complex);

        return realiser.realiseSentence(complex);
    }
    
    public List<Map<String, String>> generateAlternatives() {
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory factory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);
        SubstitutorAlgorithm substitutor = new SubstitutorAlgorithm();
        
        List<Map<String, String>> result = new ArrayList<>();
        for (long i = 0; i < 1; i++) {
            Topic topic = TopicEnum.TEST_TOPIC.topic(factory);
            PaperTextTemplatesResult templates = topic.buildPaperTextTemplates(i);
            substitutor.substituteAll(t -> t == PlaceholderType.SURNAME ? "David" : "screwdriver", templates.all());
            NLGElement templatedTitle = templates.title();
            NLGElement templatedAbstractText = templates.abstractText();
            Map<String, String> paperDataMap = new LinkedHashMap<>();
            paperDataMap.put("title", realiser.realise(templatedTitle).getRealisation().trim());
            paperDataMap.put("abstract", realiser.realise(templatedAbstractText).getRealisation().trim());
            result.add(paperDataMap);
        }
        
        return result;
    }

}
