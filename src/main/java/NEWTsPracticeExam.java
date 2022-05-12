import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import json.WizardWorldAPIFactory;

public class NEWTsPracticeExam extends JFrame
{
    private JPanel verticalPanel;

    private final String[] availableCategories =
            {"--", "Charm", "Conjuration", "Spell", "Transfiguration", "HealingSpell",
                    "DarkCharm", "Jinx", "Curse", "MagicalTransportation", "Hex", "CounterSpell",
                    "DarkArts", "CounterJinx", "CounterCharm", "Untransfiguration",
                    "BindingMagicalContract", "Vanishment"};
    private JComboBox<String> category;
    private JLabel categorySelected;

    private JPanel flashCards;
    private JLabel instructions;
    private JLabel effect;
    private JTextField incantation;

    private JButton submitAnswer;
    private JLabel results;

    private NEWTsPresenter presenter;

    public NEWTsPracticeExam()
    {
        setForm();

        setVerticalPanel();

        setInitialValues();
    }

    /**
     * Set the default settings of the Form
     */
    private void setForm()
    {
        setTitle("Nemesure's Exhausting Wizarding Tests Practice Examination");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
    }

    private void setVerticalPanel()
    {
        verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        add(verticalPanel);
    }

    private void setInitialValues()
    {
        // presenter = new NEWTsPresenter(this, new SpellGenerator());

        WizardWorldAPIFactory factory = new WizardWorldAPIFactory();
        presenter = new NEWTsPresenter(this, factory.getInstance());

        addChooseCategoryPanel();

        addFlashCardPanel();

        addSubmitAndResultsPanel();
    }

    private void addChooseCategoryPanel()
    {
        JPanel chooseCategory = new JPanel();
        chooseCategory.setLayout(new FlowLayout());

        category = new JComboBox<>(availableCategories);
        category.addActionListener(this::onSelectCategory);
        chooseCategory.add(category);

        categorySelected = new JLabel();
        chooseCategory.add(categorySelected);

        verticalPanel.add(chooseCategory);
    }

    private void addFlashCardPanel()
    {
        flashCards = new JPanel();
        flashCards.setLayout(new BoxLayout(flashCards, BoxLayout.Y_AXIS));

        addInstructionsPanel();

        addSpellAndEffectPanel();

        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        flashCards.setBorder(border);
        verticalPanel.add(flashCards);
    }

    private void addInstructionsPanel()
    {
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new FlowLayout());

        instructions = new JLabel();
        String instructionsText = "<html>Write out the incantation that causes the following effect. <br/>" +
                "In the absence of a formal incantation, please write the name of the spell.</html>";
        instructions.setText(instructionsText);
        instructionsPanel.add(instructions);

        flashCards.add(instructionsPanel);
    }

    private void addSpellAndEffectPanel()
    {
        JPanel spellAndEffect = new JPanel();
        spellAndEffect.setLayout(new FlowLayout());

        effect = new JLabel();
        effect.setPreferredSize(new Dimension(400, 40));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        effect.setBorder(border);
        effect.setText("Effect Goes Here");
        spellAndEffect.add(effect);

        incantation = new JTextField();
        incantation.setPreferredSize(new Dimension(200, 40));
        spellAndEffect.add(incantation);

        flashCards.add(spellAndEffect);
    }

    private void addSubmitAndResultsPanel()
    {
        JPanel submitAndResults = new JPanel();
        submitAndResults.setLayout(new FlowLayout());

        results = new JLabel();
        submitAndResults.add(results);

        submitAnswer = new JButton("Submit Your Answer");
        submitAnswer.addActionListener(this::onSubmitClicked);
        submitAndResults.add(submitAnswer);

        verticalPanel.add(submitAndResults);
    }

    public void setCategorySelectedIndex(int i)
    {
        category.setSelectedIndex(i);
    }

    public void setCategorySelected(String categorySelectedText)
    {
        categorySelected.setText(categorySelectedText);
    }

    private void onSelectCategory(ActionEvent actionEvent)
    {
        String selectedCategory = Objects.requireNonNull(category.getSelectedItem()).toString();
        presenter.loadSpellInformation(selectedCategory);
    }

    public void setEffect(String spellEffect)
    {
        effect.setText(spellEffect);
    }

    public void resetToDefaults()
    {
        setEffect("Effect Goes Here");
        resetIncantation();
        setResult("");
    }

    public void resetIncantation()
    {
        incantation.setText("");
    }

    private void onSubmitClicked(ActionEvent actionEvent)
    {
        presenter.onSubmitAnswer(incantation.getText());
    }

    public void setResult(String result)
    {
        results.setText(result);
    }

    public void makeJOptionPaneAppear(String text)
    {
        JOptionPane.showMessageDialog(null, text);
    }

    public static void main(String[] args)
    {
        JFrame jFrame = new NEWTsPracticeExam();

        jFrame.setVisible(true);
    }
}
