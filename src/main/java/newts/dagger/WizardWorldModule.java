package newts.dagger;

import dagger.Module;
import dagger.Provides;
import newts.json.WizardWorldService;
import newts.json.WizardWorldServiceFactory;

import javax.inject.Singleton;

@Module
public class WizardWorldModule
{
    @Singleton
    @Provides
    public WizardWorldService providesWizardWorldService(WizardWorldServiceFactory factory)
    {
        return factory.getInstance();
    }
}
