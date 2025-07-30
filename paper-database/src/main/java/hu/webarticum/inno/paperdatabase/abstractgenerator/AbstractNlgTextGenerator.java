package hu.webarticum.inno.paperdatabase.abstractgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

public abstract class AbstractNlgTextGenerator implements TextGenerator {

    protected final Function<PlaceholderType, String> substitutor;
    
    protected final NLGFactory factory;
    
    protected final Realiser realiser;

    protected final Supplier<Random> randomSupplier;
    
    protected Random random = null;
    
    protected AbstractNlgTextGenerator(Function<PlaceholderType, String> substitutor, NLGFactory factory, Realiser realiser, Supplier<Random> randomSupplier) {
        this.substitutor = substitutor;
        this.factory = factory;
        this.realiser = realiser;
        this.randomSupplier = randomSupplier;
    }
    
    @Override
    public String generate() {
        return realiser.realise(buildNlgElement()).getRealisation().trim();
    }
    
    private NLGElement buildNlgElement() {
        this.random = randomSupplier.get();
        Supplier<? extends NLGElement> generatorStructure = buildGeneratorStructure();
        NLGElement result = generatorStructure.get();
        this.random = null;
        return result;
    }

    protected abstract Supplier<? extends NLGElement> buildGeneratorStructure(); // NOSONAR: wildcard is benefitical for simplicity

    protected Supplier<DocumentElement> paragraphOf(Supplier<List<DocumentElement>> content) {
        return () -> factory.createParagraph(content.get());
    }
    
    @SafeVarargs
    protected final Supplier<List<DocumentElement>> sequenceOf(Supplier<List<DocumentElement>>... parts) {
        return () -> {
            List<DocumentElement> outParts = new ArrayList<>();
            for (Supplier<List<DocumentElement>> part : parts) {
                outParts.addAll(part.get());
            }
            return outParts;
        }; 
    }

    @SafeVarargs
    protected final Supplier<List<DocumentElement>> anyOf(Supplier<List<DocumentElement>>... alternatives) {
        return () -> {
            int index = random.nextInt(alternatives.length);
            return alternatives[index].get();
        };
    }

    protected final Supplier<List<DocumentElement>> optional(Supplier<List<DocumentElement>> part) {
        return () -> random.nextBoolean() ? part.get() : Collections.emptyList();
    }
    
    protected final Supplier<List<DocumentElement>> sentence(Supplier<DocumentElement> sentenceSupplier) {
        return () -> Arrays.asList(sentenceSupplier.get());
    }
    
}
