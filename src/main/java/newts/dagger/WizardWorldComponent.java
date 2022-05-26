package newts.dagger;

import javax.inject.Singleton;

import dagger.Component;
import newts.NewtsPracticeExam;

@Component(modules = {WizardWorldModule.class})
@Singleton
public interface WizardWorldComponent
{
    NewtsPracticeExam getNewtsPracticeExam();
}
