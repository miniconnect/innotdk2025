package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextsResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.KeywordEnum;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

public class MedicineTopic implements Topic {

    enum SharedKey {
        
        SOME_OBJECT,
        
    }
    
    private static final Keyword[] keywords = {
            KeywordEnum.AI.keyword(),
            KeywordEnum.ALGORITHMS.keyword(),
    };
    
    private final NLGFactory factory;
    
    private final Realiser realiser;
    
    public MedicineTopic(NLGFactory factory, Realiser realiser) {
        this.factory = factory;
        this.realiser = realiser;
    }
    
    @Override
    public List<Keyword> keywords() {
        return Collections.unmodifiableList(Arrays.asList(keywords));
    }

    @Override
    public PaperTextsResult buildPaperTextTemplates(WordGenerator wordGenerator, long seed) {
        TopicTextGenerator textGenerator = new TopicTextGenerator(wordGenerator, factory, realiser, seed);
        return new PaperTextsResult(textGenerator.generateTitle(), textGenerator.generateAbstract());
    }

    private static class TopicTextGenerator extends TextGeneratorBase {

        private static final String P_LOREM = "lorem";
        
        private static final Map<String, PlaceholderType> PLACEHOLDERS = new HashMap<>();
        static {
            PLACEHOLDERS.put(P_LOREM, PlaceholderType.TOOL);
        }
        
        protected TopicTextGenerator(WordGenerator wordGenerator, NLGFactory factory, Realiser realiser, long seed) {
            super(PLACEHOLDERS, factory, realiser, wordGenerator, seed);
        }

        public String generateTitle() {
            return "Some medicine paper";
        }

        public String generateAbstract() {
            return "Lorem ipsum dolor sit amet health.";
        }
        
    }
    
}
