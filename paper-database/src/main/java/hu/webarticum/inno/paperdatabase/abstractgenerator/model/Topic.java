package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import java.util.List;
import java.util.function.Function;

import hu.webarticum.inno.paperdatabase.abstractgenerator.PlaceholderType;

public interface Topic {

    public String name();
    
    public List<Keyword> keywords();
    
    public PaperTextsResult buildPaperTextTemplates(Function<PlaceholderType, String> substitutor, long seed);
    
}
