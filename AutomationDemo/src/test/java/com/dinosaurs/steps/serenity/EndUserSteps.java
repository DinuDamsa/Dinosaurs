package com.dinosaurs.steps.serenity;

import com.dinosaurs.pages.DictionaryPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.hamcrest.CoreMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps {

    DictionaryPage dictionaryPage;

    @Step
    public void is_the_home_page() {
        dictionaryPage.open();
    }

    @Step
    public void enter_credentials(String str_username, String str_password) {
        dictionaryPage.enter_keywords(str_username, str_password);
    }

    @Step
    public void click_login() {
        dictionaryPage.click_login();
    }

    @Step
    public void login_action(String str_username, String str_password) {
        enter_credentials(str_username, str_password);
        click_login();
    }

    @Step
    public void assertLoginSuccess() {
        assertThat(dictionaryPage.getLoginDone(), CoreMatchers.equalTo(true));
    }

    @Step
    public void assertLoginFailed() {
        assertThat(dictionaryPage.getLoginDone(), CoreMatchers.equalTo(false));
    }

    @Step
    public void access_email() {
        dictionaryPage.access_email();
    }

    @Step
    public void click_email() {
        dictionaryPage.click_email();
    }

    @Step
    public void assertMessageBodyCorrect() {
        assertThat(dictionaryPage.assertEmailBodyCorrect(), CoreMatchers.equalTo(true));
    }

    @Step
    public void click_logout() {
        dictionaryPage.click_logout();
    }

    @Step
    public void assertLogout() {
        dictionaryPage.assert_logout_success();
    }
}