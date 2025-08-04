package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import java.util.List;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;

public interface Topic {

    public default String name() {
        return getClass().getSimpleName().replaceAll("^([A-Z][a-z]*).*$", "$1");
    }

    public List<Keyword> keywords();
    
    public PaperTextsResult buildPaperTextTemplates(WordGenerator wordGenerator, long seed);
    
}
