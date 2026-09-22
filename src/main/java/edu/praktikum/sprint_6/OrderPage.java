package edu.praktikum.sprint_6;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = ".//input[contains(@placeholder, 'Имя')]")
    private WebElement nameInput;

    @FindBy(xpath = ".//input[contains(@placeholder, 'Фамилия')]")
    private WebElement surnameInput;

    @FindBy(xpath = ".//input[contains(@placeholder, 'Адрес')]")
    private WebElement addressInput;

    @FindBy(xpath = ".//input[contains(@placeholder, 'Телефон')]")
    private WebElement phoneInput;

    @FindBy(xpath = ".//button[contains(text(), 'Далее')]")
    private WebElement nextButton;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT_SECONDS));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(Constants.URL + "order");
    }

    public void fillOrderForm(String name, String surname, String address, String phone) {
        nameInput.clear();
        nameInput.sendKeys(name);

        surnameInput.clear();
        surnameInput.sendKeys(surname);

        addressInput.clear();
        addressInput.sendKeys(address);

        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public boolean isFormDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        return nameInput.isDisplayed() && nextButton.isDisplayed();
    }

    public boolean isFormFilled(String name, String surname, String address, String phone) {
        return nameInput.getAttribute("value").contains(name) &&
                surnameInput.getAttribute("value").contains(surname) &&
                addressInput.getAttribute("value").contains(address) &&
                phoneInput.getAttribute("value").contains(phone);
    }
}