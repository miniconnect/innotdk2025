package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import java.util.List;

public interface Topic {

    public String name();
    
    public List<Keyword> keywords();
    
    public PaperTextTemplatesResult buildPaperTextTemplates(long seed);
    
}
