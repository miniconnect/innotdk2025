package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

public class PaperTextsResult {
    
    private final String title;

    private final String abstractText;
    
    public PaperTextsResult(String title, String abstractText) {
        this.title = title;
        this.abstractText = abstractText;
    }

    public final String title() {
        return title;
    }

    public final String abstractText() {
        return abstractText;
    }
    
}
