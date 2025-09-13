package hu.webarticum.inno.paperdatabase.abstractgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextsResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.CompoundWordGenerator;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.KeywordEnum;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic.MathematicsTopic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.ContextModel;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;

public class PaperAbstractGeneratorService {
    
    public Object generateAlternatives() {
        ContextModel model = new ContextModel();
        Random random = new Random(234814592L);
        List<Topic> topics = model.topics();
        List<Object> result = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            Topic topic = chooseTopic(topics, random);
            Keyword primaryKeyword = choosePrimaryKeyword(topic, random);
            WordGenerator primaryWordGenerator = primaryKeyword.wordGenerator(random.nextLong());
            List<Keyword> secondaryKeywords = chooseSecondaryKeywords(topic, primaryKeyword, random);
            WordGenerator secondaryWordGenerator = compoundWordGeneratorOf(secondaryKeywords, random.nextLong());
            PaperTextsResult textsResult = topic.buildPaperTextTemplates(primaryWordGenerator, secondaryWordGenerator, i);
            Map<String, Object> paperDataMap = new LinkedHashMap<>();
            paperDataMap.put("title", textsResult.title());
            paperDataMap.put("topic", topic.name());
            paperDataMap.put("primaryKeyword", primaryKeyword.label());
            paperDataMap.put("secondaryKeywords", keywordNamesOf(secondaryKeywords));
            paperDataMap.put("abstract", textsResult.abstractText());

            System.out.println("TITLE: " + textsResult.title());
            System.out.println("ABSTRACT: " + textsResult.abstractText());
            System.out.println();
            
            result.add(paperDataMap);
        }
        return result;
    }
    
    public static void main(String[] args) {
        new PaperAbstractGeneratorService().generateAlternatives();
    }
    
    private Topic chooseTopic(List<Topic> topics, Random random) {
        //int choosenTopicIndex = random.nextInt(topics.size());
        //return topics.get(choosenTopicIndex);
        return topics.stream().filter(MathematicsTopic.class::isInstance).findAny().get(); // NOSONAR: always present
    }

    private Keyword choosePrimaryKeyword(Topic topic, Random random) {
        // FIXME
        if (true) {
            return KeywordEnum.AI.keyword();
        }
        
        List<Keyword> primaryKeywords = topic.primaryKeywords();
        int choosenIndex = random.nextInt(primaryKeywords.size());
        return primaryKeywords.get(choosenIndex);
    }

    private List<Keyword> chooseSecondaryKeywords(Topic topic, Keyword primaryKeyword, Random random) {
        // FIXME
        if (true) {
            return Arrays.asList(KeywordEnum.ALGORITHMS.keyword());
        }
        
        List<Keyword> remainingPrimaryKeywords = topic.primaryKeywords().stream()
                .filter(k -> k != primaryKeyword)
                .collect(Collectors.toList());
        List<Keyword> secondaryKeywords = topic.secondaryKeywords();
        List<Keyword> keywords = new ArrayList<>(remainingPrimaryKeywords.size() + secondaryKeywords.size());
        keywords.addAll(remainingPrimaryKeywords);
        keywords.addAll(secondaryKeywords);
        // TODO: choose some of them
        return keywords;
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
            result.add(keyword.label());
        }
        return result;
    }

}
