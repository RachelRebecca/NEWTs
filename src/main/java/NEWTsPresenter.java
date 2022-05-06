import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import json.Spell;
import json.SpellList;

import java.util.Random;

public class NEWTsPresenter
{
    private NEWTsPracticeTest view;
    private SpellGenerator model;
    private Disposable disposable;

    boolean spellSelected = false;
    String name = null;
    String incantation = null;
    String effect = null;

    int totalAsked = 0;
    int totalCorrect = 0;

    public NEWTsPresenter(NEWTsPracticeTest view, SpellGenerator model)
    {
        this.view = view;
        this.model = model;
    }

    public void loadSpellInformation(String category)
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
        view.setEffect(effect);
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
    }
}
