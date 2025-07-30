package hu.webarticum.inno.paperdatabase.abstractgenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;

public abstract class AbstractTextTemplateGenerator implements TextTemplateGenerator {
    
    protected final NLGFactory factory;

    protected final Supplier<Random> randomSupplier;
    
    protected Random random = null;
    
    protected AbstractTextTemplateGenerator(NLGFactory factory, Supplier<Random> randomSupplier) {
        this.factory = factory;
        this.randomSupplier = randomSupplier;
    }
    
    @Override
    public NLGElement generateTemplate() {
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
