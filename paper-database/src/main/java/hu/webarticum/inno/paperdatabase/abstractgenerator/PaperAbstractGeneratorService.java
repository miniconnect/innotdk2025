package hu.webarticum.inno.paperdatabase.abstractgenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextsResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.TopicModel;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.english.Realiser;

public class PaperAbstractGeneratorService {
    
    public List<Map<String, String>> generateAlternatives() {
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory factory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);
        TopicModel model = new TopicModel(factory, realiser);
        
        List<Map<String, String>> result = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            Topic topic = model.topics().get(0);
            PaperTextsResult templates = topic.buildPaperTextTemplates(t -> t == PlaceholderType.SURNAME ? "David" : "screwdriver", i);
            Map<String, String> paperDataMap = new LinkedHashMap<>();
            paperDataMap.put("title", templates.title());
            paperDataMap.put("abstract", templates.abstractText());
            result.add(paperDataMap);
        }
        
        return result;
    }

}
