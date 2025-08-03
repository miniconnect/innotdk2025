package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

public enum PlaceholderType {

    SURNAME("Smith"),                  // e.g. Einstein
    INSTITUTE("The University"),       // e.g. MIT
    TOOL("tool"),                      // e.g. electron microscope
    METHOD("method"),                  // e.g. local search with cooling
    GENERIC_METHOD("method"),          // e.g. algorithm
    ITEM("thing"),                     // e.g. molecule
    GENERAL_ITEM("thing"),             // e.g. XYZ-molecule
    GOAL("improvement"),               // e.g. 58% increase
    RESEARCH("research"),              // e.g. experiment
    VERB("do"),                        // e.g. radiate
    FIND_VERB("construct"),            // e.g. achieve
    ADVERB("somehow"),                 // e.g. without prior teaching
    SUCCESS_ADVERB("successfully"),    // e.g. efficiently
    
    ;
    
    private final String defaultWord;
    
    private PlaceholderType(String defaultWord) {
        this.defaultWord = defaultWord;
    }
    
    public String defaultWord() {
        return defaultWord;
    }
    
}
