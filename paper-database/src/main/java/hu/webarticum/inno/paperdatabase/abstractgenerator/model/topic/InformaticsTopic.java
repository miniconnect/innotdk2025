package hu.webarticum.inno.paperdatabase.abstractgenerator.model.topic;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PaperTextsResult;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Topic;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.KeywordEnum;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword.WordGenerator;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class InformaticsTopic implements Topic {
    
    private static final Keyword[] primaryKeywords = {
            KeywordEnum.AI.keyword(),
            KeywordEnum.ALGORITHMS.keyword(),
            KeywordEnum.COMPILERS.keyword(),
            KeywordEnum.COMPUTER_VIS.keyword(),
            KeywordEnum.CRYPTOGRAPHY.keyword(),
            KeywordEnum.DATA_PRIVACY.keyword(),
            KeywordEnum.DATA_STRUCTS.keyword(),
            KeywordEnum.DATABASES.keyword(),
            KeywordEnum.DISTRIBUTED.keyword(),
            KeywordEnum.VERIFICATION.keyword(),
            KeywordEnum.ML.keyword(),
            KeywordEnum.NLP.keyword(),
            KeywordEnum.OS.keyword(),
            KeywordEnum.T_SERIES.keyword(),
    };

    private static final Keyword[] secondaryKeywords = {
            KeywordEnum.EMBEDDED.keyword(),
            KeywordEnum.GRAPH_THEORY.keyword(),
            KeywordEnum.OPTIMIZATION.keyword(),
            KeywordEnum.PROBABILITY.keyword(),
            KeywordEnum.QC.keyword(),
            KeywordEnum.RELIABILITY.keyword(),
    };
    
    private final NLGFactory factory;
    
    private final Realiser realiser;
    
    public InformaticsTopic(NLGFactory factory, Realiser realiser) {
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

        private static final String P_POPULAR_TOOL = "popular-tool";
        private static final String P_AREA = "area";
        private static final String P_PRODUCE = "produce";
        private static final String P_TARGET_PRODUCT = "target-product";
        private static final String P_METHOD = "method";
        private static final String P_DIFFICULTY = "difficulty";
        private static final String P_PROBLEMATIC_STUFF = "problematic-stuff";
        
        private static final Map<String, PlaceholderType> PRIMARY_PLACEHOLDERS = new HashMap<>();
        static {
            PRIMARY_PLACEHOLDERS.put(P_POPULAR_TOOL, PlaceholderType.ITEM);
            PRIMARY_PLACEHOLDERS.put(P_AREA, PlaceholderType.AREA);
            PRIMARY_PLACEHOLDERS.put(P_TARGET_PRODUCT, PlaceholderType.ITEM);
            PRIMARY_PLACEHOLDERS.put(P_PROBLEMATIC_STUFF, PlaceholderType.ITEM);
        }

        private static final Map<String, PlaceholderType> SECONDARY_PLACEHOLDERS = new HashMap<>();
        static {
            SECONDARY_PLACEHOLDERS.put(P_PRODUCE, PlaceholderType.VERB);
            SECONDARY_PLACEHOLDERS.put(P_METHOD, PlaceholderType.METHOD);
            SECONDARY_PLACEHOLDERS.put(P_DIFFICULTY, PlaceholderType.DIFFICULTY);
        }
        
        protected TopicTextGenerator(
                WordGenerator primaryWordGenerator, WordGenerator secondaryWordGenerator, NLGFactory factory, Realiser realiser, long seed) {
            super(PRIMARY_PLACEHOLDERS, SECONDARY_PLACEHOLDERS, factory, realiser, primaryWordGenerator, secondaryWordGenerator, seed);
        }

        public String generateTitle() {
            return generate(this::titleStructure);
        }

        private NLGElement titleStructure() {
            NPPhraseSpec mainPhrase = factory.createNounPhrase("Generation");
            
            NPPhraseSpec subjectPhrase = factory.createNounPhrase("instances");
            subjectPhrase.setPlural(true);
            subjectPhrase.addPreModifier("<XXXX>"); // FIXME
            PPPhraseSpec preposition = factory.createPrepositionPhrase("of", subjectPhrase);
            mainPhrase.addPostModifier(preposition);
            
            PPPhraseSpec usingPhrase = factory.createPrepositionPhrase("using", "<XXXX>"); // FIXME
            mainPhrase.addPostModifier(usingPhrase);
            
            return mainPhrase;
        }

        public String generateAbstract() {
            return generate(paragraphOf(sequenceOf(
                    optional(anyOf(sentence(this::contextSentenceNormalA), sentence(this::contextSentenceNormalB))),
                    anyOf(sentence(this::introSentenceWe), sentence(this::introSentencePassive), sentence(this::introSentencePaper)),
                    optional(anyOf(sentence(this::detailsSentenceA), sentence(this::detailsSentenceB))),
                    anyOf(sentence(this::resultsSentenceA), sentence(this::resultsSentenceB)))));
        }

        private DocumentElement contextSentenceNormalA() {
            NPPhraseSpec objects = factory.createNounPhrase(factory.createWord(shared(P_POPULAR_TOOL), LexicalCategory.NOUN));
            objects.setPlural(true);
            NPPhraseSpec targetArea = factory.createNounPhrase(factory.createWord(shared(P_AREA), LexicalCategory.NOUN));
            if (random.nextBoolean()) {
                PPPhraseSpec ofArea = factory.createPrepositionPhrase("of", targetArea);
                targetArea = factory.createNounPhrase("the", choose(random, "area", "field"));
                targetArea.addPostModifier(ofArea);
            }
            SPhraseSpec clause = createContextClauseNormalA(objects);
            PPPhraseSpec inField = factory.createPrepositionPhrase("in", targetArea);
            clause.addPostModifier(inField);
            if (random.nextBoolean()) {
                String modifier = choose(random, "currently", "in recent years", "nowadays", "recently");
                String optionalComma = random.nextBoolean() ? "," : "";
                clause.addFrontModifier(modifier + optionalComma);
            }
            return factory.createSentence(clause);
        }
        
        private SPhraseSpec createContextClauseNormalA(NPPhraseSpec objects) {
            if (random.nextBoolean()) {
                return createContextClauseNormalAPlay(objects);
            } else {
                return createContextClauseNormalAUsed(objects);
            }
        }

        private SPhraseSpec createContextClauseNormalAPlay(NPPhraseSpec objects) {
            NPPhraseSpec qualifiedRole = factory.createNounPhrase("a", "role");
            qualifiedRole.addPreModifier(choose(random, "increasingly important", "crucial"));
            SPhraseSpec clause = factory.createClause(objects, "play", qualifiedRole);
            clause.setFeature(Feature.PROGRESSIVE, random.nextInt(4) != 0);
            return clause;
        }

        private SPhraseSpec createContextClauseNormalAUsed(NPPhraseSpec objects) {
            String statement = choose(random, "commonly used", "essential", "integral to current methods", "widely applied");
            return factory.createClause(objects, "are", statement);
        }

        private DocumentElement contextSentenceNormalB() {
            NPPhraseSpec progressNoun;
            if (random.nextBoolean()) {
                progressNoun = factory.createNounPhrase(choose(random, "growth", "progress"));
            } else {
                progressNoun = factory.createNounPhrase(choose(random, "advances", "innovations", "developments"));
                progressNoun.setPlural(true);
            }
            if (random.nextBoolean()) {
                progressNoun.setSpecifier(choose(random, "recent", "the"));
            }
            PPPhraseSpec inArea = factory.createPrepositionPhrase("in", factory.createWord(shared(P_AREA), LexicalCategory.NOUN));
            progressNoun.addPostModifier(inArea);
            String verb = choose(random, "bring", "entail", "expose", "generate", "introduce");
            String adjective = choose(random, "emerging", "new", "novel", "significant", "unforeseen");
            String problemName = choose(random, "challenge", "issue", "obstacle", "problem");
            NPPhraseSpec problemNoun = factory.createNounPhrase(adjective, problemName);
            problemNoun.setPlural(true);
            return factory.createSentence(factory.createClause(progressNoun, verb, problemNoun));
        }

        private DocumentElement introSentenceWe() {
            SPhraseSpec content = factory.createClause("we", chooseProposeVerb(), createProposalWithForPhrase());
            if (random.nextInt(4) == 0) {
                content.addComplement(createInThisPaperPreposition());
            }
            return factory.createSentence(content);
        }
        
        private DocumentElement introSentencePassive() {
            SPhraseSpec content = factory.createClause();
            content.setVerb(chooseProposeVerb());
            content.setFeature(Feature.PASSIVE, true);
            content.setFeature(Feature.TENSE, Tense.PRESENT);
            NPPhraseSpec proposalBase = createProposalBasePhrase();
            content.setObject(proposalBase);
            PPPhraseSpec preposition = createInThisPaperPreposition();
            if (random.nextBoolean()) {
                content.addFrontModifier(preposition);
                if (random.nextBoolean()) {
                    content.addPostModifier(createProposalFor());
                }
            } else {
                content.addPostModifier(preposition);
            }
            return factory.createSentence(content);
        }

        private DocumentElement introSentencePaper() {
            SPhraseSpec content = factory.createClause(createThisPaperPhrase(), chooseProposeVerb(), createProposalWithForPhrase());
            return factory.createSentence(content);
        }

        private PPPhraseSpec createInThisPaperPreposition() {
            return factory.createPrepositionPhrase("in", createThisPaperPhrase());
        }
        
        private NPPhraseSpec createThisPaperPhrase() {
            String specifier = "this";
            if (random.nextInt(4) == 0) {
                specifier = choose(random, "the", "our");
            }
            String name = "paper";
            if (random.nextInt(4) == 0) {
                name = choose(random, "article", "contribution", "publication", "report", "research", "work");
            }
            NPPhraseSpec phrase = factory.createNounPhrase(specifier, name);
            if (!specifier.equals("this") && random.nextInt(4) == 0) {
                phrase.addPreModifier(choose(random, "current", "present"));
            }
            return phrase;
        }

        private String chooseProposeVerb() {
            return choose(random, "describe", "introduce", "outline", "present", "propose");
        }

        private NLGElement createProposalWithForPhrase() {
            NPPhraseSpec mainPhrase = createProposalBasePhrase();
            mainPhrase.addPostModifier(createProposalFor());
            return mainPhrase;
        }
        
        private PPPhraseSpec createProposalFor() {
            NLGElement gerundPhrase = createProposalForGerund();
            PPPhraseSpec forPhrase = factory.createPrepositionPhrase();
            forPhrase.setPreposition("for");
            forPhrase.addComplement(gerundPhrase);
            return forPhrase;
        }

        private VPPhraseSpec createProposalForGerund() {
            VPPhraseSpec gerundPhrase;
            if (random.nextBoolean()) {
                gerundPhrase = factory.createVerbPhrase(choose(random, "analyze", "create", "generate", "produce"));
            } else {
                gerundPhrase = factory.createVerbPhrase(factory.createWord(shared(P_PRODUCE), LexicalCategory.VERB));
            }
            gerundPhrase.setFeature(Feature.FORM, Form.GERUND);
            NPPhraseSpec targetNounPhrase = factory.createNounPhrase(factory.createWord(shared(P_TARGET_PRODUCT), LexicalCategory.NOUN));
            targetNounPhrase.setPlural(true);
            gerundPhrase.addComplement(targetNounPhrase);
            return gerundPhrase;
        }

        private NPPhraseSpec createProposalBasePhrase() {
            NPPhraseSpec phrase = factory.createNounPhrase("a", factory.createWord(shared(P_METHOD), LexicalCategory.NOUN));
            if (random.nextBoolean()) {
                phrase.addPreModifier(choose(random, "efficient", "new", "novel", "revolutionary"));
            }
            return phrase;
        }

        private DocumentElement detailsSentenceA() {
            NPPhraseSpec problemPhrase = factory.createNounPhrase("the", choose(random, "difficulty", "problem", "challenge"));
            problemPhrase.addPostModifier(factory.createPrepositionPhrase("of", factory.createWord(shared(P_DIFFICULTY), LexicalCategory.NOUN)));
            VPPhraseSpec occurPhrase = factory.createVerbPhrase(choose(random, "appear", "arise", "emerge", "occur"));
            occurPhrase.setFeature(Feature.FORM, Form.PRESENT_PARTICIPLE);
            occurPhrase.setPreModifier("often");
            NPPhraseSpec problematicNounPhrase = factory.createNounPhrase(factory.createWord(shared(P_PROBLEMATIC_STUFF), LexicalCategory.NOUN));
            problematicNounPhrase.setPlural(true);
            occurPhrase.addPostModifier(factory.createPrepositionPhrase("with", problematicNounPhrase));
            
            problemPhrase.addPostModifier(occurPhrase);
            
            VPPhraseSpec verbPhrase;
            SPhraseSpec mainClause;
            if (random.nextBoolean()) {
                verbPhrase = factory.createVerbPhrase("solve");
                mainClause= factory.createClause("we", verbPhrase, problemPhrase);
            } else {
                verbPhrase = factory.createVerbPhrase("deal");
                mainClause = factory.createClause("we", verbPhrase);
                PPPhraseSpec withPrepositionPhrase = factory.createPrepositionPhrase("with", problemPhrase);
                mainClause.addPostModifier(withPrepositionPhrase);
            }
            verbPhrase.addPreModifier("also " + choose(random, "effectively", "efficiently", "reliably", "robustly", "successfully"));
            mainClause.setFeature(Feature.TENSE, Tense.PAST);

            return factory.createSentence(mainClause);
        }

        private DocumentElement detailsSentenceB() {
            NPPhraseSpec challengePhrase = factory.createNounPhrase("a", choose(random, "challenge", "difficulty"));
            challengePhrase.addPreModifier(choose(random, "additional", "further"));
            SPhraseSpec mainClause = createChallengeLiesPhrase(challengePhrase);
            PhraseElement besidesCoreInitial = createBesidesCoreInitial();
            besidesCoreInitial.addPostModifier(",");
            mainClause.addFrontModifier(besidesCoreInitial);
            return factory.createSentence(mainClause);
        }
        
        private SPhraseSpec createChallengeLiesPhrase(NPPhraseSpec challengePhrase) {
            NPPhraseSpec problemPhrase = factory.createNounPhrase("the", "problem");
            PPPhraseSpec ofPhrase = factory.createPrepositionPhrase("of", factory.createWord(shared(P_DIFFICULTY), LexicalCategory.NOUN));
            problemPhrase.addPostModifier(ofPhrase);
            String[] verbAndPreposition = choose(
                    random, 
                    "arise from", "come from", "emerge from", "lie in", "reside in"
                    ).split(" ");
            PPPhraseSpec prepositionPhrase = factory.createPrepositionPhrase(verbAndPreposition[1], problemPhrase);
            SPhraseSpec mainClause = factory.createClause(challengePhrase, verbAndPreposition[0]);
            mainClause.addPostModifier(prepositionPhrase);
            return mainClause;
        }
        
        private PhraseElement createBesidesCoreInitial() {
            if (random.nextInt(4) == 0) {
                return factory.createAdverbPhrase("also");
            }
            NPPhraseSpec problemPhrase = factory.createNounPhrase("the", choose(random, "concern", "issue", "problem"));
            problemPhrase.addPreModifier(choose(random, "central", "core", "key", "main"));
            return factory.createPrepositionPhrase(choose(random, "besides", "beyond"), problemPhrase);
        }

        private DocumentElement resultsSentenceA() {
            SPhraseSpec mainClause = createResultSentenceAMainClause();
            if (random.nextBoolean()) {
                return factory.createSentence(mainClause);
            } else if (random.nextBoolean()) {
                SPhraseSpec framingClause = createResultShowingPreClause();
                framingClause.setObject(mainClause);
                return factory.createSentence(framingClause);
            } else if (random.nextBoolean()) {
                NPPhraseSpec resultNoun = createResultShowingPreNoun();
                PPPhraseSpec onPhrase = factory.createPrepositionPhrase("on", resultNoun);
                VPPhraseSpec basedOnPhrase = factory.createVerbPhrase("base");
                basedOnPhrase.setFeature(Feature.TENSE, Tense.PAST);
                basedOnPhrase.addPostModifier(onPhrase);
                basedOnPhrase.addPostModifier(",");
                mainClause.setFrontModifier(basedOnPhrase);
                return factory.createSentence(mainClause);
            } else {
                SPhraseSpec framingClause = createResultShowingPreClause();
                framingClause.setFeature(Feature.TENSE, Tense.PAST);
                framingClause.addFrontModifier("as");
                CoordinatedPhraseElement connectedPhrase = factory.createCoordinatedPhrase(framingClause, mainClause);
                connectedPhrase.setConjunction(",");
                return factory.createSentence(connectedPhrase);
            }
        }
        
        private SPhraseSpec createResultSentenceAMainClause() {
            NPPhraseSpec nounPhrase = createResultApproachNounPhrase();
            String verb = choose(random, "achieve", "show");
            NLGElement achievementNounPhrase = createResultImprovementPhrase();
            SPhraseSpec mainClause = factory.createClause(nounPhrase, verb, achievementNounPhrase);
            VPPhraseSpec basedOnPhrase = createResultComparedToPhrase();
            mainClause.addPostModifier(basedOnPhrase);
            return mainClause;
        }
        
        private NLGElement createResultImprovementPhrase() {
            if (random.nextBoolean()) {
                return createResultIncreaseNoun();
            } else if (random.nextBoolean()) {
                return createResultDecreaseNoun();
            } else {
                NPPhraseSpec increaseNoun = createResultIncreaseNoun();
                NPPhraseSpec decreaseNoun = createResultDecreaseNoun();
                CoordinatedPhraseElement connectedPhrase = factory.createCoordinatedPhrase(increaseNoun, decreaseNoun);
                connectedPhrase.setConjunction("and");
                return connectedPhrase;
            }
        }

        private NPPhraseSpec createResultIncreaseNoun() {
            String increaseName = choose(random, "advancement", "enhancement", "growth", "increase", "improvement");
            String increaseSubject = choose(random, "efficiency", "performance", "speed");
            return createResultAnyImprovementNoun(increaseName, increaseSubject);
        }

        private NPPhraseSpec createResultDecreaseNoun() {
            String decreaseName = choose(random, "decrease", "reduction");
            String decreaseSubject = choose(random, "execution time", "memory use", "storage");
            return createResultAnyImprovementNoun(decreaseName, decreaseSubject);
        }
        
        private NPPhraseSpec createResultAnyImprovementNoun(String improvementName, String improvementSubject) {
            NPPhraseSpec increaseNounPhrase = factory.createNounPhrase("a", improvementName);
            if (random.nextBoolean()) {
                increaseNounPhrase.addPreModifier(choose(random, "major", "serious", "significant"));
            } else if (random.nextBoolean()) {
                String increasePercent = (random.nextInt(120) + 20) + "%";
                increaseNounPhrase.addPreModifier(increasePercent);
            } else {
                String increaseFactor = (random.nextInt(12) + 2) + "";
                if (random.nextInt(4) == 0) {
                    increaseFactor += ".5";
                }
                NPPhraseSpec factorNounPhrase = factory.createNounPhrase("a", "factor");
                PPPhraseSpec ofPhrase = factory.createPrepositionPhrase("of", increaseFactor);
                factorNounPhrase.addPostModifier(ofPhrase);
                PPPhraseSpec byPhrase = factory.createPrepositionPhrase("by", factorNounPhrase);
                increaseNounPhrase.addPostModifier(byPhrase);
            }
            PPPhraseSpec inPhrase = factory.createPrepositionPhrase("in", improvementSubject);
            increaseNounPhrase.addPostModifier(inPhrase);
            return increaseNounPhrase;
        }

        private VPPhraseSpec createResultComparedToPhrase() {
            String adjective = choose(random, "former", "old");
            String noun = choose(random,  "algorithm", "approach", "implementation", "method");
            NPPhraseSpec comparedNounPhrase = factory.createNounPhrase(adjective, noun);
            comparedNounPhrase.setPlural(true);
            PPPhraseSpec toPhrase = factory.createPrepositionPhrase("to", comparedNounPhrase);
            VPPhraseSpec comparedToPhrase = factory.createVerbPhrase("compare");
            comparedToPhrase.setFeature(Feature.TENSE, Tense.PAST);
            comparedToPhrase.addPostModifier(toPhrase);
            return comparedToPhrase;
        }
        
        private SPhraseSpec createResultShowingPreClause() {
            String verb = choose(random, "demonstrate", "show", "suggest");
            return factory.createClause(createResultShowingPreNoun(), verb);
        }
        
        private NPPhraseSpec createResultShowingPreNoun() {
            String specifier = choose(random, "detailed", "early", "initial", "our", "the");
            boolean plural = random.nextBoolean();
            String nounName = plural ?
                    choose(random, "results", "measurements", "experiments", "findings", "tests", "observations", "outcomes") :
                    choose(random, "analysis", "assessment", "comparison", "evaluation", "investigation", "profiling", "study");
            NPPhraseSpec nounPhrase = factory.createNounPhrase(specifier, nounName);
            nounPhrase.setPlural(plural);
            return nounPhrase;
        }

        private DocumentElement resultsSentenceB() {
            SPhraseSpec frontClause = createShownFrontClause();
            frontClause.setFrontModifier("as");
            frontClause.setPostModifier(",");
            NPPhraseSpec approachNounPhrase = createResultApproachNounPhrase();
            String[] verbAndPreposition = choose(
                    random, 
                    "adapt to", "align with", "integrate into", "fit with", "plug into", "work with"
                    ).split(" ");
            VPPhraseSpec verbPhrase = factory.createVerbPhrase(verbAndPreposition[0]);
            verbPhrase.addModifier(choose(random, "smoothly", "well"));
            SPhraseSpec mainClause = factory.createClause(approachNounPhrase, verbPhrase);
            String existingName = choose(random, "common", "existing", "popular");
            String workflowName = choose(random, "methodology", "pipeline", "system", "workflow");
            NPPhraseSpec workflowNounPhrase = factory.createNounPhrase(existingName, workflowName);
            workflowNounPhrase.setPlural(true);
            PPPhraseSpec intoPhrase = factory.createPrepositionPhrase(verbAndPreposition[1], workflowNounPhrase);
            mainClause.addPostModifier(intoPhrase);
            CoordinatedPhraseElement coordinatedPhrase = factory.createCoordinatedPhrase(frontClause, mainClause);
            coordinatedPhrase.setConjunction(",");
            return factory.createSentence(coordinatedPhrase);
        }
        
        private SPhraseSpec createShownFrontClause() {
            int possibility = random.nextInt(4);
            if (possibility == 0) {
                return factory.createClause("we", "show");
            } else if (possibility == 1) {
                return createPastClauseWithPrepositionPhrase("demonstrate", "in", "our", "evaluation");
            } else if (possibility == 2) {
                return createPastClauseWithPrepositionPhrase("illustrate", "by", "our", "case study");
            } else {
                return createPastClauseWithPrepositionPhrase("validate", "in", null, "practice");
            }
        }

        private SPhraseSpec createPastClauseWithPrepositionPhrase(String verb, String preposition, String specifier, String noun) {
            VPPhraseSpec verbPhrase = factory.createVerbPhrase(verb);
            verbPhrase.setFeature(Feature.TENSE, Tense.PAST);
            SPhraseSpec frontClause = factory.createClause(null, verbPhrase);
            frontClause.addPostModifier(createPrepositionPhrase(preposition, specifier, noun));
            return frontClause;
        }
        
        private PPPhraseSpec createPrepositionPhrase(String preposition, String specifier, String noun) {
            NPPhraseSpec nounPhrase = factory.createNounPhrase(specifier, noun);
            PPPhraseSpec prepositionPhrase = factory.createPrepositionPhrase(preposition, nounPhrase);
            return prepositionPhrase;
        }

        private NPPhraseSpec createResultApproachNounPhrase() {
            String specifier = choose(random, "the", "our");
            String approachNoun = choose(random, "approach", "implementation", "method");
            NPPhraseSpec nounPhrase = factory.createNounPhrase(specifier, approachNoun);
            if (random.nextBoolean()) {
                nounPhrase.setPreModifier(choose(random, "new", "novel", "latest", "present"));
            }
            return nounPhrase;
        }

    }

}
