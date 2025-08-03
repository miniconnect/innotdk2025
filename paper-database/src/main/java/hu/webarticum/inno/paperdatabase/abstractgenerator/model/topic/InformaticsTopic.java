package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import static hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic.SHARED_KEY.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.TextGeneratorBase;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextsResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.KeywordEnum;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseCategory;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

enum SHARED_KEY {
    
    EARLIER_RESEARCHER,
    SOME_OBJECT,
    SOME_OTHER_OBJECT,
    
}

public class InformaticsTopic implements Topic {

    private static final String NAME = "test";
    
    private static final Keyword[] keywords = {
            KeywordEnum.AI.keyword(),
            KeywordEnum.ALGORITHMS.keyword(),
    };
    
    private final NLGFactory factory;
    
    private final Realiser realiser;
    
    public InformaticsTopic(NLGFactory factory, Realiser realiser) {
        this.factory = factory;
        this.realiser = realiser;
    }
    
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public List<Keyword> keywords() {
        return Collections.unmodifiableList(Arrays.asList(keywords));
    }

    @Override
    public PaperTextsResult buildPaperTextTemplates(WordGenerator wordGenerator, boolean multiAuthored, long seed) {
        InformaticsTextGenerator textGenerator = new InformaticsTextGenerator(wordGenerator, multiAuthored, factory, realiser, seed);
        return new PaperTextsResult(textGenerator.generateTitle(), textGenerator.generateAbstract());
    }
    
    private static class InformaticsTextGenerator extends TextGeneratorBase {
        
        private final boolean multiAuthored;
        
        private final Map<SHARED_KEY, NLGElement> sharedElements = new EnumMap<>(SHARED_KEY.class);
    
        protected InformaticsTextGenerator(WordGenerator wordGenerator, boolean multiAuthored, NLGFactory factory, Realiser realiser, long seed) {
            super(factory, realiser, () -> createRandom(seed));
            this.multiAuthored = multiAuthored;
            sharedElements.clear();
            sharedElements.put(EARLIER_RESEARCHER, createElement(wordGenerator.generate(PlaceholderType.SURNAME), PhraseCategory.NOUN_PHRASE, factory));
            sharedElements.put(SOME_OBJECT, createElement(wordGenerator.generate(PlaceholderType.ITEM), PhraseCategory.NOUN_PHRASE, factory));
            sharedElements.put(SOME_OTHER_OBJECT, createElement(wordGenerator.generate(PlaceholderType.ITEM), PhraseCategory.NOUN_PHRASE, factory));
        }

        private static Random createRandom(long seed) {
            Random random = new Random(seed);
            // FIXME: warmup
            for (int i = 0; i < 5; i++) {
                random.nextLong();
            }
            return random;
        }

        public String generateTitle() {
            return generate(this::titleStructure);
        }

        private NLGElement titleStructure() {
            NPPhraseSpec mainPhrase = factory.createNounPhrase("Generation");
            
            NPPhraseSpec subjectPhrase = factory.createNounPhrase(shared(SOME_OBJECT) + " instances");
            subjectPhrase.setPlural(true);
            subjectPhrase.setPreModifier("of");
            mainPhrase.addPostModifier(subjectPhrase);
            
            VPPhraseSpec gerundPhrase = factory.createVerbPhrase("using");
            gerundPhrase.setFeature(Feature.FORM, Form.GERUND);
            gerundPhrase.addComplement(shared(SOME_OTHER_OBJECT));
            mainPhrase.addPostModifier(gerundPhrase);
            
            return mainPhrase;
        }
        
        public String generateAbstract() {
            return generate(paragraphOf(sequenceOf(
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    optional(sentence(this::sentenceC)))));
        }
    
        private DocumentElement sentenceA() {
            String directObjectName = random.nextBoolean() ? "research" : "experiment";
            SPhraseSpec mainClause = factory.createClause(shared(EARLIER_RESEARCHER), "start", directObjectName);
            VPPhraseSpec gerundPhrase = factory.createVerbPhrase("use");
            gerundPhrase.setFeature(Feature.FORM, Form.GERUND);
            gerundPhrase.addComplement(shared(SOME_OBJECT));
            PPPhraseSpec withPhrase = factory.createPrepositionPhrase();
            withPhrase.setPreposition("with");
            withPhrase.addComplement(gerundPhrase);
            mainClause.addPostModifier(withPhrase);
            return factory.createSentence(mainClause);
        }
    
        private DocumentElement sentenceB() {
            return factory.createSentence(pastTense(factory.createClause(shared(EARLIER_RESEARCHER), "use", shared(SOME_OTHER_OBJECT))));
        }
    
        private DocumentElement sentenceC() {
            return factory.createSentence(pastTense(factory.createClause(shared(EARLIER_RESEARCHER), "finish")));
        }
    
        private SPhraseSpec pastTense(SPhraseSpec phrase) {
            phrase.setFeature(Feature.PRONOMINAL, true);
            return phrase;
        }
        
        private NLGElement shared(SHARED_KEY key) {
            return sharedElements.get(key);
        }
        
        private NLGElement createElement(String value, PhraseCategory phraseCategory, NLGFactory factory) {
            NLGElement element = factory.createStringElement(value);
            element.setCategory(phraseCategory);
            return element;
        }
    
    }

}
