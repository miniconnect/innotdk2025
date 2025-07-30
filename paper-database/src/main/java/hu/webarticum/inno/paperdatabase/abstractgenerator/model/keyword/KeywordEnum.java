package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;

public enum KeywordEnum {
    
    TEST_KEYWORD("test-keyword"),
    
    ;
    
    private final Keyword keyword;
    
    private KeywordEnum(String name) {
        // TODO: all parameters
        this.keyword = new DefaultKeyword(name);
    }
    
    public Keyword keyword() {
        return keyword;
    }

}
