package com.dinosaurs.features.search;

import com.dinosaurs.steps.serenity.EndUserSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

//@RunWith(SerenityParameterizedRunner.class)
@RunWith(SerenityRunner.class)
@UseTestDataFrom("src/test/resources/login_good_data.csv")
public class TestLoginGood {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    private String word, description;

    @Steps
    public EndUserSteps anna;

    @Test
    public void login_good_data() throws InterruptedException {
//        webdriver.get("https://www.scs.ubbcluj.ro/webmail/");
//        webdriver.findElement(By.id("rcmloginuser")).sendKeys(word);
//        webdriver.findElement(By.id("rcmloginpwd")).sendKeys(description);
//        webdriver.findElement(By.id("rcmloginsubmit")).click();

        anna.is_the_home_page();
//        anna.login_action(word, description);
        anna.login_action("cgir2476","ebb54#24d5");

        Thread.sleep(2000);
//        assertTrue(webdriver.findElement(By.id("rcmbtn103")).isDisplayed());
        anna.assertLoginSuccess();
    }
}


