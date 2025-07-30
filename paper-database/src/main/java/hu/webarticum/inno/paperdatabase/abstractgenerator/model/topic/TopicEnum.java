package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import java.util.function.Function;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import simplenlg.framework.NLGFactory;

public enum TopicEnum {
    
    TEST_TOPIC(TestTopic::new),
    
    ;
    
    private final Function<NLGFactory, Topic> topicSupplier;
    
    private TopicEnum(Function<NLGFactory, Topic> topicSupplier) {
        this.topicSupplier = topicSupplier;
    }
    
    public Topic topic(NLGFactory factory) {
        return topicSupplier.apply(factory);
    }

}
