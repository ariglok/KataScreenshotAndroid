package com.karumi.screenshot;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.karumi.screenshot.di.MainComponent;
import com.karumi.screenshot.di.MainModule;
import com.karumi.screenshot.model.SuperHero;
import com.karumi.screenshot.model.SuperHeroesRepository;
import com.karumi.screenshot.ui.view.MainActivity;
import com.karumi.screenshot.ui.view.SuperHeroDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.when;

/**
 * Created by gloria on 28/4/17.
 */

public class SuperHeroDetailActivityTest  extends ScreenshotTest {

    @Rule
    public DaggerMockRule<MainComponent> daggerRule =
            new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
                    new DaggerMockRule.ComponentSetter<MainComponent>() {
                        @Override public void setComponent(MainComponent component) {
                            SuperHeroesApplication app =
                                    (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();
                            app.setComponent(component);
                        }
                    });

    @Rule public IntentsTestRule<SuperHeroDetailActivity> activityRule =
            new IntentsTestRule<>(SuperHeroDetailActivity.class, true, false);

    @Mock
    SuperHeroesRepository repository;

    @Test
    public void showsRegularSuperHero() {
        SuperHero superHero = givenThereIsASuperHero();

        SuperHeroDetailActivity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test
    public void showsSuperHeroAvengersBadge() {
        SuperHero superHero = givenThereIsASuperHero(true);

        SuperHeroDetailActivity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test
    public void ShowSHWhitEmptyDescription() {
        SuperHero superHero = givenThereIsASuperHeroEmptyDescription(false);

        SuperHeroDetailActivity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test
    public void ShowWhitEmptyName() {
        SuperHero superHero = givenThereIsASuperHeroEmptyName(false);

        SuperHeroDetailActivity activity = startActivity(superHero);

        compareScreenshot(activity);
    }


    private SuperHero givenThereIsASuperHero() {
        return givenThereIsASuperHero(false);
    }

    private SuperHero givenAnAvenger() {
        return givenThereIsASuperHero(true);
    }

    private SuperHero givenThereIsASuperHero(boolean isAvenger) {
        String superHeroName = "SuperHero";
        String superHeroPhoto = "https://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa.jpg";
        String superHeroDescription = "Super Hero Description";
        SuperHero superHero =
                new SuperHero(superHeroName, superHeroPhoto, isAvenger, superHeroDescription);
        when(repository.getByName(superHeroName)).thenReturn(superHero);
        return superHero;
    }

    private SuperHeroDetailActivity startActivity(SuperHero superHero) {
        Intent intent = new Intent();
        intent.putExtra("super_hero_name_key", superHero.getName());
        return activityRule.launchActivity(intent);
    }

    private SuperHero givenThereIsASuperHeroEmptyName(boolean isAvenger) {
        String superHeroName = "";
        String superHeroPhoto = "https://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa.jpg";
        String superHeroDescription = "Super Hero Description";
        SuperHero superHero =
                new SuperHero(superHeroName, superHeroPhoto, isAvenger, superHeroDescription);
        when(repository.getByName(superHeroName)).thenReturn(superHero);
        return superHero;
    }

    private SuperHero givenThereIsASuperHeroEmptyDescription(boolean isAvenger) {
        String superHeroName = "SuperHero";
        String superHeroPhoto = "https://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa.jpg";
        String superHeroDescription = "";
        SuperHero superHero =
                new SuperHero(superHeroName, superHeroPhoto, isAvenger, superHeroDescription);
        when(repository.getByName(superHeroName)).thenReturn(superHero);
        return superHero;
    }

    private void scrollToView(int viewId) {
        onView(withId(viewId)).perform(scrollTo());
    }

}
