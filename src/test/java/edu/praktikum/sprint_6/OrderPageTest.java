package edu.praktikum.sprint_6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderPageTest extends BaseTest {

    static Stream<Arguments> orderDataProvider() {
        return Stream.of(
                Arguments.of("Лиззи",
                        "Дарси",
                        "Дербишир, Пемберли",
                        "+7320000000",
                        "Для миссис Дарси"),
                Arguments.of("Джейн",
                        "Бингли",
                        "Ноттингем, Незерфилд",
                        "+74050000500",
                        "Для миссис Бингли")
        );
    }
    @ParameterizedTest(name = "{index}: {4}")
    @MethodSource("orderDataProvider")
    public void testOrderFormFilling(String name, String surname, String address, String phone, String testName) {
        OrderPage orderPage = new OrderPage(driver);
        orderPage.open();

        assertTrue(orderPage.isFormDisplayed(), "Форма заказа не отображается");
        orderPage.fillOrderForm(name, surname, address, phone);
        assertTrue(orderPage.isFormFilled(name, surname, address, phone),
                "Форма не заполнена корректно " + testName);
    }
    @Test
    public void testOrderFlowFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.clickFirstOrderButton();
        OrderPage orderPage = new OrderPage(driver);
        assertTrue(orderPage.isFormDisplayed(),
                "После клика по кнопке 'Заказать' не открылась форма заказа");
    }


    @Test
    public void testOrderFlowFromBottomButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        mainPage.clickSecondOrderButton();

        OrderPage orderPage = new OrderPage(driver);
        assertTrue(orderPage.isFormDisplayed(),
                "После клика по второй кнопке 'Заказать' не открылась форма заказа");
    }
}