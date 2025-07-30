package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import hu.webarticum.inno.paperdatabase.abstractgenerator.PlaceholderType;

public interface Keyword {

    public String name();
    
    public String generateWord(PlaceholderType type, long seed);
    
}
