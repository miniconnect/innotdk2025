package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import java.util.function.BiFunction;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

public enum TopicEnum {
    
    ASTRONOMY(AstronomyTopic::new),
    BIOLOGY(BiologyTopic::new),
    CHEMISTRY(ChemistryTopic::new),
    ENGINEERING(EngineeringTopic::new),
    INFORMATICS(InformaticsTopic::new),
    MATHEMATICS(MathematicsTopic::new),
    MEDICINE(MedicineTopic::new),
    PHYSICS(PhysicsTopic::new),
    
    ;
    
    private final BiFunction<NLGFactory, Realiser, Topic> topicSupplier;
    
    private TopicEnum(BiFunction<NLGFactory, Realiser, Topic> topicSupplier) {
        this.topicSupplier = topicSupplier;
    }
    
    public Topic topic(NLGFactory factory, Realiser realiser) {
        return topicSupplier.apply(factory, realiser);
    }

}
