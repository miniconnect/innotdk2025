package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;
import simplenlg.features.Feature;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseCategory;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

public abstract class TextGeneratorBase {
    
    private static final String FALLBACK_WORD = "buzz";

    protected final NLGFactory factory;
    
    protected final Realiser realiser;
    
    private final long seed;
    
    protected Random random = null;
    
    protected final Map<String, String> sharedWordSubstitutionMap;
    
    protected TextGeneratorBase(
            Map<String, PlaceholderType> sharedPlaceholdersSpec,
            NLGFactory factory,
            Realiser realiser,
            WordGenerator wordGenerator,
            long seed) {
        this.factory = factory;
        this.realiser = realiser;
        this.seed = seed;
        this.sharedWordSubstitutionMap = buildSubstitutionMap(sharedPlaceholdersSpec, wordGenerator, seed);
    }
    
    private static Map<String, String> buildSubstitutionMap(
            Map<String, PlaceholderType> sharedPlaceholdersSpec,
            WordGenerator wordGenerator,
            long seed) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, PlaceholderType> entry : sharedPlaceholdersSpec.entrySet()) {
            String key = entry.getKey();
            PlaceholderType placeholderType = entry.getValue();
            String word = wordGenerator.generate(placeholderType);
            result.put(key, word);
        }
        return result;
    }

    protected String generate(Supplier<? extends NLGElement> generatorStructure) {
        return realiser.realise(buildNlgElement(generatorStructure)).getRealisation().trim();
    }

    protected static Random createRandom(long seed) {
        Random random = new Random(seed);
        // FIXME: warmup
        for (int i = 0; i < 5; i++) {
            random.nextLong();
        }
        return random;
    }

    private NLGElement buildNlgElement(Supplier<? extends NLGElement> generatorStructure) {
        this.random = createRandom(this.seed * 7823L);
        NLGElement result = generatorStructure.get();
        this.random = null;
        return result;
    }
    
    protected String shared(String key) {
        return sharedWordSubstitutionMap.getOrDefault(key, FALLBACK_WORD);
    }
    
    protected <T> T choose(Random random, @SuppressWarnings("unchecked") T... items) {
        int choosenIndex = random.nextInt(items.length);
        return items[choosenIndex];
    }
    
    protected NLGElement createElement(String value, PhraseCategory phraseCategory, NLGFactory factory) {
        NLGElement element = factory.createStringElement(value);
        element.setCategory(phraseCategory);
        return element;
    }

    protected Supplier<NLGElement> paragraphOf(Supplier<List<NLGElement>> content) {
        return () -> factory.createParagraph(documentElements(content.get()));
    }

    protected SPhraseSpec pastTense(SPhraseSpec phrase) {
        phrase.setFeature(Feature.PRONOMINAL, true);
        return phrase;
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
