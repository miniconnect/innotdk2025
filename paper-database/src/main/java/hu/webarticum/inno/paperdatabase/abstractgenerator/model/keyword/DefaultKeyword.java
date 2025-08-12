package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hu.webarticum.holodb.regex.facade.MatchList;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;
import hu.webarticum.miniconnect.lang.LargeInteger;

public class DefaultKeyword implements Keyword {

    private static final Map<String, MatchList> matchListCache = Collections.synchronizedMap(new HashMap<>());

    private final String label;

    private final String longLabel;
    
    private final Map<PlaceholderType, String[][]> possibilitiesPerType;
    
    public DefaultKeyword(String label, String longLabel, List<PlaceholderTypeWordsSpec> placeholderTypeWordsSpecs) {
        this.label = label;
        this.longLabel = longLabel;
        this.possibilitiesPerType = new EnumMap<>(PlaceholderType.class);
        for (PlaceholderTypeWordsSpec placeholderTypeWordsSpec : placeholderTypeWordsSpecs) {
            this.possibilitiesPerType.put(
                    placeholderTypeWordsSpec.placeholderType,
                    new String[][] {
                        copyOf(placeholderTypeWordsSpec.fixedStrings),
                        copyOf(placeholderTypeWordsSpec.regexes) });
        }
    }
    
    private static String[] copyOf(String[] strings) {
        return Arrays.copyOf(strings, strings.length);
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public String longLabel() {
        return longLabel;
    }

    @Override
    public WordGenerator wordGenerator(long seed) {
        return new DefaultWordGenerator(seed);
    }
    
    private class DefaultWordGenerator implements WordGenerator {
        
        private final Random random;
        
        private DefaultWordGenerator(long seed) {
            this.random = new Random(seed);
            // warmup
            for (int i = 0; i < 5; i++) {
                this.random.nextLong();
            }
        }
        
        @Override
        public String generate(PlaceholderType placeholderType) {
            String[][] possibilities = possibilitiesPerType.get(placeholderType);
            if (possibilities == null) {
                return placeholderType.defaultWord();
            }

            String[] fixedWords = possibilities[0];
            String[] regexes = possibilities[1];
            int fullLength = fixedWords.length + regexes.length;
            int overallIndex = random.nextInt(fullLength);
            if (overallIndex < fixedWords.length) {
                return fixedWords[overallIndex];
            }
            
            int regexIndex = overallIndex - fixedWords.length;
            String regex = regexes[regexIndex];
            MatchList matchList = matchListCache.computeIfAbsent(regex, MatchList::of);
            LargeInteger matchIndex = matchList.size().random(random);
            return matchList.get(matchIndex);
        }
        
    }

}
