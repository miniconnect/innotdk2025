package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import java.util.List;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;

public interface Topic {

    public String name();
    
    public List<Keyword> keywords();
    
    public PaperTextsResult buildPaperTextTemplates(WordGenerator wordGenerator, boolean multiAuthored, long seed);
    
}
