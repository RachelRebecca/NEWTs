import json.SpellList;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class NEWTsPracticeTest extends JFrame
{
    private JPanel verticalPanel;

    private final String[] availableCategories =
            {"None", "Charm", "Conjuration", "Spell", "Transfiguration", "HealingSpell",
            "DarkCharm", "Jinx", "Curse", "MagicalTransportation", "Hex", "CounterSpell",
            "DarkArts", "CounterJinx", "CounterCharm", "Untransfiguration",
            "BindingMagicalContract", "Vanishment"};
    private JComboBox<String> category;
    private JButton categorySubmitButton;

    private JLabel instructions;
    private JLabel effect;
    private JTextField incantation;

    private JButton submitAnswer;
    private JLabel results;

    private NEWTsPresenter presenter;

    public NEWTsPracticeTest()
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
        setTitle("Nemesure's Exhausting Wizarding Tests");
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
        presenter = new NEWTsPresenter(this, new SpellGenerator());

        addChooseCategoryPanel();

        addInstructionsPanel();

        addSpellAndEffectPanel();

        addSubmitAndResultsPanel();
    }

    private void addChooseCategoryPanel()
    {
        JPanel chooseCategory = new JPanel();
        chooseCategory.setLayout(new FlowLayout());

        category = new JComboBox<>(availableCategories);
        chooseCategory.add(category);

        categorySubmitButton = new JButton("Select Spell Category");
        categorySubmitButton.addActionListener(this::onSubmitCategory);
        chooseCategory.add(categorySubmitButton);

        verticalPanel.add(chooseCategory);
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

        verticalPanel.add(instructionsPanel);
    }

    private void addSpellAndEffectPanel()
    {
        JPanel spellAndEffect = new JPanel();
        spellAndEffect.setLayout(new FlowLayout());

        effect = new JLabel();
        effect.setPreferredSize(new Dimension(400, 40));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        effect.setBorder(border);
        effect.setText("Please write the incantation here: ");
        spellAndEffect.add(effect);

        incantation = new JTextField();
        incantation.setPreferredSize(new Dimension(200, 40));
        spellAndEffect.add(incantation);

        verticalPanel.add(spellAndEffect);
    }

    private void addSubmitAndResultsPanel()
    {
        JPanel submitAndResults = new JPanel();
        submitAndResults.setLayout(new FlowLayout());

        results = new JLabel();
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        results.setBorder(border);
        results.setText("Your Results Go Here: ");
        submitAndResults.add(results);

        submitAnswer = new JButton("Submit Your Answer");
        submitAnswer.addActionListener(this::onSubmitClicked);
        submitAndResults.add(submitAnswer);

        verticalPanel.add(submitAndResults);
    }

    private void onSubmitCategory(ActionEvent actionEvent)
    {
        presenter.loadSpellInformation(Objects.requireNonNull(category.getSelectedItem()).toString());
    }

    public void setEffect(String spellEffect)
    {
        effect.setText(spellEffect);
    }

    private void onSubmitClicked(ActionEvent actionEvent)
    {
        presenter.checkAnswer(incantation.getText());
    }

    public void setResult(String result)
    {
        results.setText(result);
    }

    public static void main(String[] args)
    {
        JFrame jFrame = new NEWTsPracticeTest();

        jFrame.setVisible(true);
    }

}
