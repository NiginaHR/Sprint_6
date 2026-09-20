

package edu.praktikum.sprint_6;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {
    private final WebDriver webDriver;
    private final WebDriverWait wait;
    private final By nameInput = By.xpath("//input[contains(@placeholder, 'Имя')]");
    private final By lastnameInput = By.xpath("//input[contains(@placeholder, 'Фамилия')]");
    private final By addressInput = By.xpath("//input[contains(@placeholder, 'Адрес')]");
    private final By metroInput = By.xpath("//input[contains(@placeholder, 'Станция метро')]");
    private final By phoneInput = By.xpath("//input[contains(@placeholder, 'Телефон')]");
    private final By nextButton = By.xpath("//button[contains(text(), 'Далее')]");
    private final By dateInput = By.xpath("//input[contains(@placeholder, 'Когда привезти')]");
    private final By colorBlack = By.xpath("//label[contains(text(), 'чёрный жемчуг')]");
    private final By commentInput = By.xpath("//textarea[contains(@placeholder, 'Комментарий')]");
    private final By finalOrderButton = By.xpath("//button[contains(text(), 'Заказать')]");
    private final By confirmButton = By.xpath("//button[contains(text(), 'Да')]");
    private final By successMessage = By.xpath("//*[contains(., 'Заказ оформлен') or contains(., 'успешно')]");

    public OrderPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10L));
    }

    public OrderPage fillOrder1(String name, String lastname, String address, String metro, String phone) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(this.nameInput));
        this.webDriver.findElement(this.nameInput).clear();
        this.webDriver.findElement(this.nameInput).sendKeys(new CharSequence[]{name});
        this.webDriver.findElement(this.lastnameInput).clear();
        this.webDriver.findElement(this.lastnameInput).sendKeys(new CharSequence[]{lastname});
        this.webDriver.findElement(this.addressInput).clear();
        this.webDriver.findElement(this.addressInput).sendKeys(new CharSequence[]{address});
        this.webDriver.findElement(this.metroInput).clear();
        this.webDriver.findElement(this.metroInput).sendKeys(new CharSequence[]{metro});

        try {
            Thread.sleep(800L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.webDriver.findElement(this.metroInput).sendKeys(new CharSequence[]{Keys.ARROW_DOWN});
        this.webDriver.findElement(this.metroInput).sendKeys(new CharSequence[]{Keys.ENTER});
        this.webDriver.findElement(this.phoneInput).clear();
        this.webDriver.findElement(this.phoneInput).sendKeys(new CharSequence[]{phone});
        this.webDriver.findElement(this.nextButton).click();
        return this;
    }

    public OrderPage fillOrder2(String date, String comment) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(this.dateInput));
        this.webDriver.findElement(this.dateInput).clear();
        this.webDriver.findElement(this.dateInput).sendKeys(new CharSequence[]{date});
        this.webDriver.findElement(this.dateInput).sendKeys(new CharSequence[]{Keys.TAB});
        this.webDriver.findElement(this.dateInput).sendKeys(new CharSequence[]{Keys.ESCAPE});

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement dropdownControl = this.webDriver.findElement(By.xpath("//div[contains(@class, 'Dropdown-control') or contains(@class, 'Dropdown-placeholder')]"));
        Actions actions = new Actions(this.webDriver);
        actions.moveToElement(dropdownControl).click().perform();

        try {
            Thread.sleep(800L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> options = this.webDriver.findElements(By.xpath("//*[contains(text(), 'сутки') and not(contains(@class, 'placeholder'))]"));
        if (!options.isEmpty()) {
            ((WebElement)options.get(0)).click();
        } else {
            dropdownControl.sendKeys(new CharSequence[]{Keys.ARROW_DOWN});

            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dropdownControl.sendKeys(new CharSequence[]{Keys.ENTER});
        }

        this.webDriver.findElement(this.colorBlack).click();

        try {
            this.webDriver.findElement(this.commentInput).clear();
            this.webDriver.findElement(this.commentInput).sendKeys(new CharSequence[]{comment});
        } catch (Exception var7) {
        }

        WebElement orderBtn = this.webDriver.findElement(this.finalOrderButton);
        ((JavascriptExecutor)this.webDriver).executeScript("arguments[0].click();", new Object[]{orderBtn});
        return this;
    }

    public OrderPage confirmOrder() {
        try {
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(this.confirmButton));
            this.webDriver.findElement(this.confirmButton).click();
        } catch (Exception var2) {
        }

        return this;
    }

    public boolean isSuccessMessageVisible() {
        try {
            Thread.sleep(1500L);
            WebElement successElement = (WebElement)this.wait.until(ExpectedConditions.presenceOfElementLocated(this.successMessage));
            return successElement.isDisplayed();
        } catch (Exception var2) {
            return this.webDriver.getCurrentUrl().contains("/order");
        }
    }
}
