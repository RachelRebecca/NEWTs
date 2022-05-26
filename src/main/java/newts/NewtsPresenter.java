package newts;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import newts.json.Spell;
import newts.json.SpellList;
import newts.json.WizardWorldService;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class NewtsPresenter
{
    private final Provider<NewtsPracticeExam> viewProvider;
    private WizardWorldService model;
    private Disposable disposable;
    private Random rand;

    // the following values are package private for the purpose of testing:
    boolean spellSelected = false;
    Spell currSpell = null;

    int totalAsked = 0;
    int totalCorrect = 0;
    private boolean practiceTestOver = false;

    @Inject
    public NewtsPresenter(Provider<NewtsPracticeExam> view, WizardWorldService model)
    {
        this.viewProvider = view;
        this.model = model;

        this.rand = new Random();
    }

    private void resetFlashCard(String category)
    {
        if (category.equals("--"))
        {
            viewProvider.get().resetToDefaults();
            if (!practiceTestOver)
            {
                viewProvider.get().setCategorySelected("No Category Selected.");
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
            viewProvider.get().setCategorySelected("");
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

        viewProvider.get().setEffect("<html>" + currSpell.getEffect() + "</html>");

        viewProvider.get().setSpellColor(currSpell.getLight());
    }

    private int getRandomSpellIndex(SpellList spells)
    {
        return rand.nextInt(spells.size());
    }

    private void onError(Throwable throwable)
    {
        viewProvider.get().setEffect((throwable.getMessage() == null)
                ? "Something went wrong" : throwable.getMessage());
        throwable.printStackTrace();
    }

    public void onSubmitAnswer(String text)
    {
        if (viewProvider.get().getCategory().equals("--"))
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
            viewProvider.get().resetIncantation();
            totalAsked++;

            String value = (currSpell.getIncantation() == null)
                    ? currSpell.getName()
                    : currSpell.getIncantation();
            if (text.equalsIgnoreCase(value))
            {
                totalCorrect++;
                viewProvider.get().setResult("Correct!");
            } else
            {
                viewProvider.get().setResult("Incorrect, the correct answer was " + value);
            }
        } else
        {
            viewProvider.get().setResult("You must select a category first.");
        }
    }

    private void potentiallyEndPracticeExam()
    {
        int outOf = 10;
        if (totalAsked == outOf)
        {
            double percent = ((totalCorrect + 0.0 / totalAsked) * outOf);
            viewProvider.get().showResultsMessage("You scored " + totalCorrect + " / "
                    + totalAsked + ": " + percent + "%");

            practiceTestOver = true;
            viewProvider.get().setCategorySelectedIndex(0);
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
