package edu.praktikum.sprint_6;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".accordion__heading")
    private List<WebElement> questions;
    @FindBy(css = ".accordion__panel")
    private List<WebElement> answers;
    @FindBy(xpath = ".//button[contains(text(), 'Заказать')]")
    private WebElement orderButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT_SECONDS));
        PageFactory.initElements(driver, this);
    }
    public void open() {
        driver.get(Constants.URL);
    }
    public boolean isPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".accordion")));
        return true;
    }
    public void clickQuestion(int index) {
        WebElement question = questions.get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        wait.until(ExpectedConditions.elementToBeClickable(question)).click();
    }
    public String getAnswerText(int index) {
        wait.until(ExpectedConditions.visibilityOf(answers.get(index)));
        return answers.get(index).getText();
    }
    public boolean isAnswerVisible(int index) {
        return answers.get(index).isDisplayed();
    }
    public void clickOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }
}