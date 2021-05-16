package com.dinosaurs.features.search;

import com.dinosaurs.steps.serenity.EndUserSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/login_bad_data.csv")
public class TestLoginBad {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    private String word, description;

    @Test
    public void login_bad_data() throws InterruptedException {
        webdriver.get("https://www.scs.ubbcluj.ro/webmail/");
        webdriver.findElement(By.id("rcmloginuser")).sendKeys(word);
        webdriver.findElement(By.id("rcmloginpwd")).sendKeys(description);
        webdriver.findElement(By.id("rcmloginsubmit")).click();
        Thread.sleep(3000);
        assertTrue(webdriver.findElement(By.id("rcmloginsubmit")).isDisplayed());
    }
}
