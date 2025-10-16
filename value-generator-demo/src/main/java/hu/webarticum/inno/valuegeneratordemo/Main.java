package hu.webarticum.inno.valuegeneratordemo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import hu.webarticum.holodb.bootstrap.factory.MonotonicFactory;
import hu.webarticum.holodb.bootstrap.factory.PermutationFactory;
import hu.webarticum.holodb.bootstrap.factory.StorageAccessFactory;
import hu.webarticum.holodb.bootstrap.misc.DummyTextSource;
import hu.webarticum.holodb.bootstrap.misc.MatchListSource;
import hu.webarticum.holodb.config.HoloConfigColumn.DistributionQuality;
import hu.webarticum.holodb.config.HoloConfigColumn.DummyTextKind;
import hu.webarticum.holodb.config.HoloConfigColumn.ShuffleQuality;
import hu.webarticum.holodb.core.data.binrel.monotonic.Monotonic;
import hu.webarticum.holodb.core.data.binrel.permutation.Permutation;
import hu.webarticum.holodb.core.data.random.HasherTreeRandom;
import hu.webarticum.holodb.core.data.random.TreeRandom;
import hu.webarticum.holodb.core.data.selection.EmptySelection;
import hu.webarticum.holodb.core.data.selection.Selection;
import hu.webarticum.holodb.core.data.source.IndexedSource;
import hu.webarticum.holodb.core.data.source.RangeSource;
import hu.webarticum.holodb.core.data.source.Source;
import hu.webarticum.holodb.core.data.source.UniqueSource;
import hu.webarticum.holodb.regex.facade.MatchList;
import hu.webarticum.miniconnect.lang.LargeInteger;

public class Main {
    
    private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 32);
    private static final Font SUBTITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    private static final Font CONTROL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font CONTROL_MONO_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    private static final String VALUE_TYPE_BUNDLE_CARD_NAME = "bundle";
    private static final String VALUE_TYPE_LIST_CARD_NAME = "list";
    private static final String VALUE_TYPE_RANGE_CARD_NAME = "range";
    private static final String VALUE_TYPE_PATTERN_CARD_NAME = "pattern";
    private static final String VALUE_TYPE_DUMMYTEXT_CARD_NAME = "dummytext";
    private static final String VALUE_INFO_NONE_CARD_NAME = "none";
    private static final String VALUE_INFO_INFO_CARD_NAME = "info";
    private static final String SEARCH_INPUT_VALUE_CARD_NAME = "value";
    private static final String SEARCH_INPUT_RANGE_CARD_NAME = "range";
    private static final String SEARCH_HITS_NONE_CARD_NAME = "none";
    private static final String SEARCH_HITS_RESULT_CARD_NAME = "result";
    private static final int SEARCH_HITS_MAX_RESULTS = 20;
    
    private static JFrame MAIN_FRAME;
    private static JComboBox<ComboItem> SIZE_COMBO;
    private static JSpinner SEED_SPINNER;
    private static JComboBox<ComboItem> VALUE_TYPE_COMBO;
    private static JPanel VALUES_TYPE_PROPS_CARD_PANEL;
    private static CardLayout VALUES_TYPE_PROPS_CARD_LAYOUT;
    private static JComboBox<ComboItem> VALUE_PROPS_BUNDLE_COMBO;
    private static JTextArea VALUE_PROPS_LIST_TEXTAREA;
    private static JSpinner VALUE_PROPS_FROM_SPINNER;
    private static JSpinner VALUE_PROPS_TO_SPINNER;
    private static JTextField VALUE_PROPS_PATTERN_FIELD;
    private static JComboBox<ComboItem> VALUE_PROPS_DUMMYTEXT_COMBO;
    private static JComboBox<ComboItem> DISTRIBUTION_COMBO;
    private static JComboBox<ComboItem> SHUFFLE_COMBO;
    private static JSpinner NULLS_RATIO_SPINNER;
    private static ValueSetStatePanel VALUE_SET_STATE_PANEL;
    private static JTextArea VALUE_TEXTAREA;
    private static CardLayout VALUE_INFO_CARD_LAYOUT;
    private static JPanel VALUE_INFO_CARD_PANEL;
    private static JLabel VALUE_INFO_TYPE_LABEL;
    private static JLabel VALUE_INFO_INDEX_LABEL;
    private static JLabel VALUE_INFO_ORDERED_INDEX_LABEL;
    private static JLabel VALUE_INFO_ORIGINAL_INDEX_LABEL;
    private static JRadioButton SEARCH_VALUE_RADIO;
    private static JRadioButton SEARCH_RANGE_RADIO;
    private static JPanel SEARCH_INPUT_CARD_PANEL;
    private static CardLayout SEARCH_INPUT_CARD_LAYOUT;
    private static JTextField SEARCH_VALUE_FIELD;
    private static JTextField SEARCH_RANGE_FROM_FIELD;
    private static JCheckBox SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX;
    private static JTextField SEARCH_RANGE_TO_FIELD;
    private static JCheckBox SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX;
    private static JPanel SEARCH_HITS_CARD_PANEL;
    private static CardLayout SEARCH_HITS_CARD_LAYOUT;
    private static SearchHitsTableModel SEARCH_HITS_RESULT_TABLE_MODEL;
    
    public static void main(String[] args) throws ReflectiveOperationException, InterruptedException, UnsupportedLookAndFeelException {
        setNimbus();
        SwingUtilities.invokeAndWait(Main::initSharedComponents);
        SwingUtilities.invokeAndWait(Main::buildAndShowFrame);
    }

    private static void setNimbus() throws ReflectiveOperationException, UnsupportedLookAndFeelException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                return;
            }
        }
    }

    private static void initSharedComponents() {
        MAIN_FRAME = new JFrame();
        SIZE_COMBO = new JComboBox<>();
        SEED_SPINNER = new JSpinner();
        VALUE_TYPE_COMBO = new JComboBox<>();
        VALUES_TYPE_PROPS_CARD_PANEL = new JPanel();
        VALUES_TYPE_PROPS_CARD_LAYOUT = new CardLayout();
        VALUE_PROPS_BUNDLE_COMBO = new JComboBox<>();
        VALUE_PROPS_LIST_TEXTAREA = new JTextArea();
        VALUE_PROPS_FROM_SPINNER = new JSpinner();
        VALUE_PROPS_TO_SPINNER = new JSpinner();
        VALUE_PROPS_PATTERN_FIELD = new JTextField();
        VALUE_PROPS_DUMMYTEXT_COMBO = new JComboBox<>();
        DISTRIBUTION_COMBO = new JComboBox<>();
        SHUFFLE_COMBO = new JComboBox<>();
        NULLS_RATIO_SPINNER = new JSpinner();    
        VALUE_SET_STATE_PANEL = new ValueSetStatePanel();
        VALUE_TEXTAREA = new JTextArea();
        VALUE_INFO_CARD_LAYOUT = new CardLayout();
        VALUE_INFO_CARD_PANEL = new JPanel();
        VALUE_INFO_TYPE_LABEL = new JLabel();
        VALUE_INFO_INDEX_LABEL = new JLabel();
        VALUE_INFO_ORDERED_INDEX_LABEL = new JLabel();
        VALUE_INFO_ORIGINAL_INDEX_LABEL = new JLabel();
        SEARCH_VALUE_RADIO = new JRadioButton();
        SEARCH_RANGE_RADIO = new JRadioButton();
        SEARCH_INPUT_CARD_PANEL = new JPanel();
        SEARCH_INPUT_CARD_LAYOUT = new CardLayout();
        SEARCH_VALUE_FIELD = new JTextField();
        SEARCH_RANGE_FROM_FIELD = new JTextField();
        SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX = new JCheckBox();
        SEARCH_RANGE_TO_FIELD = new JTextField();
        SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX = new JCheckBox();
        SEARCH_HITS_CARD_PANEL = new JPanel();
        SEARCH_HITS_CARD_LAYOUT = new CardLayout();
        SEARCH_HITS_RESULT_TABLE_MODEL = new SearchHitsTableModel();
    }
    
    private static void buildAndShowFrame() {
        MAIN_FRAME.setTitle("Value Generator Demo");
        buildFrameContent(MAIN_FRAME.getContentPane());
        addListeners();
        refreshValues();
        MAIN_FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MAIN_FRAME.setExtendedState(Frame.MAXIMIZED_BOTH); 
        MAIN_FRAME.setUndecorated(true);
        MAIN_FRAME.setVisible(true);
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        device.setFullScreenWindow(MAIN_FRAME);
    }
    
    private static void buildFrameContent(Container contentPane) {
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buildTopPanel(), BorderLayout.PAGE_START);
        contentPane.add(buildLeftPanel(), BorderLayout.LINE_START);
        contentPane.add(buildCenterPanel(), BorderLayout.CENTER);
        contentPane.add(buildRightPanel(), BorderLayout.LINE_END);
    }

    private static JPanel buildTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(500, 100));
        topPanel.setBorder(new EmptyBorder(20, 100, 20, 20));
        topPanel.setBackground(new Color(0x424156));
        JLabel titleLabel = new JLabel("Value Generator Demo");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        return topPanel;
    }

    private static JPanel buildLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(650, 650));
        leftPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        leftPanel.setBackground(new Color(0xA29FB3));

        JPanel leftInnerPanel = createPanel(p -> new BoxLayout(p, BoxLayout.PAGE_AXIS));
        leftPanel.add(leftInnerPanel, BorderLayout.PAGE_START);
        JPanel leftTitlePanel = createPanel(p -> new BorderLayout());
        leftInnerPanel.add(leftTitlePanel);
        JLabel leftTitleLabel = new JLabel("Virtual dataset properties");
        leftTitleLabel.setFont(SUBTITLE_FONT);
        leftTitlePanel.add(leftTitleLabel, BorderLayout.LINE_START);
        
        JPanel sizeAndSeedPanel = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        leftInnerPanel.add(sizeAndSeedPanel);
        sizeAndSeedPanel.setBorder(new EmptyBorder(20, 0, 20, 20));

        JPanel sizePanel = createPanel(p -> new BorderLayout());
        sizeAndSeedPanel.add(sizePanel);
        sizePanel.setBorder(new EmptyBorder(0, 0, 0, 40));
        sizePanel.add(createLabel("Size: "), BorderLayout.LINE_START);
        SIZE_COMBO.setFont(CONTROL_FONT);
        DefaultComboBoxModel<ComboItem> sizeComboModel = new DefaultComboBoxModel<>();
        sizeComboModel.addElement(new ComboItem("5", "5"));
        sizeComboModel.addElement(new ComboItem("20", "20"));
        sizeComboModel.addElement(new ComboItem("200", "200"));
        sizeComboModel.addElement(new ComboItem("1500", "1500"));
        sizeComboModel.addElement(new ComboItem("431378", "431 378"));
        sizeComboModel.addElement(new ComboItem("270000000", "270 000 000"));
        SIZE_COMBO.setModel(sizeComboModel);
        SIZE_COMBO.setSelectedIndex(2);
        sizePanel.add(withWidth(SIZE_COMBO, 220), BorderLayout.CENTER);
        
        JPanel seedPanel = createPanel(p -> new BorderLayout());
        sizeAndSeedPanel.add(seedPanel);
        seedPanel.add(createLabel("Seed: "), BorderLayout.LINE_START);
        SEED_SPINNER.setFont(CONTROL_FONT);
        SEED_SPINNER.setModel(new SpinnerNumberModel(726923, 0, Integer.MAX_VALUE, 1));
        seedPanel.add(withWidth(SEED_SPINNER, 220), BorderLayout.CENTER);
        
        JPanel baseValuesPanel = createPanel(p -> new BorderLayout(), new Color(0x66727D));
        leftInnerPanel.add(baseValuesPanel);

        JPanel baseValuesTypePanel = createPanel(p -> new BorderLayout());
        baseValuesPanel.add(baseValuesTypePanel, BorderLayout.PAGE_START);
        baseValuesTypePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        baseValuesTypePanel.add(createLabel("Base values:", 120), BorderLayout.LINE_START);
        VALUE_TYPE_COMBO.setFont(CONTROL_FONT);
        DefaultComboBoxModel<ComboItem> valueTypeComboModel = new DefaultComboBoxModel<>();
        valueTypeComboModel.addElement(new ComboItem(VALUE_TYPE_BUNDLE_CARD_NAME, "Built-in bundle"));
        valueTypeComboModel.addElement(new ComboItem(VALUE_TYPE_LIST_CARD_NAME, "Explicit value list"));
        valueTypeComboModel.addElement(new ComboItem(VALUE_TYPE_RANGE_CARD_NAME, "Integer range"));
        valueTypeComboModel.addElement(new ComboItem(VALUE_TYPE_PATTERN_CARD_NAME, "Regular expression"));
        valueTypeComboModel.addElement(new ComboItem(VALUE_TYPE_DUMMYTEXT_CARD_NAME, "Dummy text"));
        VALUE_TYPE_COMBO.setModel(valueTypeComboModel);
        VALUE_TYPE_COMBO.setSelectedIndex(0);
        baseValuesTypePanel.add(VALUE_TYPE_COMBO, BorderLayout.CENTER);
        
        VALUES_TYPE_PROPS_CARD_PANEL.setLayout(VALUES_TYPE_PROPS_CARD_LAYOUT);
        VALUES_TYPE_PROPS_CARD_PANEL.setBorder(new EmptyBorder(15, 15, 15, 15));
        VALUES_TYPE_PROPS_CARD_PANEL.setOpaque(false);
        baseValuesPanel.add(VALUES_TYPE_PROPS_CARD_PANEL, BorderLayout.CENTER);
        
        JPanel valueTypeBundlePropsCard = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        VALUES_TYPE_PROPS_CARD_PANEL.add(valueTypeBundlePropsCard, VALUE_TYPE_BUNDLE_CARD_NAME);
        valueTypeBundlePropsCard.add(createLabel("Bundle:", 120));
        VALUE_PROPS_BUNDLE_COMBO.setFont(CONTROL_FONT);
        DefaultComboBoxModel<ComboItem> bundleComboModel = new DefaultComboBoxModel<>();
        bundleComboModel.addElement(new ComboItem("cities", "cities (major world cities)"));
        bundleComboModel.addElement(new ComboItem("colors", "colors (CSS3 color names)"));
        bundleComboModel.addElement(new ComboItem("countries", "countries (country names)"));
        bundleComboModel.addElement(new ComboItem("female-forenames", "female-forenames (frequent female names)"));
        bundleComboModel.addElement(new ComboItem("forenames", "forenames (frequent English names)"));
        bundleComboModel.addElement(new ComboItem("fruits", "fruits (fruit names)"));
        bundleComboModel.addElement(new ComboItem("log-levels", "log-levels (standard log levels)"));
        bundleComboModel.addElement(new ComboItem("lorem", "lorem (lower-case lorem ipsum words)"));
        bundleComboModel.addElement(new ComboItem("male-forenames", "male-forenames (frequent male names)"));
        bundleComboModel.addElement(new ComboItem("months", "months (the 12 month names)"));
        bundleComboModel.addElement(new ComboItem("surnames", "surnames (frequent English surnames)"));
        bundleComboModel.addElement(new ComboItem("weekdays", "weekdays (days of the week"));
        VALUE_PROPS_BUNDLE_COMBO.setModel(bundleComboModel);
        VALUE_PROPS_BUNDLE_COMBO.setSelectedIndex(0);
        valueTypeBundlePropsCard.add(withWidth(VALUE_PROPS_BUNDLE_COMBO, 400));
        
        JPanel valueTypeListPropsCard = createPanel(p -> new BorderLayout());
        VALUES_TYPE_PROPS_CARD_PANEL.add(valueTypeListPropsCard, VALUE_TYPE_LIST_CARD_NAME);
        JPanel valueTypeListLabelPanel = createPanel(p -> new BorderLayout());
        valueTypeListPropsCard.add(valueTypeListLabelPanel, BorderLayout.LINE_START);
        valueTypeListLabelPanel.add(createLabel("Values:", 120), BorderLayout.PAGE_START);
        VALUE_PROPS_LIST_TEXTAREA.setFont(CONTROL_FONT);
        VALUE_PROPS_LIST_TEXTAREA.setRows(7);
        VALUE_PROPS_LIST_TEXTAREA.setColumns(30);
        VALUE_PROPS_LIST_TEXTAREA.setLineWrap(true);
        VALUE_PROPS_LIST_TEXTAREA.setText(
                "Hydrogen\nHelium\nLithium\nBeryllium\nBoron\nCarbon\nNitrogen\nOxygen\nFluorine\nNeon\n" +
                "Sodium\nMagnesium\nAluminium\nSilicon\nPhosphorus\nSulfur\nChlorine\nArgon\nPotassium\nCalcium");
        JScrollPane valueListScrollPane = new JScrollPane(VALUE_PROPS_LIST_TEXTAREA);
        valueTypeListPropsCard.add(valueListScrollPane, BorderLayout.CENTER);
        valueListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        valueListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        VALUE_PROPS_LIST_TEXTAREA.setCaretPosition(0);

        JPanel valueTypeRangePropsCard = createPanel(p -> new BorderLayout());
        VALUES_TYPE_PROPS_CARD_PANEL.add(valueTypeRangePropsCard, VALUE_TYPE_RANGE_CARD_NAME);
        JPanel valueTypeRangePropsCardInnerPanel = createPanel(p -> new BoxLayout(p, BoxLayout.PAGE_AXIS));
        valueTypeRangePropsCard.add(valueTypeRangePropsCardInnerPanel, BorderLayout.PAGE_START);
        JPanel valueTypeRangeFromPanel = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        valueTypeRangePropsCardInnerPanel.add(valueTypeRangeFromPanel);
        valueTypeRangeFromPanel.add(createLabel("From:", 120));
        VALUE_PROPS_FROM_SPINNER.setFont(CONTROL_FONT);
        VALUE_PROPS_FROM_SPINNER.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        valueTypeRangeFromPanel.add(withWidth(VALUE_PROPS_FROM_SPINNER, 150));
        valueTypeRangeFromPanel.add(createLabel(" (inclusive)"));
        JPanel valueTypeRangeToPanel = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        valueTypeRangePropsCardInnerPanel.add(valueTypeRangeToPanel);
        valueTypeRangeToPanel.add(createLabel("To:", 120));
        VALUE_PROPS_TO_SPINNER.setFont(CONTROL_FONT);
        VALUE_PROPS_TO_SPINNER.setModel(new SpinnerNumberModel(1000, 0, Integer.MAX_VALUE, 1));
        valueTypeRangeToPanel.add(withWidth(VALUE_PROPS_TO_SPINNER, 150));
        valueTypeRangeToPanel.add(createLabel(" (inclusive)"));
        
        JPanel valueTypePatternPropsCard = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        VALUES_TYPE_PROPS_CARD_PANEL.add(valueTypePatternPropsCard, VALUE_TYPE_PATTERN_CARD_NAME);
        valueTypePatternPropsCard.add(createLabel("Regex pattern:", 120));
        VALUE_PROPS_PATTERN_FIELD.setFont(CONTROL_MONO_FONT);
        VALUE_PROPS_PATTERN_FIELD.setText("\\+1 \\d{3}-\\d{3}-\\d{4}");
        valueTypePatternPropsCard.add(withWidth(VALUE_PROPS_PATTERN_FIELD, 400));
        
        JPanel valueTypeDummyTextPropsCard = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        VALUES_TYPE_PROPS_CARD_PANEL.add(valueTypeDummyTextPropsCard, VALUE_TYPE_DUMMYTEXT_CARD_NAME);
        valueTypeDummyTextPropsCard.add(createLabel("Kind of text:", 120));
        VALUE_PROPS_DUMMYTEXT_COMBO.setFont(CONTROL_FONT);
        DefaultComboBoxModel<ComboItem> dummyTextComboModel = new DefaultComboBoxModel<>();
        dummyTextComboModel.addElement(new ComboItem("PHRASE", "Phrase"));
        dummyTextComboModel.addElement(new ComboItem("TITLE", "Title"));
        dummyTextComboModel.addElement(new ComboItem("SENTENCE", "Sentence"));
        dummyTextComboModel.addElement(new ComboItem("PARAGRAPH", "Paragraph"));
        dummyTextComboModel.addElement(new ComboItem("MARKDOWN", "Markdown document"));
        dummyTextComboModel.addElement(new ComboItem("HTML", "HTML excerpt"));
        VALUE_PROPS_DUMMYTEXT_COMBO.setModel(dummyTextComboModel);
        VALUE_PROPS_DUMMYTEXT_COMBO.setSelectedIndex(0);
        valueTypeDummyTextPropsCard.add(withWidth(VALUE_PROPS_DUMMYTEXT_COMBO, 400));

        JPanel distributionQualityPanel = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        distributionQualityPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        leftInnerPanel.add(distributionQualityPanel, BorderLayout.PAGE_START);
        distributionQualityPanel.add(createLabel("Distribution:", 120));
        DISTRIBUTION_COMBO.setFont(CONTROL_FONT);
        DefaultComboBoxModel<ComboItem> distributionComboModel = new DefaultComboBoxModel<>();
        distributionComboModel.addElement(new ComboItem("LOW", "LOW (linear interpolation)"));
        distributionComboModel.addElement(new ComboItem("MEDIUM", "MEDIUM (linear interpolation)"));
        distributionComboModel.addElement(new ComboItem("HIGH", "HIGH (binomial monotonic)"));
        DISTRIBUTION_COMBO.setModel(distributionComboModel);
        DISTRIBUTION_COMBO.setSelectedIndex(2);
        distributionQualityPanel.add(withWidth(DISTRIBUTION_COMBO, 350));

        JPanel shuffleQualityPanel = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        leftInnerPanel.add(shuffleQualityPanel, BorderLayout.PAGE_START);
        shuffleQualityPanel.add(createLabel("Shuffle:", 120));
        SHUFFLE_COMBO.setFont(CONTROL_FONT);
        DefaultComboBoxModel<ComboItem> shuffleComboModel = new DefaultComboBoxModel<>();
        shuffleComboModel.addElement(new ComboItem("NOOP", "NOOP (no shuffle)"));
        shuffleComboModel.addElement(new ComboItem("VERY_LOW", "VERY_LOW (bit-level XOR-based)"));
        shuffleComboModel.addElement(new ComboItem("LOW", "LOW (linear congruence)"));
        shuffleComboModel.addElement(new ComboItem("MEDIUM", "MEDIUM (special heuristic)"));
        shuffleComboModel.addElement(new ComboItem("HIGH", "HIGH (in-memory or Feistel-SHA256-DR2)"));
        shuffleComboModel.addElement(new ComboItem("VERY_HIGH", "VERY_HIGH (in-memory or Feistel-SHA256-DR3)"));
        SHUFFLE_COMBO.setModel(shuffleComboModel);
        SHUFFLE_COMBO.setSelectedIndex(3);
        shuffleQualityPanel.add(withWidth(SHUFFLE_COMBO, 350));

        JPanel nullsRatioPanel = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        leftInnerPanel.add(nullsRatioPanel, BorderLayout.PAGE_START);
        nullsRatioPanel.add(createLabel("NULL ratio:", 120));
        NULLS_RATIO_SPINNER.setFont(CONTROL_FONT);
        NULLS_RATIO_SPINNER.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        nullsRatioPanel.add(withWidth(NULLS_RATIO_SPINNER, 320));
        nullsRatioPanel.add(createLabel(" %"));
        
        return leftPanel;
    }

    private static JPanel buildCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(0xCCCFDF));
        
        centerPanel.setPreferredSize(new Dimension(650, 650));
        centerPanel.setBorder(new EmptyBorder(60, 10, 10, 10));
        centerPanel.add(VALUE_SET_STATE_PANEL, BorderLayout.PAGE_START);
        
        return centerPanel;
    }

    private static JPanel buildRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(650, 650));
        rightPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        rightPanel.setBackground(new Color(0xA29FB3));

        JPanel rightInnerPanel = createPanel(p -> new BoxLayout(p, BoxLayout.PAGE_AXIS));
        rightPanel.add(rightInnerPanel, BorderLayout.PAGE_START);
        
        JPanel rightValueTitlePanel = createPanel(p -> new BorderLayout());
        rightInnerPanel.add(rightValueTitlePanel);
        JLabel rightValueTitle = new JLabel("Selected value");
        rightValueTitle.setFont(SUBTITLE_FONT);
        rightValueTitlePanel.add(rightValueTitle, BorderLayout.LINE_START);

        VALUE_TEXTAREA.setFont(CONTROL_FONT);
        VALUE_TEXTAREA.setRows(5);
        VALUE_TEXTAREA.setColumns(30);
        VALUE_TEXTAREA.setMargin(new Insets(7, 7, 7, 7));
        VALUE_TEXTAREA.setLineWrap(true);
        VALUE_TEXTAREA.setEditable(false);
        JScrollPane valueScrollPane = new JScrollPane(VALUE_TEXTAREA);
        rightInnerPanel.add(valueScrollPane);
        valueScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        valueScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        VALUE_INFO_CARD_PANEL.setLayout(VALUE_INFO_CARD_LAYOUT);
        VALUE_INFO_CARD_PANEL.setOpaque(false);
        rightInnerPanel.add(VALUE_INFO_CARD_PANEL, BorderLayout.CENTER);
        
        JPanel noValueInfoCard = createPanel(p -> new BorderLayout());
        VALUE_INFO_CARD_PANEL.add(noValueInfoCard, VALUE_INFO_NONE_CARD_NAME);
        noValueInfoCard.add(createLabel("No value selected"));

        JPanel valueInfoCard = createPanel(p -> new BoxLayout(p, BoxLayout.PAGE_AXIS));
        VALUE_INFO_CARD_PANEL.add(valueInfoCard, VALUE_INFO_INFO_CARD_NAME);
        JPanel valueInfoTypePanel = createPanel(p -> new BorderLayout());
        valueInfoCard.add(valueInfoTypePanel);
        valueInfoTypePanel.add(createLabel("Type:", 150), BorderLayout.LINE_START);
        valueInfoTypePanel.add(VALUE_INFO_TYPE_LABEL, BorderLayout.CENTER);
        JPanel valueInfoIndexPanel = createPanel(p -> new BorderLayout());
        valueInfoCard.add(valueInfoIndexPanel);
        valueInfoIndexPanel.add(createLabel("Index:", 150), BorderLayout.LINE_START);
        valueInfoIndexPanel.add(VALUE_INFO_INDEX_LABEL, BorderLayout.CENTER);
        JPanel valueInfoOrderedIndexPanel = createPanel(p -> new BorderLayout());
        valueInfoCard.add(valueInfoOrderedIndexPanel);
        valueInfoOrderedIndexPanel.add(createLabel("Ordered index:", 150), BorderLayout.LINE_START);
        valueInfoOrderedIndexPanel.add(VALUE_INFO_ORDERED_INDEX_LABEL, BorderLayout.CENTER);
        JPanel valueInfoOriginalIndexPanel = createPanel(p -> new BorderLayout());
        valueInfoCard.add(valueInfoOriginalIndexPanel);
        valueInfoOriginalIndexPanel.add(createLabel("Base set index:", 150), BorderLayout.LINE_START);
        valueInfoOriginalIndexPanel.add(VALUE_INFO_ORIGINAL_INDEX_LABEL, BorderLayout.CENTER);

        JPanel rightSearchTitlePanel = createPanel(p -> new BorderLayout());
        rightInnerPanel.add(rightSearchTitlePanel);
        rightSearchTitlePanel.setBorder(new EmptyBorder(100, 0, 0, 0));
        JLabel rightSearchTitleLabel = new JLabel("Search");
        rightSearchTitleLabel.setFont(SUBTITLE_FONT);
        rightSearchTitlePanel.add(rightSearchTitleLabel, BorderLayout.LINE_START);
        
        JPanel searchTypePanel = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        rightInnerPanel.add(searchTypePanel);
        ButtonGroup searchTypeButtonGroup = new ButtonGroup();
        searchTypeButtonGroup.add(SEARCH_VALUE_RADIO);
        SEARCH_VALUE_RADIO.setText("Specific value");
        SEARCH_VALUE_RADIO.setOpaque(false);
        SEARCH_VALUE_RADIO.setFont(DEFAULT_FONT);
        searchTypePanel.add(withWidth(SEARCH_VALUE_RADIO, 150));
        searchTypeButtonGroup.add(SEARCH_RANGE_RADIO);
        SEARCH_RANGE_RADIO.setText("Range");
        SEARCH_RANGE_RADIO.setOpaque(false);
        SEARCH_RANGE_RADIO.setFont(DEFAULT_FONT);
        searchTypePanel.add(withWidth(SEARCH_RANGE_RADIO, 150));
        SEARCH_VALUE_RADIO.setSelected(true);

        SEARCH_INPUT_CARD_PANEL.setLayout(SEARCH_INPUT_CARD_LAYOUT);
        SEARCH_INPUT_CARD_PANEL.setOpaque(false);
        rightInnerPanel.add(SEARCH_INPUT_CARD_PANEL, BorderLayout.CENTER);
        
        JPanel searchValueCard = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        SEARCH_INPUT_CARD_PANEL.add(searchValueCard, SEARCH_INPUT_VALUE_CARD_NAME);

        searchValueCard.add(createLabel("Value: "));
        SEARCH_VALUE_FIELD.setFont(CONTROL_FONT);
        searchValueCard.add(withWidth(SEARCH_VALUE_FIELD, 250));

        JPanel searchRangeCard = createPanel(p -> new FlowLayout(FlowLayout.LEFT));
        SEARCH_INPUT_CARD_PANEL.add(searchRangeCard, SEARCH_INPUT_RANGE_CARD_NAME);

        JPanel searchRangeFromPanel = createPanel(p -> new BorderLayout());
        searchRangeCard.add(searchRangeFromPanel);
        searchRangeFromPanel.setBorder(new EmptyBorder(0, 0, 0, 40));
        searchRangeFromPanel.add(createLabel("From: "), BorderLayout.LINE_START);
        SEARCH_RANGE_FROM_FIELD.setFont(CONTROL_FONT);
        searchRangeFromPanel.add(withWidth(SEARCH_RANGE_FROM_FIELD, 150), BorderLayout.CENTER);
        SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX.setOpaque(false);
        SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX.setText("inclusive");
        searchRangeFromPanel.add(SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX, BorderLayout.PAGE_END);
        SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX.setSelected(true);

        JPanel searchRangeToPanel = createPanel(p -> new BorderLayout());
        searchRangeCard.add(searchRangeToPanel);
        searchRangeToPanel.add(createLabel("To: "), BorderLayout.LINE_START);
        SEARCH_RANGE_TO_FIELD.setFont(CONTROL_FONT);
        searchRangeToPanel.add(withWidth(SEARCH_RANGE_TO_FIELD, 150), BorderLayout.CENTER);
        SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX.setOpaque(false);
        SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX.setText("inclusive");
        searchRangeToPanel.add(SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX, BorderLayout.PAGE_END);
        SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX.setSelected(true);
        
        SEARCH_HITS_CARD_PANEL.setLayout(SEARCH_HITS_CARD_LAYOUT);
        SEARCH_HITS_CARD_PANEL.setOpaque(false);
        rightInnerPanel.add(SEARCH_HITS_CARD_PANEL);

        JPanel searchHitsNonePanel = createPanel(p -> new BorderLayout());
        SEARCH_HITS_CARD_PANEL.add(searchHitsNonePanel, SEARCH_HITS_NONE_CARD_NAME);

        JPanel searchHitsResultPanel = createPanel(p -> new BorderLayout());
        SEARCH_HITS_CARD_PANEL.add(searchHitsResultPanel, SEARCH_HITS_RESULT_CARD_NAME);

        JTable searchHitsResultTable = new JTable(SEARCH_HITS_RESULT_TABLE_MODEL);
        setColumnWidth(searchHitsResultTable, 0, 70);
        searchHitsResultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchHitsResultTable.getColumnModel().getColumn(0).setCellRenderer(SimpleCellRenderer.instance());
        searchHitsResultTable.getColumnModel().getColumn(1).setCellRenderer(SimpleCellRenderer.instance());
        searchHitsResultTable.getTableHeader().setReorderingAllowed(false);
        searchHitsResultTable.getTableHeader().setResizingAllowed(false);
        JScrollPane searchHitsResultTableScrollPane = new JScrollPane(searchHitsResultTable);
        searchHitsResultTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        searchHitsResultTableScrollPane.setPreferredSize(new Dimension(360, 360));
        searchHitsResultPanel.add(searchHitsResultTableScrollPane, BorderLayout.CENTER);
        searchHitsResultPanel.add(new JLabel("(up to the first " + SEARCH_HITS_MAX_RESULTS + " hits)"), BorderLayout.PAGE_END);
        return rightPanel;
    }

    private static void setColumnWidth(JTable table, int i, int width) {
        TableColumn column = table.getColumnModel().getColumn(i);
        column.setMinWidth(width);
        column.setMaxWidth(width);
        column.setPreferredWidth(width);
    }
    
    private static JPanel createPanel(Function<JPanel, LayoutManager> layoutFactory) {
        return createPanel(layoutFactory, null);
    }
    
    private static JPanel createPanel(Function<JPanel, LayoutManager> layoutFactory, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(layoutFactory.apply(panel));
        panel.setOpaque(color != null);
        if (color != null) {
            panel.setBackground(color);
        }
        return panel;
    }

    private static JLabel createLabel(String text) {
        return createLabel(text, 0);
    }

    private static JLabel createLabel(String text, int width) {
        JLabel label = new JLabel(text);
        label.setOpaque(false);
        if (width > 0) {
            label.setPreferredSize(new Dimension(width, label.getPreferredSize().height));
        }
        label.setFont(DEFAULT_FONT);
        return label;
    }
    
    private static <T extends Component> T withWidth(T component, int width) {
        component.setPreferredSize(new Dimension(width, component.getPreferredSize().height));
        return component;
    }
    
    private static void addListeners() {
        SEARCH_VALUE_RADIO.addActionListener(e -> SEARCH_INPUT_CARD_LAYOUT.show(SEARCH_INPUT_CARD_PANEL, SEARCH_INPUT_VALUE_CARD_NAME));
        SEARCH_RANGE_RADIO.addActionListener(e -> SEARCH_INPUT_CARD_LAYOUT.show(SEARCH_INPUT_CARD_PANEL, SEARCH_INPUT_RANGE_CARD_NAME));
        VALUE_TYPE_COMBO.addItemListener(new SelectListener(e -> VALUES_TYPE_PROPS_CARD_LAYOUT.show(
                VALUES_TYPE_PROPS_CARD_PANEL,
                ((ComboItem) VALUE_TYPE_COMBO.getSelectedItem()).value())));
        
        List.of(SIZE_COMBO, VALUE_TYPE_COMBO, VALUE_PROPS_BUNDLE_COMBO, VALUE_PROPS_DUMMYTEXT_COMBO, DISTRIBUTION_COMBO, SHUFFLE_COMBO).forEach(
                c -> c.addItemListener(new SelectListener(e -> onValuesPropsChanged())));
        List.of(SEED_SPINNER, VALUE_PROPS_FROM_SPINNER, VALUE_PROPS_TO_SPINNER, NULLS_RATIO_SPINNER).forEach(
                s -> s.addChangeListener(e -> onValuesPropsChanged()));
        List.of(VALUE_PROPS_LIST_TEXTAREA, VALUE_PROPS_PATTERN_FIELD).forEach(
                t-> t.getDocument().addDocumentListener(new DocumentUpdateListener(e -> onValuesPropsChanged())));
        
        VALUE_SET_STATE_PANEL.addSelectListener(Main::inspectValue);

        SEARCH_VALUE_RADIO.addItemListener(e -> refreshSearch());
        SEARCH_RANGE_RADIO.addItemListener(e -> refreshSearch());
        SEARCH_VALUE_FIELD.getDocument().addDocumentListener(new DocumentUpdateListener(e -> refreshSearch()));
        SEARCH_RANGE_FROM_FIELD.getDocument().addDocumentListener(new DocumentUpdateListener(e -> refreshSearch()));
        SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX.addItemListener(e -> refreshSearch());
        SEARCH_RANGE_TO_FIELD.getDocument().addDocumentListener(new DocumentUpdateListener(e -> refreshSearch()));
        SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX.addItemListener(e -> refreshSearch());
    }
    
    private static void onValuesPropsChanged() {
        refreshValues();
        resetInspect();
        resetSearch(!getComboBoxValue(VALUE_TYPE_COMBO).equals(VALUE_TYPE_DUMMYTEXT_CARD_NAME));
    }
    
    private static void refreshValues() {
        VALUE_SET_STATE_PANEL.setState(createValueSetState());
    }

    private static ValueSetState createValueSetState() {
        try {
            return createValueSetStateThrowing();
        } catch (Exception e) {
            return ValueSetState.ofError(e.getMessage());
        }
    }
    
    private static ValueSetState createValueSetStateThrowing() {
        LargeInteger size = LargeInteger.of(getComboBoxValue(SIZE_COMBO));
        double nullRatio = ((int) NULLS_RATIO_SPINNER.getValue()) / 100d;
        LargeInteger nullCount = size.multiply(nullRatio);
        LargeInteger nonNullCount = size.subtract(nullCount);
        LargeInteger seed = LargeInteger.of((int) SEED_SPINNER.getValue());
        TreeRandom treeRandom = new HasherTreeRandom(seed);
        String valueType = getComboBoxValue(VALUE_TYPE_COMBO);
        Source<?> baseSource = createBaseSource(valueType, size, treeRandom);
        LargeInteger baseSize = baseSource.size();
        String distributionQualityName = getComboBoxValue(DISTRIBUTION_COMBO);
        Monotonic monotonic = MonotonicFactory.createMonotonic(treeRandom, nonNullCount, baseSize, DistributionQuality.valueOf(distributionQualityName));
        String shuffleQualityName = getComboBoxValue(SHUFFLE_COMBO);
        Permutation permutation = PermutationFactory.createPermutation(treeRandom, size, ShuffleQuality.valueOf(shuffleQualityName));
        return ValueSetState.ofSource(baseSource, monotonic, permutation);
    }
    
    private static Source<?> createBaseSource(String valueType, LargeInteger size, TreeRandom treeRandom) {
        if (valueType.equals(VALUE_TYPE_BUNDLE_CARD_NAME)) {
            return createBundleSource();
        } else if (valueType.equals(VALUE_TYPE_LIST_CARD_NAME)) {
            return createListSource();
        } else if (valueType.equals(VALUE_TYPE_RANGE_CARD_NAME)) {
            return createRangeSource();
        } else if (valueType.equals(VALUE_TYPE_PATTERN_CARD_NAME)) {
            return createPatternSource();
        } else if (valueType.equals(VALUE_TYPE_DUMMYTEXT_CARD_NAME)) {
            return createDummyTextSource(treeRandom, size);
        } else {
            throw new IllegalArgumentException("Unknown value type: " + valueType);
        }
    }
    
    private static Source<?> createBundleSource() {
        String bundleName = getComboBoxValue(VALUE_PROPS_BUNDLE_COMBO);
        String resource = "hu/webarticum/holodb/values/" + bundleName + ".txt";
        ClassLoader classLoader = StorageAccessFactory.class.getClassLoader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                classLoader.getResourceAsStream(resource)))) {
            List<Object> values = new LinkedList<>();
            String value;
            while ((value = reader.readLine()) != null) {
                if (!value.isEmpty()) {
                    values.add(value);
                }
            }
            return new UniqueSource<>(values.toArray(new String[0]));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Source<?> createListSource() {
        String[] values = VALUE_PROPS_LIST_TEXTAREA.getText().split("\n");
        return new UniqueSource<>(values);
    }

    private static Source<?> createRangeSource() {
        LargeInteger from = LargeInteger.of((int) VALUE_PROPS_FROM_SPINNER.getValue());
        LargeInteger to = LargeInteger.of((int) VALUE_PROPS_TO_SPINNER.getValue());
        LargeInteger size = to.subtract(from).add(LargeInteger.ONE);
        return new RangeSource(from, size);
    }

    private static IndexedSource<?> createPatternSource() {
        return new MatchListSource(MatchList.of(VALUE_PROPS_PATTERN_FIELD.getText()));
    }

    private static Source<?> createDummyTextSource(TreeRandom treeRandom, LargeInteger size) {
        String dummyTextKindName = getComboBoxValue(VALUE_PROPS_DUMMYTEXT_COMBO);
        return new DummyTextSource(DummyTextKind.valueOf(dummyTextKindName), treeRandom, size);
    }

    private static String getComboBoxValue(JComboBox<ComboItem> comboBox) {
        ComboItem comboItem = (ComboItem) comboBox.getSelectedItem();
        if (comboItem == null) {
            return "";
        }
        return comboItem.value();
    }

    private static void inspectValue(ValueSetStatePanel.ValueInfo valueInfo) {
        if (valueInfo == null) {
            resetInspect();
            return;
        }
        VALUE_INFO_CARD_LAYOUT.show(VALUE_INFO_CARD_PANEL, VALUE_INFO_INFO_CARD_NAME);
        Object value = valueInfo.value();
        String stringValue = coalesceString(value, "");
        VALUE_TEXTAREA.setText(stringValue);
        VALUE_INFO_TYPE_LABEL.setText(value == null ? "NULL" : value.getClass().getSimpleName());
        VALUE_INFO_INDEX_LABEL.setText(coalesceString(valueInfo.index(), ""));
        VALUE_INFO_ORDERED_INDEX_LABEL.setText(coalesceString(valueInfo.orderedIndex(), ""));
        VALUE_INFO_ORIGINAL_INDEX_LABEL.setText(coalesceString(valueInfo.originalIndex(), ""));
    }

    private static String coalesceString(Object value, String fallback) {
        return value != null ? value.toString() : fallback;
    }
    
    private static void resetInspect() {
        VALUE_TEXTAREA.setText("");
        VALUE_INFO_CARD_LAYOUT.show(VALUE_INFO_CARD_PANEL, VALUE_INFO_NONE_CARD_NAME);
    }

    private static void refreshSearch() {
        if (SEARCH_VALUE_RADIO.isSelected()) {
            refreshSearchValue();
        } else  {
            refreshSearchRange();
        }
    }

    private static void refreshSearchValue() {
        String valueString = SEARCH_VALUE_FIELD.getText();
        IndexedSource<?> indexedSource = getIndexedSource();
        if (valueString.isEmpty() || indexedSource == null) {
            SEARCH_HITS_CARD_LAYOUT.show(SEARCH_HITS_CARD_PANEL, SEARCH_HITS_NONE_CARD_NAME);
            return;
        }
        SEARCH_HITS_CARD_LAYOUT.show(SEARCH_HITS_CARD_PANEL, SEARCH_HITS_RESULT_CARD_NAME);
        Object value = searchValueOf(valueString);
        Selection selection = indexedSource.find(value);
        displaySearchSelection(indexedSource, selection);
    }

    private static void refreshSearchRange() {
        String fromString = SEARCH_RANGE_FROM_FIELD.getText();
        String toString = SEARCH_RANGE_TO_FIELD.getText();
        IndexedSource<?> indexedSource = getIndexedSource();
        if ((fromString.isEmpty() && toString.isEmpty()) || indexedSource == null) {
            SEARCH_HITS_CARD_LAYOUT.show(SEARCH_HITS_CARD_PANEL, SEARCH_HITS_NONE_CARD_NAME);
            return;
        }
        SEARCH_HITS_CARD_LAYOUT.show(SEARCH_HITS_CARD_PANEL, SEARCH_HITS_RESULT_CARD_NAME);
        Object fromValue = searchValueOf(fromString);
        boolean fromInclusive = SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX.isSelected();
        Object toValue = searchValueOf(toString);
        boolean toInclusive = SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX.isSelected();
        Selection selection = indexedSource.findBetween(fromValue, fromInclusive, toValue, toInclusive);
        displaySearchSelection(indexedSource, selection);
    }
    
    private static Object searchValueOf(String valueString) {
        if (valueString.isEmpty()) {
            return null;
        }
        if (getComboBoxValue(VALUE_TYPE_COMBO).equals(VALUE_TYPE_RANGE_CARD_NAME)) {
            try {
                return LargeInteger.of(valueString);
            } catch (NumberFormatException e) {
                return LargeInteger.ZERO;
            }
        } else {
            return valueString;
        }
    }

    private static void displaySearchSelection(IndexedSource<?> indexedSource, Selection selection) {
        SEARCH_HITS_RESULT_TABLE_MODEL.update(indexedSource, selection);
    }

    private static IndexedSource<?> getIndexedSource() {
        ValueSetState valueSetState = VALUE_SET_STATE_PANEL.getState();
        if (valueSetState.status() != ValueSetState.Status.SOURCE) {
            return null;
        }
        Source<?> finalSource = valueSetState.finalSource();
        if (!(finalSource instanceof IndexedSource)) {
            return null;
        }
        return (IndexedSource<?>) finalSource;
    }
    
    private static void resetSearch(boolean enable) {
        SEARCH_VALUE_FIELD.setText("");
        SEARCH_RANGE_FROM_FIELD.setText("");
        SEARCH_RANGE_TO_FIELD .setText("");
        
        SEARCH_VALUE_RADIO.setEnabled(enable);
        SEARCH_RANGE_RADIO.setEnabled(enable);
        SEARCH_VALUE_FIELD.setEnabled(enable);
        SEARCH_RANGE_FROM_FIELD.setEnabled(enable);
        SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX.setEnabled(enable);
        SEARCH_RANGE_TO_FIELD .setEnabled(enable);
        SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX.setEnabled(enable);
    }
    
    private static class SelectListener implements ItemListener {

        final Consumer<ItemEvent> selectCallback;

        private SelectListener(Consumer<ItemEvent> selectCallback) {
            this.selectCallback = selectCallback;
        }
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectCallback.accept(e);
            }
        }
        
    }
        
    private static class DocumentUpdateListener implements DocumentListener {
        
        final Consumer<DocumentEvent> updateCallback;

        private DocumentUpdateListener(Consumer<DocumentEvent> updateCallback) {
            this.updateCallback = updateCallback;
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateCallback.accept(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateCallback.accept(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateCallback.accept(e);
        }
        
    }

    private static class ComboItem {
        
        final String value;
        
        final String label;
        
        ComboItem(String value, String label) {
            this.value = value;
            this.label = label;
        }
        
        String value() {
            return value;
        }
        
        @Override
        public String toString() {
            return label;
        }
        
    }

    private static class SearchHitsTableModel extends AbstractTableModel {
        
        private static final long serialVersionUID = 1L;
        
        private IndexedSource<?> indexedSource = new RangeSource(LargeInteger.ZERO);
        private Selection selection = EmptySelection.instance();
        
        public void update(IndexedSource<?> indexedSource, Selection selection) {
            this.indexedSource = indexedSource;
            this.selection = selection;
            fireTableDataChanged();
        }
        
        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return selection.size().min(LargeInteger.of(SEARCH_HITS_MAX_RESULTS)).intValue();
        }
    
        @Override
        public Object getValueAt(int row, int col) {
            LargeInteger index = selection.at(LargeInteger.of(row));
            if (col == 0) {
                return index;
            } else if (col == 1) {
                return indexedSource.get(index);
            } else {
                return "";
            }
        }
        
        @Override
        public String getColumnName(int column) {
            return switch (column) {
                case 0 -> "Index";
                case 1 -> "Value";
                default -> "";
            };
        }
    }
    
}
