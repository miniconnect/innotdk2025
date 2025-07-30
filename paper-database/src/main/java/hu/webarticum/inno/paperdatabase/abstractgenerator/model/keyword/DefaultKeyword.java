package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import hu.webarticum.inno.paperdatabase.abstractgenerator.PlaceholderType;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;

public class DefaultKeyword implements Keyword {
    
    private final String name;
    
    public DefaultKeyword(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String generateWord(PlaceholderType type, long seed) {
        // TODO
        return "Lorem-" + seed + "-ipsum";
    }

}
