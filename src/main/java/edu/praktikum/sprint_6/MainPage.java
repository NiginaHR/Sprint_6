
package edu.praktikum.sprint_6;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By faqQuestion = By.xpath("//div[contains(text(), 'Сколько это стоит? И как оплатить?')]");
    private final By faqAnswer = By.xpath("//div[contains(text(), 'Сколько это стоит? И как оплатить?')]/parent::div/following-sibling::div");
    private final By orderButtons = By.xpath("//button[contains(text(), 'Заказать')]");
    private final By samokatLogo = By.xpath("//a[@href='/'][.//img]");
    private final By yandexLogo = By.xpath("//a[@target='_blank'][.//img]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10L));
    }

    public MainPage open() {
        return this;
    }

    public MainPage clickFaqQuestion() {
        WebElement questionElement = this.driver.findElement(this.faqQuestion);
        ((JavascriptExecutor)this.driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", new Object[]{questionElement});

        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            questionElement.click();
        } catch (Exception var3) {
            ((JavascriptExecutor)this.driver).executeScript("arguments[0].click();", new Object[]{questionElement});
        }

        return this;
    }

    public String getFaqAnswerText() {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(this.faqAnswer));
        return this.driver.findElement(this.faqAnswer).getText();
    }

    public OrderPage clickOrderButton(boolean isTopButton) {
        List<WebElement> buttons = this.driver.findElements(this.orderButtons);
        WebElement targetButton = isTopButton ? (WebElement)buttons.get(0) : (WebElement)buttons.get(1);
        ((JavascriptExecutor)this.driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", new Object[]{targetButton});

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Actions actions = new Actions(this.driver);
            actions.moveToElement(targetButton).click().perform();
        } catch (Exception var5) {
            ((JavascriptExecutor)this.driver).executeScript("arguments[0].click();", new Object[]{targetButton});
        }

        return new OrderPage(this.driver);
    }

    public void clickSamokatLogo() {
        this.driver.findElement(this.samokatLogo).click();
    }

    public void clickYandexLogo() {
        this.driver.findElement(this.yandexLogo).click();
    }

    public void switchToNewWindowAndBack() {
        String originalWindow = this.driver.getWindowHandle();
        this.wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for(String windowHandle : this.driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                this.driver.switchTo().window(windowHandle);
                break;
            }
        }

        this.wait.until(ExpectedConditions.urlContains("ya.ru"));
        this.driver.close();
        this.driver.switchTo().window(originalWindow);
    }
}
