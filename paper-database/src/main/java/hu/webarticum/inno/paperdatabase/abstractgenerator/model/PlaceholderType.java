package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

public enum PlaceholderType {

    AREA("optimization"),
    DIFFICULTY("erosion"),
    METHOD("effort"),
    ITEM("item"),
    PRODUCE("produce"),
    
    ;
    
    private final String defaultWord;
    
    private PlaceholderType(String defaultWord) {
        this.defaultWord = defaultWord;
    }
    
    public String defaultWord() {
        return defaultWord;
    }
    
}
