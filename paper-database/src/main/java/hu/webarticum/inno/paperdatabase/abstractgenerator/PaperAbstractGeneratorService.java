package hu.webarticum.inno.paperdatabase.abstractgenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextsResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.CompoundWordGenerator;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.ContextModel;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;

public class PaperAbstractGeneratorService {
    
    public List<Map<String, Object>> generateAlternatives() {
        ContextModel model = new ContextModel();
        Random topicChooserRandom = new Random(0);
        List<Topic> topics = model.topics();
        List<Map<String, Object>> result = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            int choosenTopicIndex = topicChooserRandom.nextInt(topics.size());
            Topic topic = topics.get(choosenTopicIndex);
            List<Keyword> choosenKeywords = chooseKeywords(topic, i);
            WordGenerator wordGenerator = compoundWordGeneratorOf(choosenKeywords, i);
            PaperTextsResult templates = topic.buildPaperTextTemplates(wordGenerator, true, i);
            Map<String, Object> paperDataMap = new LinkedHashMap<>();
            paperDataMap.put("title", templates.title());
            paperDataMap.put("keywords", keywordNamesOf(choosenKeywords));
            paperDataMap.put("abstract", templates.abstractText());
            result.add(paperDataMap);
        }
        return result;
    }

    private List<Keyword> chooseKeywords(Topic topic, long seed) {
        // TODO: choose some of them
        return topic.keywords();
    }
    
    private WordGenerator compoundWordGeneratorOf(List<Keyword> keywords, long seed) {
        List<WordGenerator> wordGenerators = new ArrayList<>(keywords.size());
        long wordGeneratorSeed = seed;
        for (Keyword selectedKeyword : keywords) {
            wordGeneratorSeed *= 37;
            WordGenerator wordGenerator = selectedKeyword.wordGenerator(wordGeneratorSeed);
            wordGenerators.add(wordGenerator);
        }
        return new CompoundWordGenerator(wordGenerators, seed);
    }
    
    private List<String> keywordNamesOf(List<Keyword> keywords) {
        List<String> result = new ArrayList<String>(keywords.size());
        for (Keyword keyword : keywords) {
            result.add(keyword.name());
        }
        return result;
    }

}
