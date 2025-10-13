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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Main {
    
    private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 32);
    
    private static final Font SUBTITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    
    private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    
    private static final Font CONTROL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    
    private static final Font CONTROL_MONO_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    
    
    private static final JComboBox<ComboItem> SIZE_COMBO = new JComboBox<>();

    private static final JSpinner SEED_SPINNER = new JSpinner();

    private static final JComboBox<ComboItem> VALUE_TYPE_COMBO = new JComboBox<>();
    
    private static final JPanel VALUES_TYPE_PROPS_CARD_PANEL = new JPanel();
    
    private static final CardLayout VALUES_TYPE_PROPS_CARD_LAYOUT = new CardLayout();
    
    private static final String VALUE_TYPE_BUNDLE_CARD_NAME = "bundle";
    
    private static final String VALUE_TYPE_LIST_CARD_NAME = "list";
    
    private static final String VALUE_TYPE_RANGE_CARD_NAME = "range";
    
    private static final String VALUE_TYPE_PATTERN_CARD_NAME = "pattern";
    
    private static final String VALUE_TYPE_DUMMYTEXT_CARD_NAME = "dummytext";
    
    private static final JComboBox<ComboItem> VALUE_PROPS_BUNDLE_COMBO = new JComboBox<>();

    private static final JTextArea VALUE_PROPS_LIST_TEXTAREA = new JTextArea();
    
    private static final JSpinner VALUE_PROPS_FROM_SPINNER = new JSpinner();
    
    private static final JSpinner VALUE_PROPS_TO_SPINNER = new JSpinner();
    
    private static final JTextField VALUE_PROPS_PATTERN_FIELD = new JTextField();
    
    private static final JComboBox<ComboItem> VALUE_PROPS_DUMMYTEXT_COMBO = new JComboBox<>();
    
    private static final JComboBox<ComboItem> DISTRIBUTION_COMBO = new JComboBox<>();
    
    private static final JComboBox<ComboItem> SHUFFLE_COMBO = new JComboBox<>();

    private static final JSpinner NULLS_RATIO_SPINNER = new JSpinner();

    
    private static final JTextArea VALUE_TEXTAREA = new JTextArea();

    private static final CardLayout VALUE_INFO_CARD_LAYOUT = new CardLayout();
    
    private static final JPanel VALUE_INFO_CARD_PANEL = new JPanel();
    
    private static final String VALUE_INFO_NONE_CARD_NAME = "none";
    
    private static final String VALUE_INFO_INFO_CARD_NAME = "info";
    
    
    private static final JRadioButton SEARCH_VALUE_RADIO = new JRadioButton();
    
    private static final JRadioButton SEARCH_RANGE_RADIO = new JRadioButton();
    
    private static final JPanel SEARCH_INPUT_CARD_PANEL = new JPanel();
    
    private static final CardLayout SEARCH_INPUT_CARD_LAYOUT = new CardLayout();
    
    private static final String SEARCH_INPUT_VALUE_CARD_NAME = "value";
    
    private static final JTextField SEARCH_VALUE_FIELD = new JTextField();
    
    private static final String SEARCH_INPUT_RANGE_CARD_NAME = "rage";

    private static final JTextField SEARCH_RANGE_FROM_FIELD = new JTextField();

    private static final JCheckBox SEARCH_RANGE_FROM_INCLUSIVE_CHECKBOX = new JCheckBox();

    private static final JTextField SEARCH_RANGE_TO_FIELD = new JTextField();

    private static final JCheckBox SEARCH_RANGE_TO_INCLUSIVE_CHECKBOX = new JCheckBox();
    
    
    public static void main(String[] args) throws ReflectiveOperationException, InterruptedException, UnsupportedLookAndFeelException {
        setNimbus();
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

    private static void buildAndShowFrame() {
        JFrame frame = new JFrame("Value Generator Demo");
        buildFrameContent(frame.getContentPane());
        addListeners();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.setVisible(true);
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        device.setFullScreenWindow(frame);
    }
    
    private static void buildFrameContent(Container contentPane) {
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buildTopPanel(), BorderLayout.PAGE_START);
        contentPane.add(buildLeftPanel(), BorderLayout.LINE_START);
        contentPane.add(buildCenterPanel(), BorderLayout.LINE_END);
        contentPane.add(buildRightPanel(), BorderLayout.LINE_END);
    }

    private static JPanel buildTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        
        topPanel.setPreferredSize(new Dimension(500, 100));
        topPanel.setBorder(new EmptyBorder(20, 100, 20, 20));
        topPanel.setBackground(new Color(0x99DD77));
        JLabel titleLabel = new JLabel("Value Generator Demo");
        titleLabel.setFont(TITLE_FONT);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        return topPanel;
    }

    private static JPanel buildLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(650, 650));
        leftPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        leftPanel.setBackground(Color.YELLOW);

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
        sizeComboModel.addElement(new ComboItem("1500", "1500"));
        sizeComboModel.addElement(new ComboItem("431378", "431 378"));
        sizeComboModel.addElement(new ComboItem("270000000", "270 000 000"));
        SIZE_COMBO.setModel(sizeComboModel);
        SIZE_COMBO.setSelectedIndex(1);
        sizePanel.add(withWidth(SIZE_COMBO, 220), BorderLayout.CENTER);
        
        JPanel seedPanel = createPanel(p -> new BorderLayout());
        sizeAndSeedPanel.add(seedPanel);
        seedPanel.add(createLabel("Seed: "), BorderLayout.LINE_START);
        SEED_SPINNER.setFont(CONTROL_FONT);
        SEED_SPINNER.setModel(new SpinnerNumberModel(98765, 0, Integer.MAX_VALUE, 1));
        seedPanel.add(withWidth(SEED_SPINNER, 220), BorderLayout.CENTER);
        
        JPanel baseValuesPanel = createPanel(p -> new BorderLayout(), Color.RED);
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
        VALUE_PROPS_LIST_TEXTAREA.setMargin(new Insets(7, 7, 7, 7));
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
        DISTRIBUTION_COMBO.setSelectedIndex(1);
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
        JPanel centerPanel = new JPanel();
        
        centerPanel.setPreferredSize(new Dimension(650, 650));
        centerPanel.setBorder(new EmptyBorder(100, 10, 10, 10));
        centerPanel.setBackground(new Color(0xCCBBFF));
        centerPanel.add(new JLabel("Value properties"));
        
        return centerPanel;
    }

    private static JPanel buildRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(650, 650));
        rightPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        rightPanel.setBackground(new Color(0xAA99FF));

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
        
        JPanel card1 = createPanel(p -> new BorderLayout());
        VALUE_INFO_CARD_PANEL.add(card1, VALUE_INFO_NONE_CARD_NAME);
        card1.add(createLabel("No value selected"));

        JPanel card2 = createPanel(p -> new BorderLayout());
        VALUE_INFO_CARD_PANEL.add(card2, VALUE_INFO_INFO_CARD_NAME);
        card2.add(createLabel("VALUE INFO..."));

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
        
        JPanel hitsPlaceholder = createPanel(p -> new BorderLayout(), Color.ORANGE);
        rightInnerPanel.add(hitsPlaceholder);
        hitsPlaceholder.setPreferredSize(new Dimension(300, 300));

        return rightPanel;
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
        VALUE_TYPE_COMBO.addItemListener(e -> VALUES_TYPE_PROPS_CARD_LAYOUT.show(
                VALUES_TYPE_PROPS_CARD_PANEL,
                ((ComboItem) VALUE_TYPE_COMBO.getSelectedItem()).value()));
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
    
}
