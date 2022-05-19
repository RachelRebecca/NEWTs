import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import json.Spell;
import json.SpellList;
import json.WizardWorldService;

import java.awt.*;
import java.util.Random;

public class NewtsPresenter
{
    private NewtsPracticeExam view;
    private WizardWorldService model;
    private Disposable disposable;
    private Random rand;

    // the following values are package private for the purpose of testing:
    boolean spellSelected = false;
    Spell currSpell = null;

    int totalAsked = 0;
    int totalCorrect = 0;
    private boolean practiceTestOver = false;

    public NewtsPresenter(NewtsPracticeExam view, WizardWorldService model)
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
        if (category.equals("--"))
        {
            resetFlashCard(category);
        } else
        {
            view.setCategorySelected("");
            getNewQuestion(category);
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

        currSpell = spell;

        view.setEffect("<html>" + currSpell.getEffect() + "</html>");

        view.setSpellColor(currSpell.getLight());
    }

    private int getRandomSpellIndex(SpellList spells)
    {
        return rand.nextInt(spells.size());
    }

    private void onError(Throwable throwable)
    {
        view.setEffect((throwable.getMessage() == null)
                ? "Something went wrong" : throwable.getMessage());
        throwable.printStackTrace();
    }

    public void onSubmitAnswer(String text)
    {
        if (view.getCategory().equals("--"))
        {
            spellSelected = false;
        }
        checkAnswer(text);
        potentiallyEndPracticeExam();
    }

    private void checkAnswer(String text)
    {
        if (spellSelected)
        {
            view.resetIncantation();
            totalAsked++;

            String value = (currSpell.getIncantation() == null)
                    ? currSpell.getName()
                    : currSpell.getIncantation();
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
            view.showResultsMessage("You scored " + totalCorrect + " / "
                    + totalAsked + ": " + percent + "%");

            practiceTestOver = true;
            view.setCategorySelectedIndex(0);
            resetFlashCard("--");
            resetDefaults();
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
            getNewQuestion(currSpell.getType());
        }
    }

    private void resetDefaults()
    {
        spellSelected = false;
        currSpell = null;

        totalAsked = 0;
        totalCorrect = 0;
        practiceTestOver = false;
    }
}
