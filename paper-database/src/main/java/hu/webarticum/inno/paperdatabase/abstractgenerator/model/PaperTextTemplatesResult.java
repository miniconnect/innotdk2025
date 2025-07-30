package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import simplenlg.framework.NLGElement;

public class PaperTextTemplatesResult {
    
    private final NLGElement title;

    private final NLGElement abstractText;
    
    public PaperTextTemplatesResult(NLGElement title, NLGElement abstractText) {
        this.title = title;
        this.abstractText = abstractText;
    }

    public final NLGElement title() {
        return title;
    }

    public final NLGElement abstractText() {
        return abstractText;
    }
    
    public NLGElement[] all() {
        return new NLGElement[] { title, abstractText };
    }
    
}
