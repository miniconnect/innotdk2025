package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;

@FunctionalInterface
public interface WordGenerator {
    
    public String generate(PlaceholderType placeholderType);
    
}
