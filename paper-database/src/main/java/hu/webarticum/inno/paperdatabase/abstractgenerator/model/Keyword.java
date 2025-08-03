package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;

public interface Keyword {

    public String name();
    
    public WordGenerator wordGenerator(long seed);
    
}
