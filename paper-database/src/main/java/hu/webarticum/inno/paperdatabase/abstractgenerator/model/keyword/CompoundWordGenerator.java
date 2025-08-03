package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;

public class CompoundWordGenerator implements WordGenerator {
    
    private final List<WordGenerator> innerWordGenerators;
    
    private final Random random;
    
    public CompoundWordGenerator(List<WordGenerator> innerWordGenerators, long seed) {
        this.innerWordGenerators = new ArrayList<>(innerWordGenerators);
        this.random = new Random(seed);
    }

    @Override
    public String generate(PlaceholderType placeholderType) {
        int choosenIndex = random.nextInt(innerWordGenerators.size());
        WordGenerator wordGenerator = innerWordGenerators.get(choosenIndex);
        return wordGenerator.generate(placeholderType);
    }

}
