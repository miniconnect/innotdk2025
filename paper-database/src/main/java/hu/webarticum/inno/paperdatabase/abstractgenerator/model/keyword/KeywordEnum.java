package hu.webarticum.inno.paperdatabase.abstractgenerator.model.keyword;

import java.util.Arrays;

import hu.webarticum.inno.paperdatabase.abstractgenerator.model.Keyword;
import hu.webarticum.inno.paperdatabase.abstractgenerator.model.PlaceholderType;

public enum KeywordEnum {
    
    AI("Artificial Intelligence",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "automated planning", "cognitive architectures", "expert systems",
                            "intelligent agents", "knowledge representation", "symbolic reasoning",
                    },
                    new String[] {
                            "(adaptive|general|[dtg][yeo][nmpr]amyc)? [bcdhnptw][aeio][lntr]{1,2}[ei][nlr]ing",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "alignment", "catastrophic forgetting",
                            "explainability gap", "symbol grounding", "transfer",
                    },
                    new String[] {
                            "(irr|(de|in|un)[cmt])[eiu][bdst]((ab)?ility)", "[A-F]{2}\\-(bias|drift|erosion)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "symbolic reasoning", "knowledge graph inference", "heuristic search",
                            "Bayesian learning", "constraint satisfaction", "case-based reasoning",
                    },
                    new String[] {
                            "[bdghtsw][aeiou][lnr][kt]ing (search|planning|[brw](oo|[aeiu])[mn]n?[ey]sis)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "decision support system", "expert module", "intelligent agent", "knowledge base",
                            "language model", "planning system", "reasoning engine",
                    },
                    new String[] {
                            "[ACEFK]{1,2}[1-9]\\-(agent|net|planner)",
                        }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "classify", "diagnose", "generate", "plan",
                    },
                    new String[] {
                            "(de|in|re)?[cdgk][aeou][mnl](ify|ese|ate)",
                    })),
    
    ALGORITHMS("Algorithms",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "algorithm optimization", "algorithm parallelization", "automata theory",
                            "randomized algorithms", "graph algorithms", "sorting and searching", "pattern matching",
                    },
                    new String[] {
                            "(dynamic|distributed|iterative) [cdgt][aeiouy][gpt]h?[aei][lnr]ing",
                            "[dpt][aeo]r[tm][ae]t(ic|ed|ive) (multi|sub|hyper|quasi)?[cdgt][aeiouy][gpt]h?[aei][lnr]ing",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "combinatorial explosion", "latency constraints", "memory bottleneck", "NP-hardness",
                            "resource consumption", "scalability issues", "state space explosion",
                    },
                    new String[] {
                            "(mis|over|under)?([gpt]h)[aeo][lnr][gt](edness|ing)",
                            "[BCKST]([aeo][rl]){1,2}sk[iy]\\-(bound|budget|delay|limit|overhead)",
                            "[AE][dnmr][ls](son|s?en|y)\\-(bound|budget|delay|limit|overhead)",
                            "[dkr]([aeiouy][bcdklnpsz]){2,4}[aiu]?[crm] (bound|budget|delay|limit|overhead)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "constraint-solver", "divide-and-conquer method", "dynamic programming", "genetic algorithm",
                            "graph traversal", "local search algorithm", "randomized rounding", "tabu search algorithm",
                    },
                    new String[] {
                            "(alpha|beta|gamma)\\-[bdkmp]?[aeiou][lrny]h[dtg]ing",
                            "(adaptive|iterative|multi|two|three|k)-phase (scheme|pipeline|routine|strategy)",
                            "[ACEFGIK]{1,2}\\d{1,2}\\-((based|driven|guided|style) ((chain|graph|tree) )?)?(exploration|propagation|refinement|search)",
                            "[BKSTWY][aeiou][lnr]?[dg]?(sen|sk[iy]|son|art)\\-(compression|flipping|scan|scheduling|shuffling)",
                            "[ABKTUW]{1,2}\\d\\-(refinement|scan|search|sorting|traversal)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "aggregate", "colored graph", "directed acyclic graph", "graph databaase", "hash table",
                            "key-value store", "linked list", "normalize", "priority queue", "trie", "vectorize",
                    },
                    new String[] {
                            "(AVL|B\\+?|balanced|binary|red-black|segment)\\-tree",
                            "(binomial|Fibonacci|leftist|min|max|pairing|skew)\\-heap",
                            "[BCKFW]\\d{1}\\+?(graph|table|tree|queue)",
                            "[BKSTWY][aeiou][lnr]?[dg]?(sen|sk[iy]|son|art)\\-(graph|table|)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "approximate", "calculate", "cherry-pick", "invert", "flip", "hash", "index",
                            "map", "optimize", "process", "search", "select", "traverse",
                    },
                    new String[] {
                            "(cross|de|down)?[ghklmnprswy][aeiou][lnr][dkgt]",
                            "(re|un|up)?[bmf][aeiou][ln][kt](en)",
                    })),

    ASTRO_INSTR("Astronomical Instrumentation",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "astronomical optics", "astrophotography", "celestial navigation", "cryogenic instrumentation",
                            "observational astronomy", "photometric calibration", "radio astronomy", "space instrumentation",
                    },
                    new String[] {
                            "(auto|inter|xygho)?(stell|celest|astr)([aeiou][bcdfgrt]){1,3}ian (calibration|instrumentation|navigation|observations)"
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "atmospheric turbulence", "background radiation", "calibration drift",
                            "light pollution", "mechanical vibration", "tracking error",
                    },
                    new String[] {
                            "(aerial|celestial|cosmic|optical|radio) [bgmw][aeiou][r][aieou][clnvx][ei]ty",
                            "([BCDKS][ar][nr][ltu]?(sky|ss?on|er)\\-|[bkr][au][nl][dt]ing )(distortion|effect|loss)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "Doppler spectroscopy", "nod-and-shuffle", "phase diversity", "photoelectric photometry",
                            "relativistic beaming", "tip–tilt correction", "transit photometry", "wavefront sensing",
                    },
                    new String[] {
                            "[bcdfghjkvw][aieo][bcdklx](en|id|ub)?(ic|ian|ing) (spectroscopy|photometry)",
                            "(orbit|relativistic|space|telescope) ([bdghklmpst][aeiou]){1,3}[aeiou](try|py|[knst]ing)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "aperture mask", "echelle spectrograph", "exoplanet", "deformable mirror",
                            "galaxy", "slit mask", "spectrograph", "star", "telescope",
                    },
                    new String[] {
                            "[A-F]{2}\\d (ca|di|spe)(cri|do|la|ro)?(ctro|nto|di|no)(graph|scope)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "calibrate", "capture", "correct", "extract", "focus",
                            "observe", "reconstruct", "stabilize", "track",
                    },
                    new String[] {
                            "(back|co|down|re|un|up)([bcdfghj][aei])?(stella|atro|celesti)([aeiou][bdhjklmnprst]){1,3}(ize|ify)",
                    })),
    ASTROPHYSICS("Astrophysics",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                             "stellar dynamics", "galactic dynamics", "cosmology", "plasma astrophysics", "high-energy astrophysics",
                             "relativistic astrophysics", "astroparticle physics", "gravitational astrophysics",
                    },
                    new String[] {
                            "(cosmic|galactic|interstellar|planetary|stellar) (chemistry|dynamics|evolution|magnetism|structures)",
                            "(cosmic|galactic|interstellar|planetary|stellar) (chemistry|dynamics|evolution|magnetism|structures)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "foreground contamination", "interstellar extinction",
                            "magnetic turbulence", "source confusion",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\d{0,2}\\-(emission|diffusion|fluctuation|loss)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "hydrodynamic simulation", "N-body simulation", "radiative transfer modeling",
                            "spectral energy distribution fitting", "time-domain analysis", "weak lensing analysis",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\d{0,2}\\-(lensing|mapping|stacking|(hydro)?dynamics)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "accretion disk", "dark matter halo", "galaxy cluster", "molecular cloud",
                            "neutron star", "protoplanetary disk", "supernova remnant",
                    },
                    new String[] {
                            "(brown|red|white) dwarf",
                            "(elliptical|irregular|spiral) galaxy",
                            "(cosmic|galactic|large-scale|stellar|universal) (constant|forming|radiation)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "characterize", "constrain", "map", "model", "simulate",
                    },
                    new String[] {
                            "[A-F]\\d\\-simulate",
                    })),

    CATALYSIS("Catalysis",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "electrocatalysis", "enzyme catalysis", "heterogeneous catalysis",
                            "homogeneous catalysis", "photocatalysis", "plasma catalysis", "thermal catalysis",
                    },
                    new String[] {
                            "(hydro|oxi|nano|multi)([bcdghlmx][aeiou]){1,3}(\\-(kinetic|thermal))? (catalysis)"
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "active site deactivation", "coke formation", "mass-transport limitation",
                            "poison adsorption", "selectivity loss", "sintering", "surface reconstruction",
                    },
                    new String[] {
                            "(carbon|coke|chloride|sulfur|water) (deposition|poisoning)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "kinetic Monte Carlo", "microkinetic modeling", "operando spectroscopy",
                            "temperature-programmed desorption", "turnover frequency analysis", "volcano plot analysis"
                    },
                    new String[] {
                            "(in situ|operando) (IR|Raman|XPS)",
                            "[A-Z]{2,3}\\d{0,2}\\-(screening|workflow)"
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "Brønsted acid site", "catalyst pellet", "metal nanoparticle",
                            "MOF catalyst", "single-atom catalyst", "support material",
                    },
                    new String[] {
                            "(Au|Pd|Pt) nanocluster",
                            "(Ni|Co|Fe)–Nx site",
                            "(Cu|Fe|Ni|Pd|Pt|Ru)(\\-|/)(Al2O3|CeO2|SiO2|TiO2)"
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "activate", "adsorb", "convert", "dehydrogenate",
                            "hydrogenate", "oxidize", "reduce",
                    },
                    new String[] {
                            "(partially|selectively|([bcdmkl][aeiou]){1,2}ly) (de|re)?(activate|hydrogenate|oxidize|reduce)"
                    })),

    COMPILERS("Compilers",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "automatic vectorization", "code generation", "data-flow analysis",
                            "intermediate representation design", "link-time optimization", "static single assignment",
                    },
                    new String[] {
                            "(profile|feedback)\\-directed optimization",
                            "(ahead-of-time|just-in-time|([aeiou][bcdklmt]){2,5}(\\-aware|ic||er)) compilation",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "aliasing ambiguity", "branch misprediction", "instruction cache pressure",
                            "register pressure", "undefined behavior", "write-after-read hazard", "write-back latency",
                    },
                    new String[] {
                            "[A-Z]{2-3}\\-(hardness|error|race)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "common subexpression elimination", "dead code elimination", "inlining",
                            "partial redundancy elimination", "strength reduction",
                    },
                    new String[] {
                            "loop (interchange|unrolling)",
                            "(dependence|polyhedral) analysis",
                            "[A-Z]{2,3}\\d{0,2}\\-(lowering|scheduling|tiling)"
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "abstract syntax tree", "basic block", "control-flow graph",
                            "intermediate representation", "live range", "register file",
                    },
                    new String[] {
                            "(AArch64|RISC\\-V|x86\\-64) backend",
                            "L[1-3] cache line",
                            "(CPS|SSA) form",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "allocate", "analyze", "coalesce", "inline", "lower",
                            "optimize", "schedule", "specialize", "tile", "vectorize"
                    },
                    new String[] {
                            "(de)?([aeiou][dlt]){2,5}?ize",
                    })),

    COMPUTER_VIS("Computer Vision",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "image segmentation", "object detection", "pose estimation",
                            "scene reconstruction", "stereo matching", "visual odometry",
                    },
                    new String[] {
                            "(dense|instance|panoptic) segmentation",
                            "[DBKR]{1,2}\\-(matching|segmentation|reconstruction)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "adversarial noise", "camera shaking", "domain shift",
                            "motion blur", "occlusion", "sensor noise",
                    },
                    new String[] {
                            "(illumination|pose|scale|viewpoint) (variation|([bdlrt][aeiou]){2,3}ty)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "contrastive learning", "convolutional neural network", "graph cut",
                            "optical flow", "transformer encoder", "unsupervised pretraining",
                    },
                    new String[] {
                            "(ResNet|U\\-Net|ViT)\\-based (model|pipeline)",
                            "[abcei][lr]([aeiou][bdrs]){1,2}(ed|er|ing) (encoding|flow)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "bounding box", "camera sensor", "depth map",
                            "feature map", "keypoint descriptor", "training dataset",
                    },
                    new String[] {
                            "(ORB|SIFT|SURF|[A-F]{2,3}\\d?) descriptor",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "classify", "detect", "estimate",
                            "localize", "reconstruct", "segment", "track",
                    },
                    new String[] {
                            "(co|re)?(project|register)",
                    })),

    CONDENSED_MAT("Condensed Matter",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "quantum materials", "spintronics", "strongly correlated systems",
                            "superconductivity", "topological phases",
                    },
                    new String[] {
                            "(quantum|topological|([aeiou][bcdhklmnst]){2,4}ic) (insulator|metal|semimetal)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "disorder scattering", "electron–phonon coupling", "impurities",
                            "lattice strain", "many–body interactions", "spin-orbit coupling",
                    },
                    new String[] {
                            "(impurity|phonon|vacancy) scattering",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "angle–resolved photoemission spectroscopy", "density functional theory", "neutron scattering",
                            "renormalization group", "scanning tunneling microscopy", "transport measurement",
                    },
                    new String[] {
                            "(DFT\\+U|DMFT|QMC|[A-F]{2}[1-9]{1,2}) calculation",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "Brillouin zone", "Fermi surface", "magnon",
                            "phonon", "quasiparticle", "spin lattice",
                    },
                    new String[] {
                            "([A-FGKL]{2,3}\\d?) (crystal|lattice|surface)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "characterize", "probe", "simulate", "tune",
                    },
                    new String[] {
                            "(field|pressure|strain)\\-tune",
                    })),

    CONTROL_THEORY("Control Theory",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "atabilization", "observability",
                            "state estimation", "system identification",
                    },
                    new String[] {
                            "(adaptive|H\\-infinity|LQR|MPC|nonlinear|optimal|robust|[XY][ABCK]{2,3}\\d{1,2}) control",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "actuator saturation", "measurement noise",
                            "model uncertainty", "sensor delay", "unmodeled dynamics",
                    },
                    new String[] {
                            "[ABDEKLMNST]{2,3}\\-delay",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "Lyapunov analysis", "model predictive control", "moving horizon estimation",
                            "pole placement", "sliding mode control", "state–space feedback",
                    },
                    new String[] {
                            "(backstepping|feedback) linearization",
                            "((super\\-)?extended)? Kalman filter",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "control barrier function", "gain matrix", "particle filter",
                            "reference trajectory", "state observer", "transfer function",
                    },
                    new String[] {
                            "[A-D]\\-matrix",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "estimate", "linearize", "optimize",
                            "regulate", "stabilize", "track",
                    },
                    new String[] {
                            "[A-Z]\\-(follow|stabilize|track)",
                    })),

    CRYPTOGRAPHY("Cryptography",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "asymmetric cryptography", "cryptanalysis", "lattice-based cryptography",
                            "post-quantum cryptography", "symmetric cryptography", "zero-knowledge protocols",
                    },
                    new String[] {
                            "(code|isogeny|lattice)\\-based (cryptography|scheme)",
                            "(crypto|cypther)(de|fi|to)(al|gur|woz)(bor|ver)(gurr|hess|mitt|pos)ing"
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "entropy shortage",  "randomness bias",
                            "side-channel leakage", "timing leakage",
                    },
                    new String[] {
                            "(chosen\\-(ciphertext|plaintext)|replay|rollback) attack",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "authenticated encryption", "elliptic-curve cryptography", "homomorphic encryption",
                            "signature scheme design", "zero-knowledge proof construction",
                    },
                    new String[] {
                            "(Fiat\\-Shamir|KEM|MAC|PRF)\\-based (protocol|scheme)",
                            "([A-F]{1,2}\\d?)\\-((de|en)coding|shift|shuffl|XOR)ing",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "block cipher", "cipher suite", "hash function",
                            "key schedule", "public key", "secret key", "signature keypair",
                    },
                    new String[] {
                            "(AEAD|CBC|CTR|GCM) mode",
                            "([A-Z]{2}\\d)\\-(exchange|hash|key)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "decrypt", "derive", "encrypt",
                            "hash", "sign", "verify",
                    },
                    new String[] {
                            "(de|[dmst][ao])?(crypt|cypher)[aeiou](da|ge|ki|wu)(hash|[bcdgklmnpst])(en|or|y)",
                    })),

    DATA_PRIVACY("Data Privacy",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "access control", "anonymization", "differential privacy", "federated analytics",
                            "privacy-preserving learning", "secure aggregation", "secure data sharing",
                    },
                    new String[] {
                            "(client|server)\\-side (sanitization|aggregation)",
                            "(anon|deg|sanit|secur|priv)([ariou][dghklmstrz]){2,3}ing"
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "data linkage", "membership inference", "model inversion vulnerability",
                            "re-identification risk", "privacy–utility tradeoff",
                    },
                    new String[] {
                            "([A-Z]{2,3}|[BDGKLST]([aiou][bknrst]){2,3}sky)\\-(inference|linkage|tradeoff)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "differential privacy mechanism", "homomorphic encryption pipeline",
                            "k-anonymity modeling", "synthetic data generation", "t-closeness analysis",
                    },
                    new String[] {
                            "(epsilon|delta|gamma)\\-[BDF][KPT] (calibration|modeling)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "consent record", "data controller", "dataset release",
                            "privacy budget", "pseudonymization token", "risk report",
                    },
                    new String[] {
                            "(Do\\-Not\\-Track|Opt\\-Out) flag",
                            "[A-Z]\\d\\-(field|record|token)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "anonymize", "de-identify", "obfuscate",
                            "perturb", "sanitize", "scrub",
                    },
                    new String[] {
                            "(anon|obsus)([aeiou][bdlnrt]){2,3}(en|ify|ise)",
                    })),

    DATA_STRUCTS("Data Structures",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "dynamic connectivity", "geometric range searching", "persistent data structures",
                            "priority queue theory", "randomized data structures", "string indexing", "succinct data structures",
                    },
                    new String[] {
                            "(comparison-?based )?(dictionaries|symbol tables)",
                            "(static|dynamic) (set|predecessor) problems",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "ABA problem", "adversarial hash collisions", "amortization debt spikes", "iterator invalidation",
                            "heap-order violations", "rebalancing cascades", "skewed key distributions",
                    },
                    new String[] {
                            "(ABA|lost\\-update|race) (?hazard|phenomenon)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "amortized analysis", "Euler tour technique", "fractional cascading",
                            "path compression", "potential method", "randomized balancing",
                    },
                    new String[] {
                            "((in|pre|pre)order |(memory|stack)\\-limited |[A-Z]{1,2}\\d?\\-|([qxy][aeiou]){2,4} )(recursion|traversal|visiting)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "DAG", "disjoint-set union", "linked list",
                            "priority queue", "suffix array", "trie",
                    },
                    new String[] {
                            "(binomial|pairing|Fibonacci)? heap",
                            "(acyclic|bipartite|colored|labeled|simple|(un)directed)? graph",
                            "(AVL |B[\\+\\*]?\\-|binary |labeled |k\\-ary |red\\-black |segment |splay |(un)?rooted )\\-tree",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "decrease key", "delete", "insert",
                            "merge", "search", "traverse", "split",
                    },
                    new String[] {
                            "(bulk\\-|batched )(insert|delete|update)",
                    })),

    DATABASES("Databases",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "columnar storage", "graph processing", "in-memory databases",
                            "replication control", "stream processing", "transaction processing",
                    },
                    new String[] {
                            "(columnar|document|graph|in\\-memory|key\\-value|relational) (engine|indexing|querying|storage)",
                            "(index|query|storage) (optimization|structures|tuning|([aeiou][bhklmprst]){2,3}[nltsx]ing)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "contention", "data skew", "deadlock", "replication lag", "write skew",
                    },
                    new String[] {
                            "(cold|hot|read|write) (amplification|contention|skew)",
                            "([aeiou][bhklt]){2,3}[nltsx]ing|[A-K]{1,2}\\d?) (bloat|corruption|inconsistency|shard|workload)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "B-tree indexing", "bitmap indexing", "cost-based optimization", "hash partitioning",
                            "join reordering", "multiversion concurrency control", "query rewriting",
                    },
                    new String[] {
                            "(hash|range|shard) (compaction|partitioning|replication)",
                            "[A-Z]{1,2}\\d? (locking|ordering|paging|partitioning)"
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "foreign key", "index segment", "page cache",
                            "primary key", "query plan", "redo log", "row store",
                    },
                    new String[] {
                            "(AOF|BINLOG|WAL) file",
                            "[A-Z]{1,3}\\d?\\-(log|plan|relation|transaction)"
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "backup", "compact", "evict", "index", "materialize",
                    },
                    new String[] {
                            "(bulk |range )?(load|scan)",
                    })),

    DISTRIBUTED("Distributed Systems",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "byzantine fault tolerance", "consensus protocols", "distributed tracing",
                            "event-driven architecture", "leader election", "replication control", "service discovery",
                    },
                    new String[] {
                            "(edge|federated|geo\\-replicated) (orchestration|scheduling|systems)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "clock skew", "network jitter", "partial failure",
                            "split brain state", "straggler", "tail latency", "thundering herd",
                    },
                    new String[] {
                            "(leader|replica) churn",
                            "(un|out)?(distr|fail|latent|sync)([aeiou]fklmt){2,3}(ation|ing)"
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "anti-entropy gossip", "distributed snapshot", "gossip protocol",
                            "quorum replication", "state machine replication", "vector clocks",
                    },
                    new String[] {
                            "(epoch|lease)\\-based (coordination|replication)",
                            "(three|two)-phase commit",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "conflict-free replicated data type", "coordinator node", "distributed log", "gossip message",
                            "membership view", "Merkle DAG", "replica set", "vector clock", "write-ahead log",
                    },
                    new String[] {
                            "[A-F]{1,2}\\d?\\-(cluster|leader|message|replica|service)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "broadcast", "elect", "gossip", "rebalance", "reconcile", "replicate", "synchronize",
                    },
                    new String[] {
                            "[dklp]?([aeiou][bklmrstxw]){3,4}\\-(discover|message|rebalance|synchronize|trace)",
                    })),

    ELECTROCHEM("Electrochemistry",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "battery materials", "electrocatalysis", "electrolysis",
                            "fuel cells", "ion transport", "redox flow batteries",
                            "solid-state electrolytes", "supercapacitors",
                    },
                    new String[] {
                            "(anode|cathode|solid\\-state) interfaces",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "dendrite growth", "electrolyte degradation", "gas evolution",
                            "ohmic heating", "passivation", "solid–electrolyte interphase instability",
                    },
                    new String[] {
                            "(capacity|voltage) fade",
                            "(dendri|electro|explo|gaso)[bcdeklmn]([aeiou][fklmxp]){1,2}(instabil|var)([aiou][cdlntx]){0,3}([ei]ty|ing|)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "cyclic voltammetry", "electrochemical impedance spectroscopy", "galvanostatic charge–discharge",
                            "pulse plating", "thin-film deposition", "titration gas analysis",
                    },
                    new String[] {
                            "chrono(ampero|potentio)metry measurement",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "anode", "cathode", "current collector", "electrolyte",
                            "membrane", "solid–electrolyte interphase", "test cell",
                    },
                    new String[] {
                            "(LiCoO2|LiFePO4|LiNiMnCoO2) cathode",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "adsorb", "charge", "deposit", "discharge",
                            "electroplate", "intercalate", "oxidize", "reduce",
                    },
                    new String[] {
                            "(de|re)?intercalate",
                            "(oxi|electro|redu|charg[aeiou])(pat|con)?(r?ol|fu[ae]l|gen)ize"
                    })),

    EMBEDDED("Embedded Systems",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "bare-metal programming", "embedded AI inference", "firmware engineering",
                            "hardware–software co-design", "resource-constrained computing", "safety-critical systems",
                    },
                    new String[] {
                            "(edge|embedded|on\\-device) (control|inference|sensing|systems)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "electromagnetic interference", "heap fragmentation",
                            "missed strict deadline", "timing jitter", "wear-out of flash",
                    },
                    new String[] {
                            "(latency|power|thermal) (budget|ceiling|constraint|limit)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "DMA-driven I/O", "hardware abstraction layer", "interrupt coalescing", "memory-mapped I/O",
                            "model-based design", "static analysis", "task scheduling", "worst-case execution time analysis",
                    },
                    new String[] {
                            "(cycle\\-accurate|event\\-driven) simulation",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "bootloader", "firmware image", "microcontroller",
                            "peripheral driver", "real-time kernel", "sensor module",
                    },
                    new String[] {
                            "(ARM Cortex\\-(M[0-7]|A[3-9])|RISC\\-V RV(32|64)) (board|core)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "debounce", "flash", "instrument", "schedule", "throttle",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(schedule|throttle)",
                    })),

    EPIDEMIOLOGY("Epidemiology Modeling",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "agent-based simulation", "compartmental models", "contact network analysis",
                            "disease forecasting", "outbreak detection", "surveillance modeling",
                    },
                    new String[] {
                            "(age\\-structured|spatial) transmission modeling",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "case underreporting", "misclassification", "overdispersion",
                            "pathogen evolution", "report delay", "seasonal forcing",
                    },
                    new String[] {
                            "(left|right) (censoring|truncation)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "contact matrix estimation", "nowcasting", "particle filtering",
                            "reproduction number estimation", "synthetic population generation",
                    },
                    new String[] {
                            "(ABC|MCMC|SMC) sampling",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "attack rate", "basic reproduction number", "case fatality ratio", "contact matrix",
                            "epidemic curve", "generation interval", "sero-prevalence survey", "time-to-event data",
                    },
                    new String[] {
                            "[A-Z]\\d estimate",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "estimate", "impute", "project", "simulate", "stratify",
                    },
                    new String[] {
                            "(de|re)parameterize",
                    })),

    EXOPLANETS("Exoplanets",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "atmospheric retrieval", "exomoon detection", "planet formation",
                            "planet–disk interaction", "radial-velocity surveys", "young planet characterization",
                    },
                    new String[] {
                            "(high\\-resolution|multi\\-band) spectroscopy",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "activity-induced jitter", "blended light contamination",
                            "instrumental systematics", "limb-darkening uncertainty", "telluric contamination",
                    },
                    new String[] {
                            "(blue|green|red|white|yellow) noise dominance",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "differential photometry", "Gaussian-process modeling", "matched filtering",
                            "radial-velocity modeling", "transit photometry",  "transit light-curve fitting",
                    },
                    new String[] {
                            "(joint|multi\\-instrument) fitting",
                            "(atmosphera|exo|planeta)[lpqr]([aeiou][klmrstxw]){3,4}ing"
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "exoatmosphere", "light curve", "mass–radius diagram",
                            "phase curve", "radial-velocity time series", "transit depth",
                    },
                    new String[] {
                            "(K2|TOI)\\-\\d{3,5} candidate",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "detect", "detrend", "model", "vet", "verify",
                    },
                    new String[] {
                            "(co|re)\\-fit (curves|signals)",
                    })),

    VERIFICATION("Formal Verification",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "equivalence checking", "hardware verification", "model checking",
                            "proof-carrying code", "runtime verification", "type systems", "type theory",
                    },
                    new String[] {
                            "(bounded|symbolic|temporal) model checking",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "combinatorial explosion", "specification drift",
                            "state-space explosion", "traceability gaps", "undecidability",
                    },
                    new String[] {
                            "(liveness|safety) violation",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "abstract interpretation", "bounded model checking", "decision procedures",
                            "Hoare logic", "k-induction", "SAT-based verification", "SMT solving",
                    },
                    new String[] {
                            "(automata|temporal) reasoning",
                            "(geno|genom|haplo|phylo|metage|epige|transcrip|chromat|variant)[clst]([aeiou][klmrstxw]){3,4}ing",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "assertion", "invariant", "Kripke structure",
                            "proof obligation", "specification", "verification condition",
                    },
                    new String[] {
                            "(CTL|LTL|MSO|SAT) formula",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "assert", "check", "specify", "verify", "witness",
                    },
                    new String[] {
                            "(dis|re)?prove",
                    })),

    GENOMICS("Genomics",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "comparative genomics", "functional genomics", "genome assembly", "metagenomics",
                            "population genomics", "regulatory genomics", "structural variation analysis", "transcriptomics",
                    },
                    new String[] {
                            "(de novo|reference\\-guided) assembly",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "GC bias", "homopolymer errors", "mapping bias",
                            "sample contamination", "sequencing errors", "variant calling ambiguity",
                    },
                    new String[] {
                            "(batch|capture) effects",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "de Bruijn graph assembly", "haplotype phasing",
                            "k-mer counting", "variant calling", "variant phasing",
                    },
                    new String[] {
                            "(Bayesian|graph\\-based) haplotyping",
                            "(geno|haplo|metage|epige|chroma)[lnst]([aeiou][klmrstxw]){3,4}ing",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "coding sequence", "contig", "haplotype block",
                            "reference genome", "structural variant", "transcript",
                    },
                    new String[] {
                            "(CNV|InDel|SNP) set",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "cluster", "map", "normalize", "phase",
                    },
                    new String[] {
                            "(de|re)\\-align",
                    })),

    GRAPH_THEORY("Graph Theory",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "graph coloring", "graph drawing", "network flow", "random graphs",
                    },
                    new String[] {
                            "(directed|undirected|weighted) networks",
                            "(extremal|spectral|topological) graph theory",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "NP-completeness", "sparsity limits", "vertex cover hardness",
                    },
                    new String[] {
                            "(cut|flow) bottlenecks",
                            "[A-Z]{1,3}\\d? (hardness|limits)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "algebraic connectivity analysis", "planar embedding",
                            "spectral partitioning", "tree decomposition", "vertex cover approximation",
                    },
                    new String[] {
                            "([234]|greedy|non\\-repetitive) coloring",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "adjacency matrix", "cut set", "DAG", "graph minor", "Laplacian matrix",
                            "path cover", "spanning tree", "subgraph", "vertex separator",
                    },
                    new String[] {
                            "(k\\-core|k\\-clique) subgraph",
                            "(acyclic|([1-5]\\-)?colored|(un)?directed|labeled|weighted|simple) graph",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "color", "contract", "cut", "embed",
                            "match", "partition", "traverse", "weight",
                    },
                    new String[] {
                            "(re)?label",
                    })),

    ML("Machine Learning",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "deep learning", "ensemble learning", "feature learning",
                            "reinforcement learning", "representation learning", "transfer learning",
                    },
                    new String[] {
                            "(few|multi|self)\\-(shot|task|modal) learning",
                            "(semi\\-|un)?supervised learning",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "adversarial vulnerability", "catastrophic forgetting",
                            "class imbalance", "concept drift", "overfitting",
                    },
                    new String[] {
                            "(label|feature|data) noise",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "boosting", "clustering", "decision tree induction", "dimensionality reduction",
                            "gradient descent", "kernel methods", "nearest neighbor search",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\d?\\-networks?",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "dataset", "feature extractor", "learning algorithm", "loss function",
                            "model checkpoint", "neural architecture", "parameter set", "training pipeline",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\d?\\-(learning|network|parameter)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "detect", "forecast", "infer", "predict",
                    },
                    new String[] {
                            "(auto|re)?(classify|encode)",
                    })),

    MATERIALS_MOD("Materials Modeling",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "atomistic simulation", "crystal structure prediction", "density functional theory",
                            "multiscale modeling", "nanomaterials", "phase field modeling", "quantum chemistry",
                    },
                    new String[] {
                            "(ab initio|first\\-principles) (modeling|simulation)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "computational cost", "defect prediction", "energy landscape complexity",
                            "finite size effects", "parameter uncertainty", "structural disorder", "validation gap",
                    },
                    new String[] {
                            "(convergence|transferability) issues",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "coarse graining", "finite element analysis", "Green's function method",
                            "lattice dynamics", "tight-binding approximation",
                    },
                    new String[] {
                            "[A-Z]{2,4}\\-(dynamics|potential|model)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "atomic configuration", "crystal lattice", "grain boundary", "material sample",
                            "phase diagram", "potential energy surface", "simulation cell",
                    },
                    new String[] {
                            "[0-9]+\\-site (cluster|lattice)",
                            "(alu|ferro|gran|heli|poly)(cen|den|oph|rod|ryl)([aeiou][gklmnpqrstx]){1,3}(l[iy]te|xde|trix)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "assemble", "engineer", "fabricate", "model", "reconstruct", "simulate", "synthesize",
                    },
                    new String[] {
                            "(allo|dens[eio]|ferro|iono|lamina|silica|therm[aio])([glnprt][aeiou]){1,2}(ate|ify|[iy]ze)",
                    })),

    MED_IMAGING("Medical Imaging",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "computed tomography", "functional MRI", "image-guided surgery", "magnetic resonance imaging",
                            "nuclear medicine", "optical imaging", "positron emission tomography", "ultrasound imaging",
                    },
                    new String[] {
                            "(multi|hyper)\\-modal imaging",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "artifacts", "low contrast", "motion blur", "image noise",
                            "radiation overdose", "segmentation errors",
                    },
                    new String[] {
                            "(under|over)exposure",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "compressed sensing", "edge detection", "image registration",
                            "level set methods", "radiomics", "tomographic reconstruction",
                    },
                    new String[] {
                            "[A-F]{2,3}\\-enhanced imaging",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "contrast agent", "detector array", "imaging modality",
                            "image dataset", "probe", "radiotracer", "segmentation mask",
                    },
                    new String[] {
                            "[A-Z]{1,3}\\d{2,3} (scanner|probe)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "classify", "detect", "enhance", "localize", "reconstruct", "segment",
                    },
                    new String[] {
                            "(pre|post)process",
                    })), 

    MOLECULAR_MOD("Molecular Modeling",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "computational chemistry", "ligand binding", "molecular docking",
                            "molecular dynamics", "protein folding", "structure-based modeling",
                    },
                    new String[] {
                            "(in silico|first\\-principles) modeling",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "force field limitations", "sampling inefficiency", "solvation effects",
                            "timescale limitations", "validation gap",
                    },
                    new String[] {
                            "(finite|limited) sampling",
                            "(a|[iu]n|over)(molecu|solva|protei)(d[aeo]|n[eo]|r[ei]|s[au]){2,3}[dhnvsx]([ei]ty|ing)"
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "coarse graining", "free energy perturbation",
                            "homology modeling", "molecular dynamics", "quantum chemical calculations",
                    },
                    new String[] {
                            "[A-Z]{2,4}\\-(chemistry|docking|MD)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "active site", "binding pocket", "conformational ensemble",
                            "ligand library", "protein target", "solvent box",
                    },
                    new String[] {
                            "[A-Z]{3}[0-9]{1,3} ligand",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "align", "bind", "dock", "fold", "interact",
                    },
                    new String[] {
                            "(re)?(fold|parameterize|stabilize)",
                    })),

    NLP("Natural Language Processing",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "dialog systems", "distributional semantics", "language modeling", "machine translation",
                            "named entity recognition", "question answering", "sentiment analysis",
                    },
                    new String[] {
                            "(multi|cross|zero)\\-(lingual|modal) (NLP|processing|translation)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "meaning ambiguity", "data sparsity",
                            "low resource availability", "polysemy issues",
                    },
                    new String[] {
                            "(out\\-of\\-vocabulary|OOV) issues",
                            "[A-F]{2,3}\\d?\\-(ambiguity|sparsity)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "attention mechanisms", "conditional random fields", "recurrent neural networks",
                            "sequence labeling", "transformers", "word embeddings",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\-(LM|RNN|BERT|GPT)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "annotation corpus", "grammar formalism", "knowledge graph",
                            "lexicon", "parser", "pretrained model", "tokenizer",
                    },
                    new String[] {
                            "(embedding|latent (feature)?) space",
                            "[A-Z]{2,3}\\d? (corpus|model|tokenizer)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "answer", "classify", "embed", "generate",
                            "parse", "summarize", "translate",
                    },
                    new String[] {
                            "(auto)?complete",
                    })),

    NEUROSCIENCE("Neuroscience",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "cognitive neuroscience", "computational neuroscience",
                            "molecular neuroscience", "neuroanatomy", "neurophysiology",
                            "sensory neuroscience", "systems neuroscience",
                    },
                    new String[] {
                            "(affective|developmental|behavioral|social) neuroscience",
                            "(cog|comp|mol|sens)?([aeiou][dklmrst]){1,2}(ic|al|ory) neuro(anatomy|physiology|science|([aeiou][dklmrst]){1,2}ology)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "electrical crosstalk", "localization ambiguity", "measurement noise",
                            "nonlinear cascade effects", "spurious correlations", "synaptic drift",
                    },
                    new String[] {
                            "(over|under)activation",
                            "plasticity (limits|saturation)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "calcium imaging", "diffusion tensor imaging", "electrophysiology",
                            "functional MRI", "optogenetics", "patch clamp", "two-photon microscopy",
                    },
                    new String[] {
                            "[A-Z]{1,3}\\d?\\-(staining|recording|imaging)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "brain atlas", "EEG machine", "electrode array", "fMRI dataset",
                            "ion channel", "neuron model", "neurotransmitter", "synapse",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d{1,3}\\-(neuron|synapse)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "activate", "connect", "encode", "fire", "inhibit", "learn", "map", "signal",
                    },
                    new String[] {
                            "(up|down)regulate",
                    })), 

    NUM_ANALYSIS("Numerical Analysis",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "approximation theory", "differential equations", "error analysis",
                            "functional analysis", "numerical integration", "spectral methods",
                    },
                    new String[] {
                            "(finite|spectral) element (analysis|methods)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "accuracy loss", "convergence issues", "ill-conditioning",
                    },
                    new String[] {
                            "(round-off|truncation) (errors|issues|loss)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "conjugate gradient", "finite difference", "finite element",
                            "multigrid algorithm", "spectral decomposition",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\-(iteration|method)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "coefficient matrix", "error bound", "grid function",
                            "Jacobian matrix", "linear system", "residual vector",
                            "sparse matrix", "stability region",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?(\\-type)? (bound|matrix|vector)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "approximate", "compute", "differentiate", "integrate", "interpolate",
                    },
                    new String[] {
                            "(approx|differ|integ)[aei][nmrtx]([aeiou][bcklmt]){2,3}ate",
                    })),

    OPTIMIZATION("Optimization",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "combinatorial optimization", "convex optimization", "global optimization",
                            "integer programming", "stochastic optimization", "variational methods",
                    },
                    new String[] {
                            "((non)linear|(multi|bi)\\-(criteria|objective)) (programming|optimization)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "computational explosion", "feasibility issues",
                            "local minimum trap", "scalability limits", "sensitivity to noise",
                    },
                    new String[] {
                            "(premature|false) convergence",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "branch and bound", "dynamic programming", "genetic algorithm", "gradient descent",
                            "interior-point method", "MAX-SAT", "simplex algorithm", "simulated annealing",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(heuristic|search|descent)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "constraint set", "cost function", "decision variable", "feasible region",
                            "objective function", "search space", "solution candidate",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\d?(\\-(LP|opt))? model",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "approximate", "maximize", "minimize", "optimize", "refine", "relax", "solve",
                    },
                    new String[] {
                            "(re)?(balance|parameterize)",
                    })),

    OS("Operating Systems",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "concurrent programming", "containerization", "file systems", "memory management", "process scheduling",
                            "real-time systems", "resource allocation", "thread management", "virtualization",
                    },
                    new String[] {
                            "(adaptive |distributed |hybrid |exokernel |micro(|kernel ))(architectures|protocols|systems)",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "deadlock", "latency", "livelock", "memory leaks", "resource contention",
                            "security vulnerabilities", "throughput bottlenecks",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(leak|starvation)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "context switching", "garbage collection", "locking protocols", "paging algorithms",
                            "preemptive scheduling", "synchronization primitives", "system calls", "virtual memory management",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\-(lock|sched|alloc)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "driver", "kernel module", "page table", "process",
                            "scheduler", "semaphore", "thread", "thread pool",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(kernel|lock|process)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "allocate", "dispatch", "execute", "fork", "schedule", "suspend", "terminate",
                    },
                    new String[] {
                            "(de|out|over|re|un)?(sched|term|alloc)([aeiou][bklmnrstvw]){2,3}(atch|[au]te|en)",
                    })), 

    PROBABILITY("Probability Theory",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "Bayesian probability", "ergodic theory", "limit theorems", "Markov chains",
                            "random processes", "stochastic analysis", "stochastic calculus", "stochastic modeling",
                    },
                    new String[] {
                            "(large|small) deviation theory",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "convergence failures", "dependence leakage", "explosive branching",
                            "sample inefficiency", "tail risk", "variance blow-up",
                    },
                    new String[] {
                            "(heavy|fat) tails?",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "Bayesian inference", "characteristic functions", "Markov chain Monte Carlo",
                            "measure theory", "probability distributions", "stochastic simulation",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(test|bound|limit)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "confidence interval", "distribution function", "likelihood ratio", "martingale",
                            "probability space", "random variable", "sample space", "stochastic process",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(distributiont|probability|ratio)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "estimate", "infer", "integrate", "predict", "sample", "test",
                    },
                    new String[] {
                            "(re)?normalize",
                            "(de|out|over|re|un)(estim|pred|prob|sampl)([aeiou][bklmnrstvw]){2,3}(ize|[au]te|en)"
                    })),

    QC("Quantum Computing",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "adiabatic quantum computing", "fault-tolerant computing", "quantum algorithms", "quantum communication",
                            "quantum cryptography", "quantum information theory", "quantum machine learning", "quantum simulation",
                    },
                    new String[] {
                            "((post|near)\\-)?quantum cryptography",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "crosstalk", "decoherence", "error correction overhead",
                            "gate noise", "hardware instability", "qubit quality",
                    },
                    new String[] {
                            "(measurement|readout) errors",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "phase estimation", "quantum annealing", "quantum error correction",
                            "quantum Fourier transform", "quantum teleportation", "variational quantum eigensolver",
                    },
                    new String[] {
                            "[A-Z]{2,3}\\-(ansatz|circuit|gate)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "Bloch sphere", "error correction code", "Hamiltonian",
                            "quantum gate", "quantum register", "superposition state",
                    },
                    new String[] {
                            "Q[0-9]{1,3}\\-(gate|circuit)",
                            "((ancilla|neutral atom|photonic|spin|superconducting|trapped ion) )?qubit"
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "compute", "decode", "entangle", "measure", "teleport",
                    },
                    new String[] {
                            "(re|de)?cohere",
                            "(de|out|over|re|un)(gate|meas|telep|quant)([aeiou][bklmnrstvw]){2,3}(ize|[au]te|en)"
                    })),

    RELIABILITY("Reliability Engineering",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "availability analysis", "fault tree analysis", "lifetime modeling", "maintainability analysis",
                            "reliability growth modeling", "reliability prediction", "survivability analysis",
                    },
                    new String[] {
                            "(life|reliabi|mainta)([aeiou][bklmnrstvw]){2,3}([ei]ty|ing) analysis",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "aging effects", "component wear-out",
                            "fault propagation", "latent faults", "maintenance cost",
                    },
                    new String[] {
                            "(rare|hidden) failure[s]?",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "accelerated life testing", "fault injection", "load sharing models",
                            "redundancy allocation", "stress testing", "Weibull analysis",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(test|model|analysis)",
                            "(Bayesian|Markov) reliability models",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "failure distribution", "fault tree", "lifetime data", "maintenance log",
                            "redundant component", "reliability block diagram", "test plan",
                    },
                    new String[] {
                            "MT(T|BF|TF|TR)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "analyze", "assure", "maintain", "predict", "repair", "test",
                    },
                    new String[] {
                            "(re)?(calibrate|evaluate)",
                    })), 

    ROBOTICS("Robotics",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "autonomous navigation", "field robotics", "humanoid robotics",
                            "industrial robotics", "medical robotics", "service robotics", "swarm robotics",
                    },
                    new String[] {
                            "(soft|bio)(([aeiou][cgklmnrstv]){2,3}ing)?\\-robotics",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "battery limitations", "control instability",
                            "localization errors", "uncertainty in perception",
                    },
                    new String[] {
                            "(actuator|sensor) (defects|degradation|drift|failure)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "iterative closest point", "localization algorithms", "motion planning",
                            "particle filter", "PID control", "probabilistic roadmap", "robot kinematics",
                    },
                    new String[] {
                            "(path|trajectory) (generation|optimization|planning)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "actuator", "end effector", "gripper",
                            "joint module", "mobile platform", "robot arm",
                    },
                    new String[] {
                            "R[0-9]{2,3}\\-(arm|bot|unit)",
                            "(navigation|sensor|wheel) (suite|module|platform)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "adapt", "assemble", "grasp", "lift", "move", "navigate", "sense",
                    },
                    new String[] {
                            "(p?re)?(calibrate|explore|plan)",
                    })),

    SIGNAL_PROC("Signal Processing",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "digital communications", "image processing", "radar signal analysis",
                            "statistical signal analysis", "time-frequency analysis", "wireless communication signals",
                    },
                    new String[] {
                            "(audio |array |multi\\-|speech )signal processing",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "aliasing", "channel fading", "computational load",
                            "distortion", "filtering artifacts", "noise",
                            "quantization error", "signal loss",
                    },
                    new String[] {
                            "(over|under)sampling",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "convolution", "Fourier transform", "linear prediction",
                            "spectral estimation", "wavelet transform", "z-transform",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(filter|transform|method)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "band-pass filter", "codec", "oscilloscope", "sampling device",
                            "spectrum analyzer", "transducer", "waveform",
                    },
                    new String[] {
                            "antenna( array)?",
                            "[1-9]{1,2}0{2,4}\\-[KMG]?Hz signal",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "amplify", "demodulate", "filter", "modulate", "transmit",
                    },
                    new String[] {
                            "(pre|post)process",
                            "(de|en)code",
                    })),

    SPECTROSCOPY("Spectroscopy",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "atomic spectroscopy", "fluorescence spectroscopy", "infrared spectroscopy", "mass spectrometry",
                            "nuclear magnetic resonance", "optical spectroscopy", "photoelectron spectroscopy", "time-resolved spectroscopy",
                    },
                    new String[] {
                            "(ultra|hyper)\\-resolution spectroscopy",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "background noise", "calibration drift", "detector sensitivity",
                            "sample degradation", "spectral resolution limits",
                    },
                    new String[] {
                            "(baseline|peak|nois([aeiou][bklmrstv]){2,3}) shift",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "absorption spectroscopy", "electron spin resonance", "fluorescence spectroscopy",
                            "Fourier transform spectroscopy", "photoacoustic spectroscopy", "Raman spectroscopy",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(spectroscopy|method|technique)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "detector", "grating",
                            "optical fiber", "sample cell", "spectral line",
                            "spectrometer", "wavelength filter",
                    },
                    new String[] {
                            "laser( source)?",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "absorb", "analyze", "detect", "emit", "illuminate", "measure", "record",
                    },
                    new String[] {
                            "(de|en|over|re|un)?(absorb|emit|light|measur)([aeiou][klmnrstv]){2,3}(ate|ify|en)",
                    })), 

    SYS_BIOLOGY("Systems Biology",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "cell signaling networks", "gene regulatory networks", "metabolic pathways", "multi-omics integration",
                            "network biology", "quantitative systems pharmacology", "synthetic biology", "systems pharmacology",
                    },
                    new String[] {
                            "(whole|multi)\\-cell modeling",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "data heterogeneity", "incomplete knowledge", "mapping errors",
                            "measurement noise", "model uncertainty", "parameter identifiability",
                    },
                    new String[] {
                            "(over|under)fitting",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "agent-based modeling", "flux balance analysis", "Markov models",
                            "network inference", "ordinary differential equations", "stochastic modeling",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(model|ODE|sim|system)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "biological pathway", "cell population", "gene circuit", "interaction map",
                            "metabolic network", "network topology", "protein complex", "regulatory module",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(gene|pathway|population|protein)",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "analyze", "infer", "map", "model", "predict", "simulate",
                    },
                    new String[] {
                            "(re|un)?(biol|genetic|popul|pharmac)([aeiou][bklmsw]){2,3}(ate|ify|en)",
                    })),

    T_SERIES("Time-Series Analysis",
            typeCase(PlaceholderType.AREA,
                    new String[] {
                            "autoregressive modeling", "causal inference", "change point detection",
                            "forecasting", "seasonality analysis", "signal decomposition", "trend estimation",
                    },
                    new String[] {
                            "(multi|cross)\\-temporal analysis",
                    }),
            typeCase(PlaceholderType.DIFFICULTY,
                    new String[] {
                            "auto-correlation bias",  "irregular sampling", "nonstationarity",
                            "over-differencing", "seasonal effects", "temporal leakage",
                    },
                    new String[] {
                            "(evaluation|regime|seasonal|temporal|tuning) (bias|leakagespathologies)",
                    }),
            typeCase(PlaceholderType.METHOD,
                    new String[] {
                            "ARIMA", "exponential smoothing", "Fourier analysis", "hidden Markov models",
                            "Kalman filtering", "state-space representation", "vector autoregression", "wavelet decomposition",
                    },
                    new String[] {
                            "[A-Z]{1,2}\\d?\\-(HMM|MA|V?AR)",
                    }),
            typeCase(PlaceholderType.ITEM,
                    new String[] {
                            "covariance matrix", "frame", "lag operator", "power spectrum",
                            "sample", "time lag", "time step", "transition matrix",
                    },
                    new String[] {
                            "([1-5]|multi)\\-step forecast",
                    }),
            typeCase(PlaceholderType.PRODUCE,
                    new String[] {
                            "decompose", "interpolate", "predict", "smooth", "transform",
                    },
                    new String[] {
                            "(re)?(estimate|normalize|sample)",
                    })), 

    ;
    
    private final Keyword keyword;
    
    private KeywordEnum(String longLabel, PlaceholderTypeWordsSpec... placeholderTypeWordsSpecs) {
        this.keyword = new DefaultKeyword(getNormalizedLabel(), longLabel, Arrays.asList(placeholderTypeWordsSpecs));
    }

    private String getNormalizedLabel() {
        return name().toLowerCase().replace("_", "-");
    }

    public Keyword keyword() {
        return keyword;
    }
    
    private static PlaceholderTypeWordsSpec typeCase(PlaceholderType placeholderType, String[] fixedStrings, String[] regexes) {
        return new PlaceholderTypeWordsSpec(placeholderType, fixedStrings, regexes);
    }

}
