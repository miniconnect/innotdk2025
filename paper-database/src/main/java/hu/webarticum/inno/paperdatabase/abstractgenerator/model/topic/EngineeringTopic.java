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

public class EngineeringTopic implements Topic {

    private static final Keyword[] primaryKeywords = {
            KeywordEnum.ALGORITHMS.keyword(),
    };

    private static final Keyword[] secondaryKeywords = {
            KeywordEnum.AI.keyword(),
    };
    
    private final NLGFactory factory;
    
    private final Realiser realiser;
    
    public EngineeringTopic(NLGFactory factory, Realiser realiser) {
        this.factory = factory;
        this.realiser = realiser;
    }

    @Override
    public List<Keyword> primaryKeywords() {
        return Collections.unmodifiableList(Arrays.asList(primaryKeywords));
    }

    @Override
    public List<Keyword> secondaryKeywords() {
        return Collections.unmodifiableList(Arrays.asList(secondaryKeywords));
    }

    @Override
    public PaperTextsResult buildPaperTextTemplates(WordGenerator primaryWordGenerator, WordGenerator secondaryWordGenerator, long seed) {
        TopicTextGenerator textGenerator = new TopicTextGenerator(primaryWordGenerator, secondaryWordGenerator, factory, realiser, seed);
        return new PaperTextsResult(textGenerator.generateTitle(), textGenerator.generateAbstract());
    }
    
    private static class TopicTextGenerator extends TextGeneratorBase {

        private static final String P_LOREM = "lorem";
        private static final String P_IPSUM = "ipsum";
        
        private static final Map<String, PlaceholderType> PRIMARY_PLACEHOLDERS = new HashMap<>();
        static {
            PRIMARY_PLACEHOLDERS.put(P_LOREM, PlaceholderType.TOOL);
        }

        private static final Map<String, PlaceholderType> SECONDARY_PLACEHOLDERS = new HashMap<>();
        static {
            SECONDARY_PLACEHOLDERS.put(P_IPSUM, PlaceholderType.TOOL);
        }
        
        protected TopicTextGenerator(
                WordGenerator primaryWordGenerator, WordGenerator secondaryWordGenerator, NLGFactory factory, Realiser realiser, long seed) {
            super(PRIMARY_PLACEHOLDERS, SECONDARY_PLACEHOLDERS, factory, realiser, primaryWordGenerator, secondaryWordGenerator, seed);
        }

        public String generateTitle() {
            return "Some engineering paper";
        }

        public String generateAbstract() {
            return "Lorem ipsum dolor sit amet machine.";
        }
        
    }
    
}
