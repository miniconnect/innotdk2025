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
            typeCase(PlaceholderType.SURNAME,
                    new String[] { "Newell", "McCarthy", "Minsky", "Quillian", "Samuel", "Shannon", "Turing", "Wiener" },
                    new String[] { }),
            typeCase(PlaceholderType.INSTITUTE,
                    new String[] { "Dartmouth University", "IBM", "MIT", "NASA", "Stanford University" },
                    new String[] { "[BCDGHT][aeu][dklmrt][elsty] (Institute|Labs|Research|University)" }),
            typeCase(PlaceholderType.ITEM,
                    new String[] { "neural network", "AI agent", "teaching algorithm" },
                    new String[] { "" })),
    
    ALGORITHMS("algorithms",
            typeCase(PlaceholderType.SURNAME,
                    new String[] { "Knuth", "Neumann", "Turing" },
                    new String[] { }),
            typeCase(PlaceholderType.INSTITUTE,
                    new String[] { "MIT", "Princeton University" },
                    new String[] { "[BCDGHT][aeu][dklmrt][elsty] (Institute|Labs|Research|University)" }),
            typeCase(PlaceholderType.ITEM,
                    new String[] { "parallel processing", "local search" },
                    new String[] { "[BCDFGHKLM][aeiou][rstvz]y? algorithm", "[ABKLMNP][AMPT][2-9]? algorithm" })),
    
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
