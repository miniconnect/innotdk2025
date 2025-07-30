package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.KeywordEnum;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic.TopicEnum;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

public class TopicModel {
    
    private final List<Topic> topics;

    private final List<Keyword> keywords;
    
    public TopicModel(NLGFactory factory, Realiser realiser) {
        this.topics = Collections.unmodifiableList(collectTopics(factory, realiser));
        this.keywords = Collections.unmodifiableList(collectKeywords());
    }
    
    private static List<Topic> collectTopics(NLGFactory factory, Realiser realiser) {
        List<Topic> topics = new ArrayList<>();
        for (TopicEnum topicEnum : TopicEnum.values()) {
            topics.add(topicEnum.topic(factory, realiser));
        }
        Collections.sort(topics, Comparator.comparing(Topic::name));
        return topics;
    }

    private static List<Keyword> collectKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        for (KeywordEnum keywordEnum : KeywordEnum.values()) {
            keywords.add(keywordEnum.keyword());
        }
        Collections.sort(keywords, Comparator.comparing(Keyword::name));
        return keywords;
    }
    
    public List<Topic> topics() {
        return topics;
    }
    
    public List<Keyword> keywords() {
        return keywords;
    }
    
}
