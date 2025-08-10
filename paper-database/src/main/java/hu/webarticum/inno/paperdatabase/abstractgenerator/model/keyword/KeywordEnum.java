package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import java.util.Arrays;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;

public enum KeywordEnum {
    
    /*
    ai: INFORMATICS, ENGINEERING, MEDICINE, BIOLOGY, PHYSICS
    algorithms: INFORMATICS, MATHEMATICS, ENGINEERING
    astronomical instrumentation: ASTRONOMY, ENGINEERING
    astrophysics: ASTRONOMY, PHYSICS
    bioinformatics: BIOLOGY, INFORMATICS, MEDICINE
    catalysis: CHEMISTRY, ENGINEERING, BIOLOGY
    clinical informatics: MEDICINE, INFORMATICS
    compilers: INFORMATICS, ENGINEERING
    computer vision: INFORMATICS, ENGINEERING, MEDICINE
    condensed matter: PHYSICS, CHEMISTRY
    control theory: ENGINEERING, MATHEMATICS, INFORMATICS
    cryptography: INFORMATICS, MATHEMATICS, ENGINEERING
    data privacy: INFORMATICS, MEDICINE, ENGINEERING
    data structures: INFORMATICS, MATHEMATICS
    databases: INFORMATICS, ENGINEERING
    distributed systems: INFORMATICS, ENGINEERING
    electrochemistry: CHEMISTRY, PHYSICS, ENGINEERING
    embedded systems: ENGINEERING, INFORMATICS
    epidemiology modeling: MEDICINE, MATHEMATICS, INFORMATICS
    exoplanets: ASTRONOMY, PHYSICS, INFORMATICS
    formal verification: INFORMATICS, MATHEMATICS, ENGINEERING
    genomics: BIOLOGY, MEDICINE, INFORMATICS
    graph theory: MATHEMATICS, INFORMATICS, PHYSICS
    machine learning: INFORMATICS, MEDICINE, BIOLOGY, PHYSICS
    materials modeling: PHYSICS, CHEMISTRY, ENGINEERING
    medical imaging: MEDICINE, INFORMATICS, PHYSICS
    molecular modeling: CHEMISTRY, BIOLOGY, PHYSICS
    nlp: INFORMATICS, MEDICINE
    neuroscience: BIOLOGY, MEDICINE, INFORMATICS
    numerical analysis: MATHEMATICS, PHYSICS, ENGINEERING
    optimization: MATHEMATICS, INFORMATICS, ENGINEERING
    operating systems: INFORMATICS, ENGINEERING
    probability theory: MATHEMATICS, PHYSICS, INFORMATICS
    proteomics: BIOLOGY, MEDICINE, CHEMISTRY
    quantum computing: PHYSICS, INFORMATICS, MATHEMATICS
    reliability engineering: ENGINEERING, INFORMATICS
    robotics: ENGINEERING, INFORMATICS
    signal processing: ENGINEERING, PHYSICS, MEDICINE
    spectroscopy: CHEMISTRY, PHYSICS, MEDICINE
    systems biology: BIOLOGY, MEDICINE, INFORMATICS
    time-series analysis: INFORMATICS, ASTRONOMY, MEDICINE, ENGINEERING
    */
    
    AI("ai",
            typeCase(PlaceholderType.AREA,
                    new String[] { "plucking", "marthrement", "ketorny-harness" },
                    new String[] { "[A-Z]{3}\\d\\-(mache|hitxe)" }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] { "terribloxon", "horronity", "pruff" },
                    new String[] { "[XY]\\-crash" }),
            typeCase(PlaceholderType.METHOD,
                    new String[] { "analyzer", "LLM", "evolutionary technique" },
                    new String[] { "[A-Z]{3}-analysis" }),
            typeCase(PlaceholderType.ITEM,
                    new String[] { "model", "robot" },
                    new String[] { "NLV-\\d{3}" }),
            typeCase(PlaceholderType.VERB,
                    new String[] { "speak", "listen" },
                    new String[] { "[XYZ]-match" })),
    
    ALGORITHMS("algorithms",
            typeCase(PlaceholderType.AREA,
                    new String[] { "calculation", "doxrting" },
                    new String[] { "p[aei]fking" }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] { "EVIL", "DEVIL" },
                    new String[] { "X\\d{2}\\-ouch" }),
            typeCase(PlaceholderType.METHOD,
                    new String[] { "algorithm", "method", "analiser strategy" },
                    new String[] { "[A-Z]{3}\\-method" }),
            typeCase(PlaceholderType.ITEM,
                    new String[] { "thing", "item" },
                    new String[] { "[ABC]\\-agent" }),
            typeCase(PlaceholderType.VERB,
                    new String[] { "calculate", "move" },
                    new String[] { "[A-Z]\\d\\-create" })),
    
    ;
    
    private final Keyword keyword;
    
    private KeywordEnum(String name, PlaceholderTypeWordsSpec... placeholderTypeWordsSpecs) {
        this.keyword = new DefaultKeyword(name, Arrays.asList(placeholderTypeWordsSpecs));
    }
    
    public Keyword keyword() {
        return keyword;
    }
    
    private static PlaceholderTypeWordsSpec typeCase(PlaceholderType placeholderType, String[] fixedStrings, String[] regexes) {
        return new PlaceholderTypeWordsSpec(placeholderType, fixedStrings, regexes);
    }

}
