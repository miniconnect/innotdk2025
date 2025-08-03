package hu.webarticum.inno.paperdatabase.abstractgenerator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

public abstract class TextGeneratorBase {

    protected final NLGFactory factory;
    
    protected final Realiser realiser;
    
    protected final Supplier<Random> randomSupplier;
    
    protected Random random = null;
    
    protected TextGeneratorBase(NLGFactory factory, Realiser realiser, Supplier<Random> randomSupplier) {
        this.factory = factory;
        this.realiser = realiser;
        this.randomSupplier = randomSupplier;
    }
    
    public String generate(Supplier<? extends NLGElement> generatorStructure) {
        return realiser.realise(buildNlgElement(generatorStructure)).getRealisation().trim();
    }
    
    private NLGElement buildNlgElement(Supplier<? extends NLGElement> generatorStructure) {
        this.random = randomSupplier.get();
        NLGElement result = generatorStructure.get();
        this.random = null;
        return result;
    }

    protected Supplier<NLGElement> paragraphOf(Supplier<List<NLGElement>> content) {
        return () -> factory.createParagraph(documentElements(content.get()));
    }
    
    private List<DocumentElement> documentElements(List<NLGElement> nlgElements) {
        List<DocumentElement> result = new ArrayList<>(nlgElements.size());
        for (NLGElement nlgElement : nlgElements) {
            if (nlgElement instanceof DocumentElement) {
                result.add((DocumentElement) nlgElement);
            } else {
                result.add(factory.createSentence(nlgElement));
            }
        }
        return result;
    }

    @SafeVarargs
    protected final Supplier<List<NLGElement>> sequenceOf(Supplier<List<NLGElement>>... parts) {
        return () -> {
            List<NLGElement> outParts = new ArrayList<>();
            for (Supplier<List<NLGElement>> part : parts) {
                outParts.addAll(part.get());
            }
            return outParts;
        }; 
    }

    @SafeVarargs
    protected final Supplier<List<NLGElement>> anyOf(Supplier<List<NLGElement>>... alternatives) {
        return () -> {
            int index = random.nextInt(alternatives.length);
            return alternatives[index].get();
        };
    }

    protected final Supplier<List<NLGElement>> optional(Supplier<List<NLGElement>> part) {
        return () -> random.nextBoolean() ? part.get() : Collections.emptyList();
    }
    
    protected final Supplier<List<NLGElement>> sentence(Supplier<NLGElement> sentenceSupplier) {
        return () -> Arrays.asList(sentenceSupplier.get());
    }
    
}
