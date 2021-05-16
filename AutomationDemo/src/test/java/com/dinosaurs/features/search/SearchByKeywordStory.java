package com.dinosaurs.features.search;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@RunWith(SerenityRunner.class)
public class SearchByKeywordStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Issue("#WIKI-1")
    @Test
    public void senario_login_sendEmail_logout() throws InterruptedException { // verifica mail

        //login
        webdriver.get("https://www.scs.ubbcluj.ro/webmail/");
        webdriver.findElement(By.id("rcmloginuser")).sendKeys("cgir2476");
        webdriver.findElement(By.id("rcmloginpwd")).sendKeys("ebb54#24d5");
        webdriver.findElement(By.id("rcmloginsubmit")).click();


        System.out.println("LOGGED IN");

        //verify login
        assert (webdriver.findElement(By.id("rcmbtn103")).isDisplayed());

        System.out.println("VERIFIED LOGIN");

        //send email
        WebDriverWait wait = new WebDriverWait(webdriver,15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("messagelist")));

        wait.withTimeout(Duration.ofSeconds(10));
        Thread.sleep(2000);

//        webdriver.findElement(By.id("rcmbtn117")).click();

//        Thread.sleep(2000);
//        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("_to")));
//        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("compose-subject")));
//        webdriver.findElement(By.id("_to")).sendKeys("diir2495@scs.ubbcluj.ro");
//        webdriver.findElement(By.id("compose-subject")).sendKeys("Lab6_VVSS");
//        webdriver.findElement(By.id("compose-body")).sendKeys("Bacon ipsum dolor amet pork belly shankle buffalo capicola, shank venison turkey alcatra spare ribs sirloin turducken beef chislic. Jowl spare ribs porchetta chislic chicken kielbasa bresaola filet mignon ground round fatback. Boudin shoulder pork chop chicken tri-tip kevin cow, hamburger ham frankfurter pork belly. Porchetta tri-tip venison meatloaf ham tail pancetta filet mignon kielbasa jowl chislic turkey. Shankle leberkas porchetta ground round jerky beef ribs landjaeger capicola meatloaf chicken sirloin pork loin frankfurter pork chop bacon.");
//        webdriver.findElement(By.id("rcmbtn105")).click();

        System.out.println("SENT EMAIL");
//        Thread.sleep(10000);
        //verify sent
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rcmliU2VudA")));
        Thread.sleep(2000);
        webdriver.findElement(By.id("rcmliU2VudA")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rcmrow66"))); // TODO: rcmrow67
        Thread.sleep(2000);
        webdriver.findElement(By.id("rcmrow66")).click();
        webdriver.findElement(By.id("rcmrow66")).click();
        Thread.sleep(2000);
        assert (webdriver.findElement(By.id("messagebody")).getText().equals("Bacon ipsum dolor amet pork belly shankle buffalo capicola, shank venison turkey alcatra spare ribs sirloin turducken beef chislic. Jowl spare ribs porchetta chislic chicken kielbasa bresaola filet mignon ground round fatback. Boudin shoulder pork chop chicken tri-tip kevin cow, hamburger ham frankfurter pork belly. Porchetta tri-tip venison meatloaf ham tail pancetta filet mignon kielbasa jowl chislic turkey. Shankle leberkas porchetta ground round jerky beef ribs landjaeger capicola meatloaf chicken sirloin pork loin frankfurter pork chop bacon."));
        Thread.sleep(2000);

        System.out.println("VERIFY SENT EMAIL");

        //logout
        webdriver.findElement(By.id("rcmbtn103")).click();

        System.out.println("LOGOUT");

        Thread.sleep(2000);
        //verify logout
        assert (webdriver.findElement(By.id("login-form")).isDisplayed());

        System.out.println("VERIFY LOGOUT");
    }
}