package com.dinosaurs.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

import java.util.List;

@DefaultUrl("https://www.scs.ubbcluj.ro/webmail/")
public class DictionaryPage extends PageObject {

    @FindBy(id="rcmloginuser")
    private WebElementFacade username;

    @FindBy(id="rcmloginpwd")
    private WebElementFacade password;

    @FindBy(id="rcmloginsubmit")
    private WebElementFacade loginbutton;

    public void access_email() {
        find(By.id("rcmliU2VudA")).click();

    }

    public void click_email(){
        find(By.id("rcmrow66")).click();
        find(By.id("rcmrow66")).click();
    }

    public boolean assertEmailBodyCorrect() {
        return find(By.id("messagebody")).getText().equals("Bacon ipsum dolor amet pork belly shankle buffalo capicola, shank venison turkey alcatra spare ribs sirloin turducken beef chislic. Jowl spare ribs porchetta chislic chicken kielbasa bresaola filet mignon ground round fatback. Boudin shoulder pork chop chicken tri-tip kevin cow, hamburger ham frankfurter pork belly. Porchetta tri-tip venison meatloaf ham tail pancetta filet mignon kielbasa jowl chislic turkey. Shankle leberkas porchetta ground round jerky beef ribs landjaeger capicola meatloaf chicken sirloin pork loin frankfurter pork chop bacon.");
    }

    public void click_logout(){
        find(By.id("rcmbtn103")).click();
    }
    public boolean assert_logout_success() {
        return find(By.id("login-form")).isDisplayed();
    }

    public void enter_keywords(String str_username, String str_password) {
        username.type(str_username);
        password.type(str_password);
    }

    public void click_login() {
        loginbutton.click();
    }

    public boolean getLoginDone() {
        try {
            return find(By.id("rcmbtn103")).isDisplayed();
        } catch (NoSuchElementException er) {
            return false;
        }
    }
}