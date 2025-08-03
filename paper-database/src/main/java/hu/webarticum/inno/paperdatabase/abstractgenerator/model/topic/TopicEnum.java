package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import java.util.function.BiFunction;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

public enum TopicEnum {
    
    INFORMATICS(InformaticsTopic::new),
    
    ;
    
    private final BiFunction<NLGFactory, Realiser, Topic> topicSupplier;
    
    private TopicEnum(BiFunction<NLGFactory, Realiser, Topic> topicSupplier) {
        this.topicSupplier = topicSupplier;
    }
    
    public Topic topic(NLGFactory factory, Realiser realiser) {
        return topicSupplier.apply(factory, realiser);
    }

}
