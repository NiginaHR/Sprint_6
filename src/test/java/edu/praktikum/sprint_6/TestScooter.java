package edu.praktikum.sprint_6;

import java.time.Duration;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestScooter {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(new String[]{"--no-sandbox"});
        options.addArguments(new String[]{"--disable-dev-shm-usage"});
        options.addArguments(new String[]{"--headless"});
        options.addArguments(new String[]{"--window-size=1920,1080"});
        this.driver = new ChromeDriver(options);
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5L));
        this.driver.get("https://qa-scooter.education-services.ru/");
        this.mainPage = new MainPage(this.driver);
    }

    @Test
    public void faqTest() {
        this.mainPage.open().clickFaqQuestion();
        String answerText = this.mainPage.getFaqAnswerText();
        Assertions.assertTrue(answerText.contains("400"), "Текст ответа не содержит '400'");
    }

    @ParameterizedTest
    @MethodSource({"orderDataProvider"})
    public void testOrderFlow(String name, String lastname, String address, String metro, String phone, String date, String comment, boolean isTopButton, String testDescription) {
        OrderPage orderPage = this.mainPage.clickOrderButton(isTopButton);
        orderPage.fillOrder1(name, lastname, address, metro, phone).fillOrder2(date, comment).confirmOrder();
        Assertions.assertTrue(orderPage.isSuccessMessageVisible(), "Сообщение об успешном заказе не появилось. Тест: " + testDescription);
    }

    @Test
    public void testSamokatLogoRedirect() {
        this.mainPage.clickSamokatLogo();
        Assertions.assertTrue(this.driver.getCurrentUrl().contains("qa-scooter"), "Переход по логотипу Самоката не сработал");
    }

    @Test
    public void testYandexLogoNewWindow() {
        this.mainPage.clickYandexLogo();
        this.mainPage.switchToNewWindowAndBack();
        Assertions.assertTrue(this.driver.getCurrentUrl().contains("qa-scooter"), "Не удалось вернуться на исходную страницу");
    }

    static Stream<Arguments> orderDataProvider() {
        return Stream.of(Arguments.of(new Object[]{"Лиззи", "Дарси", "Дербишир, Пемберли", "Чистые пруды", "+79003200000", "18.09.2026", "Для миссис Дарси", true, "Заказ через верхнюю кнопку"}), Arguments.of(new Object[]{"Джейн", "Бингли", "Ноттингем, Незерфилд", "Невский проспект", "+79003200000", "20.09.2026", "Для миссис Бингли", false, "Заказ через нижнюю кнопку"}));
    }

    @AfterEach
    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }

    }
}
