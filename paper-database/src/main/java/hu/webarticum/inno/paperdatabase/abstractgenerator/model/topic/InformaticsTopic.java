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
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class InformaticsTopic implements Topic {
    
    private static final String NAME = "informatics";
    
    private static final Keyword[] keywords = {
            KeywordEnum.AI.keyword(),
            KeywordEnum.ALGORITHMS.keyword(),
    };
    
    private final NLGFactory factory;
    
    private final Realiser realiser;
    
    public InformaticsTopic(NLGFactory factory, Realiser realiser) {
        this.factory = factory;
        this.realiser = realiser;
    }
    
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public List<Keyword> keywords() {
        return Collections.unmodifiableList(Arrays.asList(keywords));
    }

    @Override
    public PaperTextsResult buildPaperTextTemplates(WordGenerator wordGenerator, long seed) {
        TopicTextGenerator textGenerator = new TopicTextGenerator(wordGenerator, factory, realiser, seed);
        return new PaperTextsResult(textGenerator.generateTitle(), textGenerator.generateAbstract());
    }
    
    private static class TopicTextGenerator extends TextGeneratorBase {

        // TODO: enumerate all necessary placeholders
        private static final String P_EARLIER_RESEARCHER = "earlier-researcher";
        private static final String P_SOME_OBJECT = "object";
        private static final String P_SOME_OTHER_OBJECT = "other-object";
        
        private static final Map<String, PlaceholderType> PLACEHOLDERS = new HashMap<>();
        static {
            // TODO: specify type for each placeholder
            PLACEHOLDERS.put(P_EARLIER_RESEARCHER, PlaceholderType.TOOL);
            PLACEHOLDERS.put(P_SOME_OBJECT, PlaceholderType.TOOL);
            PLACEHOLDERS.put(P_SOME_OTHER_OBJECT, PlaceholderType.TOOL);
        }
        
        protected TopicTextGenerator(WordGenerator wordGenerator, NLGFactory factory, Realiser realiser, long seed) {
            super(PLACEHOLDERS, factory, realiser, wordGenerator, seed);
        }

        public String generateTitle() {
            return generate(this::titleStructure);
        }

        private NLGElement titleStructure() {
            NPPhraseSpec mainPhrase = factory.createNounPhrase("Generation");
            
            NPPhraseSpec subjectPhrase = factory.createNounPhrase("instances");
            subjectPhrase.setPlural(true);
            subjectPhrase.addPreModifier(shared(P_SOME_OBJECT));
            PPPhraseSpec preposition = factory.createPrepositionPhrase("of", subjectPhrase);
            mainPhrase.addPostModifier(preposition);
            
            PPPhraseSpec usingPhrase = factory.createPrepositionPhrase("using", shared(P_SOME_OTHER_OBJECT));
            mainPhrase.addPostModifier(usingPhrase);
            
            return mainPhrase;
        }

        public String generateAbstract() {
            return generate(paragraphOf(sequenceOf(
                    optional(anyOf(sentence(this::contextSentenceA), sentence(this::contextSentenceB))),
                    anyOf(sentence(this::introSentenceWe), sentence(this::introSentencePassive), sentence(this::introSentencePaper)),
                    optional(anyOf(sentence(this::detailsSentenceA), sentence(this::detailsSentenceB))),
                    anyOf(sentence(this::resultsSentenceA), sentence(this::resultsSentenceB)))));
        }

        private DocumentElement contextSentenceA() {
            // TODO: implement something usable
            return factory.createSentence(pastTense(factory.createClause(shared(P_EARLIER_RESEARCHER), "is", shared(P_SOME_OTHER_OBJECT))));
        }

        private DocumentElement contextSentenceB() {
            // TODO: implement something usable
            return factory.createSentence(pastTense(factory.createClause(shared(P_SOME_OTHER_OBJECT), "is", shared(P_SOME_OTHER_OBJECT))));
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
            if (random.nextInt(8) == 0) {
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
            VPPhraseSpec gerundPhrase = factory.createVerbPhrase(choose(random, "analyze", "create", "generate", "produce")); // TODO: use a placeholder
            gerundPhrase.setFeature(Feature.FORM, Form.GERUND);
            gerundPhrase.addComplement(choose(random, "Feistel-networks", "evolutionary algorithms", "labeled graphs")); // TODO: use a placeholder
            return gerundPhrase;
        }

        private NPPhraseSpec createProposalBasePhrase() {
            NPPhraseSpec phrase = factory.createNounPhrase("a", choose(random, "algorithm", "method", "solution")); // TODO: use a placeholder
            if (random.nextBoolean()) {
                phrase.addPreModifier(choose(random, "efficient", "new", "novel", "revolutionary"));
            }
            return phrase;
        }

        private DocumentElement detailsSentenceA() {
            // TODO: implement something usable
            return factory.createSentence(pastTense(factory.createClause(shared(P_EARLIER_RESEARCHER), "do", shared(P_SOME_OTHER_OBJECT))));
        }

        private DocumentElement detailsSentenceB() {
            // TODO: implement something usable
            return factory.createSentence(pastTense(factory.createClause(shared(P_EARLIER_RESEARCHER), "make", shared(P_SOME_OTHER_OBJECT))));
        }

        private DocumentElement resultsSentenceA() {
            // TODO: implement something usable
            return factory.createSentence(pastTense(factory.createClause(shared(P_EARLIER_RESEARCHER), "end")));
        }

        private DocumentElement resultsSentenceB() {
            // TODO: implement something usable
            return factory.createSentence(pastTense(factory.createClause(shared(P_EARLIER_RESEARCHER), "finish")));
        }

    }

}
