package edu.praktikum.sprint_6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTest extends GeneralTest {

    @Test
    public void testMainPageLoads() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        assertTrue(mainPage.isPageLoaded(), "Главная страница не загрузилась корректно");
    }

    @Test
    public void testClickOrderButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        mainPage.clickOrderButton();

        OrderPage orderPage = new OrderPage(driver);
        assertTrue(orderPage.isFormDisplayed(), "После клика форма заказа не отобразилась");
    }
}