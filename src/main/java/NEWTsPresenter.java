import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import json.Spell;
import json.SpellList;

import javax.swing.*;
import java.util.Random;

public class NEWTsPresenter
{
    private NEWTsPracticeExam view;
    private SpellGenerator model;
    private Disposable disposable;

    boolean spellSelected = false;
    String name = null;
    String incantation = null;
    String effect = null;
    String category = null;

    int totalAsked = 0;
    int totalCorrect = 0;

    public NEWTsPresenter(NEWTsPracticeExam view, SpellGenerator model)
    {
        this.view = view;
        this.model = model;
    }

    public void resetFlashCard(String category)
    {
        if (category.equals("--"))
        {
            view.setEffect("Effect Goes Here");
            view.resetIncantation();
            view.setResult("");
        }
    }

    public void loadSpellInformation(String category)
    {
        if (!category.equals("--"))
        {
            view.setCategorySelected("");
            getNewQuestion(category);
        }
        else
        {
            view.setCategorySelected("No Category Selected.");
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
        Random rand = new Random();
        return rand.nextInt(spells.size());
    }

    private void onError(Throwable throwable)
    {
        view.setEffect((throwable.getMessage() == null) ? "Something went wrong" : throwable.getMessage());
        throwable.printStackTrace();
    }

    public void checkAnswer(String text)
    {
        if (spellSelected)
        {
            totalAsked++;

            String value = (incantation == null) ? name : incantation;
            if (text.equalsIgnoreCase(value))
            {
                totalCorrect++;
                view.setResult("Correct!");
            }
            else
            {
                view.setResult("Incorrect, the correct answer was " + value);
            }
        }
        else
        {
            view.setResult("You must select a category first.");
        }
    }

    public void potentiallyEndPracticeExam()
    {
        int outOf = 10;
        if (totalAsked == outOf)
        {
            double percent = ((totalCorrect + 0.0 / totalAsked) * outOf);
            JOptionPane.showMessageDialog(null,
                    "You scored " + totalCorrect + " / " + totalAsked + ": " + percent + "%");

            resetDefaults();
            view.setCategorySelectedIndex(0);
            resetFlashCard("--");
        }
        else if (spellSelected)
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
    }
}
