package hu.webarticum.inno.paperdatabase.abstractgenerator;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;

import simplenlg.framework.NLGElement;

public class SubstitutorAlgorithm {

    public void substituteAll(Function<PlaceholderType, String> valueProvider, NLGElement... elements) {
        LinkedHashSet<NlgPlaceholder> placeholders = new LinkedHashSet<>();
        collectPlaceholders(Arrays.asList(elements), placeholders);
        for (NlgPlaceholder placeholder : placeholders) {
            String value = valueProvider.apply(placeholder.getPlaceholderType());
            placeholder.substitute(value);
        }
    }
    
    private void collectPlaceholders(List<NLGElement> elements, LinkedHashSet<NlgPlaceholder> out) {
        for (NLGElement element : elements) {
            if (element instanceof NlgPlaceholder) {
                out.add((NlgPlaceholder) element);
            } else {
                collectPlaceholders(element.getChildren(), out);
            }
        }
    }
    
}
