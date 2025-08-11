package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;

public interface Keyword {

    public default String label() {
        return getClass().getSimpleName().toLowerCase().replace("_", "-");
    }

    public String longLabel();
    
    public WordGenerator wordGenerator(long seed);
    
}
