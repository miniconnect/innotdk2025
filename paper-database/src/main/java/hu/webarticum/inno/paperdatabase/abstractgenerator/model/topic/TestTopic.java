package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import static hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic.SHARED_KEY.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import hu.webarticum.inno.paperdatabase.abstractgenerator.AbstractTextTemplateGenerator;
import hu.webarticum.inno.paperdatabase.abstractgenerator.NlgPlaceholder;
import hu.webarticum.inno.paperdatabase.abstractgenerator.PlaceholderType;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextTemplatesResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.KeywordEnum;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseCategory;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;

enum SHARED_KEY {
    
    EARLIER_RESEARCHER,
    SOME_OBJECT,
    
}

public class TestTopic implements Topic {

    private static final String NAME = "test";
    
    private final NLGFactory factory;
    
    private final List<Keyword> keywords;
    
    public TestTopic(NLGFactory factory) {
        this.factory = factory;
        this.keywords = Collections.unmodifiableList(collectKeywords());
    }
    
    private static List<Keyword> collectKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        keywords.add(KeywordEnum.TEST_KEYWORD.keyword());
        return keywords;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public List<Keyword> keywords() {
        return keywords;
    }

    @Override
    public PaperTextTemplatesResult buildPaperTextTemplates(long seed) {
        return new PaperTextTemplatesResult(
                factory.createClause("Particle", "do", "movement"),
                new TestTextTemplateGenerator(factory, seed).generateTemplate());
    }

    private static class TestTextTemplateGenerator extends AbstractTextTemplateGenerator {
        
        private final Map<SHARED_KEY, NlgPlaceholder> sharedPlaceholders = new EnumMap<>(SHARED_KEY.class);
    
        protected TestTextTemplateGenerator(NLGFactory factory, long seed) {
            super(factory, () -> createRandom(seed));
            sharedPlaceholders.clear();
            sharedPlaceholders.put(EARLIER_RESEARCHER, new NlgPlaceholder(PlaceholderType.SURNAME, PhraseCategory.NOUN_PHRASE, factory));
            sharedPlaceholders.put(SOME_OBJECT, new NlgPlaceholder(PlaceholderType.ITEM, PhraseCategory.NOUN_PHRASE, factory));
        }

        private static Random createRandom(long seed) {
            Random random = new Random(seed);
            // FIXME: warmup
            for (int i = 0; i < 5; i++) {
                random.nextLong();
            }
            return random;
        }

        @Override
        protected Supplier<DocumentElement> buildGeneratorStructure() {
            return paragraphOf(sequenceOf(
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    anyOf(sentence(this::sentenceA), sentence(this::sentenceB)),
                    optional(sentence(this::sentenceC))));
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
            return factory.createSentence(pastTense(factory.createClause(shared(EARLIER_RESEARCHER), "go", "home")));
        }
    
        private DocumentElement sentenceC() {
            return factory.createSentence(pastTense(factory.createClause(shared(EARLIER_RESEARCHER), "finish")));
        }
    
        private SPhraseSpec pastTense(SPhraseSpec phrase) {
            phrase.setFeature(Feature.PRONOMINAL, true);
            return phrase;
        }
        
        private NlgPlaceholder shared(SHARED_KEY key) {
            return sharedPlaceholders.get(key);
        }
    
    }

}
