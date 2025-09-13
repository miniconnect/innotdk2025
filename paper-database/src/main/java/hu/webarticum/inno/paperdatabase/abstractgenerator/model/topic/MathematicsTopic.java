package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextsResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.KeywordEnum;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;
import simplenlg.features.Feature;
import simplenlg.features.LexicalFeature;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class MathematicsTopic implements Topic {

    private static final Keyword[] primaryKeywords = {
            KeywordEnum.GRAPH_THEORY.keyword(),
            KeywordEnum.NUM_ANALYSIS.keyword(),
            KeywordEnum.OPTIMIZATION.keyword(),
            KeywordEnum.PROBABILITY.keyword(),
    };

    private static final Keyword[] secondaryKeywords = {
            KeywordEnum.ALGORITHMS.keyword(),
            KeywordEnum.CRYPTOGRAPHY.keyword(),
    };
    
    private final NLGFactory factory;
    
    private final Realiser realiser;
    
    public MathematicsTopic(NLGFactory factory, Realiser realiser) {
        this.factory = factory;
        this.realiser = realiser;
    }

    @Override
    public List<Keyword> primaryKeywords() {
        return Collections.unmodifiableList(Arrays.asList(primaryKeywords));
    }

    @Override
    public List<Keyword> secondaryKeywords() {
        return Collections.unmodifiableList(Arrays.asList(secondaryKeywords));
    }

    @Override
    public PaperTextsResult buildPaperTextTemplates(WordGenerator primaryWordGenerator, WordGenerator secondaryWordGenerator, long seed) {
        TopicTextGenerator textGenerator = new TopicTextGenerator(primaryWordGenerator, secondaryWordGenerator, factory, realiser, seed);
        return new PaperTextsResult(textGenerator.generateTitle(), textGenerator.generateAbstract());
    }
    
    private static class TopicTextGenerator extends TextGeneratorBase {

        private static final String P_LOREM = "lorem";
        private static final String P_IPSUM = "ipsum";
        
        private static final Map<String, PlaceholderType> PRIMARY_PLACEHOLDERS = new HashMap<>();
        static {
            PRIMARY_PLACEHOLDERS.put(P_LOREM, PlaceholderType.ITEM);
        }

        private static final Map<String, PlaceholderType> SECONDARY_PLACEHOLDERS = new HashMap<>();
        static {
            SECONDARY_PLACEHOLDERS.put(P_IPSUM, PlaceholderType.ITEM);
        }
        
        private final NPPhraseSpec theoremPhrase;
        
        private final boolean theoremIsNewProof;
        
        protected TopicTextGenerator(
                WordGenerator primaryWordGenerator, WordGenerator secondaryWordGenerator, NLGFactory factory, Realiser realiser, long seed) {
            super(PRIMARY_PLACEHOLDERS, SECONDARY_PLACEHOLDERS, factory, realiser, primaryWordGenerator, secondaryWordGenerator, seed);
            Random initRandom = new Random();
            if (initRandom.nextBoolean()) {
                this.theoremPhrase = buildTheoremPhrase(initRandom);
                this.theoremIsNewProof = initRandom.nextBoolean();
            } else {
                this.theoremPhrase = null;
                this.theoremIsNewProof = false;
            }
        }
        
        private NPPhraseSpec buildTheoremPhrase(Random initRandom) {
            String name = generateName(initRandom);
            String type = initRandom.nextInt(4) == 0 ? "Theorem" : "Lemma";
            if (initRandom.nextBoolean()) {
                return factory.createNounPhrase("the", name + " " + type);
            } else {
                return possessive(name, type);
            }
        }
        
        private NPPhraseSpec possessive(String owner, String owned) {
            NPPhraseSpec ownerNounPhrase = factory.createNounPhrase(owner);
            ownerNounPhrase.setFeature(LexicalFeature.PROPER, true);
            ownerNounPhrase.setFeature(Feature.POSSESSIVE, true);
            NPPhraseSpec ownedPhrase = factory.createNounPhrase(owned);
            ownedPhrase.setSpecifier(ownerNounPhrase);
            return ownedPhrase;
        }

        public String generateTitle() {
            return capitalize(generate(firstOf(conditional(
                    theoremPhrase != null,
                    sentence(this::theoremTitle),
                    sentence(this::generalTitle)))));
        }

        private NLGElement theoremTitle() {
            NPPhraseSpec proofNounPhrase = factory.createNounPhrase(choose(random, "establishment", "derivation", "justification", "proof", "verification"));
            if (theoremIsNewProof) {
                proofNounPhrase.addPreModifier(choose(random, "alternative", "new", "novel"));
                if (random.nextBoolean()) {
                    proofNounPhrase.setSpecifier("a");
                }
            }
            proofNounPhrase.setPostModifier(factory.createPrepositionPhrase("for", factory.createWord(theoremPhrase, LexicalCategory.NOUN)));
            return proofNounPhrase;
        }

        private NLGElement generalTitle() {
            return factory.createNounPhrase("Paper");
        }
        
        public String generateAbstract() {
            return generate(paragraphOf(conditional(
                    theoremPhrase != null,
                    sequenceOf(
                            sentence(this::theoremIntroSentence),
                            optional(sentence(this::theoremMiddleSentence)),
                            sentence(this::theoremConsequenceSentence)),
                    sentence(this::generalAbstract))));
        }

        private NLGElement theoremIntroSentence() {
            /*
             * demonstration of Lemma XYZ

derivation of Lemma XYZ

deduction of Lemma XYZ

verification of Lemma XYZ

justification of Lemma XYZ

establishment of Lemma XYZ

formal derivation of Lemma XYZ

sequent-calculus derivation of Lemma XYZ

natural-deduction derivation of Lemma XYZ

             */
            
            
            //return "This is a " + (theoremIsNewProof ? "novel proof" : "proof") + " for the " + theoremName + ".";s
            
            return factory.createClause("derivation", "prove", "theorem");
        }

        private NLGElement theoremMiddleSentence() {
            return factory.createClause("this", "is", "middle");
        }

        private NLGElement theoremConsequenceSentence() {
            return factory.createClause("this", "is", "consequence");
        }
        
        private NLGElement generalAbstract() {
            return factory.createClause("Lorem", "do", "ipsum");
        }
        
    }
    
}
