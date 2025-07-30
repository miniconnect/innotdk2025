package hu.webarticum.inno.paperdatabase.abstractgenerator;

import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseCategory;
import simplenlg.framework.PhraseElement;

public class NlgPlaceholder extends PhraseElement {
    
    private final PlaceholderType placeholderType;

    public NlgPlaceholder(PlaceholderType placeholderType, PhraseCategory category, NLGFactory factory) {
        super(category);
        this.setFactory(factory);
        this.placeholderType = placeholderType;
    }
    
    public PlaceholderType getPlaceholderType() {
        return placeholderType;
    }
    
    public void substitute(String word) {
        setHead(word);
    }
    
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }
    
}
