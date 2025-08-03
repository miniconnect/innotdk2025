package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;

class PlaceholderTypeWordsSpec {
    
    final PlaceholderType placeholderType;

    final String[] fixedStrings;

    final String[] regexes;
    
    public PlaceholderTypeWordsSpec(PlaceholderType placeholderType, String[] fixedStrings, String[] regexes) {
        this.placeholderType = placeholderType;
        this.fixedStrings = fixedStrings;
        this.regexes = regexes;
    }
    
}