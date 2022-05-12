import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import json.Spell;
import json.SpellList;
import json.WizardWorldAPI;
import json.WizardWorldAPIFactory;

import javax.swing.*;
import java.util.Random;

public class NEWTsPresenter
{
    private NEWTsPracticeExam view;
    private WizardWorldAPI model;
    private Disposable disposable;
    private Random rand;

    // the following values are package private for the purpose of testing:
    boolean spellSelected = false;
    String name = null;
    String incantation = null;
    String effect = null;
    String category = null;

    int totalAsked = 0;
    int totalCorrect = 0;
    private boolean practiceTestOver = false;

    public NEWTsPresenter(NEWTsPracticeExam view, WizardWorldAPI model)
    {
        this.view = view;
        this.model = model;

        this.rand = new Random();
    }

    private void resetFlashCard(String category)
    {
        if (category.equals("--"))
        {
            view.resetToDefaults();
            if (!practiceTestOver)
            {
                view.setCategorySelected("No Category Selected.");
            }
        }
    }

    public void loadSpellInformation(String category)
    {
        if (!category.equals("--"))
        {
            view.setCategorySelected("");
            getNewQuestion(category);
        } else
        {
            resetFlashCard(category);
        }
    }

    private void getNewQuestion(String category)
    {
        disposable = model.getSpell(category)
                .subscribeOn(Schedulers.io()) // do this request in the background
                .observeOn(Schedulers.newThread())   // run onNext in a new thread
                .subscribe(this::onNext, this::onError);
    }

    private void onNext(SpellList spells)
    {
        int index = getRandomSpellIndex(spells);

        Spell spell = spells.get(index);

        spellSelected = true;
        effect = spell.getEffect();
        name = spell.getName();
        incantation = spell.getIncantation();
        category = spell.getType();
        view.setEffect("<html>" + effect + "</html>");
    }

    private int getRandomSpellIndex(SpellList spells)
    {
        return rand.nextInt(spells.size());
    }

    private void onError(Throwable throwable)
    {
        view.setEffect((throwable.getMessage() == null) ? "Something went wrong" : throwable.getMessage());
        throwable.printStackTrace();
    }

    public void onSubmitAnswer(String text)
    {
        checkAnswer(text);
        potentiallyEndPracticeExam();
    }

    private void checkAnswer(String text)
    {
        if (spellSelected)
        {
            view.resetIncantation();
            totalAsked++;

            String value = (incantation == null) ? name : incantation;
            if (text.equalsIgnoreCase(value))
            {
                totalCorrect++;
                view.setResult("Correct!");
            } else
            {
                view.setResult("Incorrect, the correct answer was " + value);
            }
        } else
        {
            view.setResult("You must select a category first.");
        }
    }

    private void potentiallyEndPracticeExam()
    {
        int outOf = 10;
        if (totalAsked == outOf)
        {
            double percent = ((totalCorrect + 0.0 / totalAsked) * outOf);
            view.makeJOptionPaneAppear("You scored " + totalCorrect + " / " + totalAsked + ": " + percent + "%");

            practiceTestOver = true;
            view.setCategorySelectedIndex(0);
            resetFlashCard("--");
            resetDefaults();

        } else if (spellSelected)
        {
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            getNewQuestion(category);
        }
    }

    private void resetDefaults()
    {
        spellSelected = false;
        name = null;
        incantation = null;
        effect = null;
        category = null;

        totalAsked = 0;
        totalCorrect = 0;
        practiceTestOver = false;
    }
}
