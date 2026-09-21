package edu.praktikum.sprint_6;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class FaqTest extends GeneralTest {

    @ParameterizedTest
    @CsvSource({
            "0, 'Сутки — 400 рублей. Оплата курьеру — наличными или картой.'",
            "1, 'Пока что у нас так: один заказ — один самокат.'",
            "2, 'Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру.'",
            "3, 'Только начиная с завтрашнего дня. Но скоро станем расторопнее.'",
            "4, 'Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.'",
            "5, 'Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток'",
            "6, 'Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим.'",
            "7, 'Да, обязательно. Всем самокатов! И Москве, и Московской области.'"
    })
    public void testAllQuestions(int index, String expectedText) {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        mainPage.clickQuestion(index);

        assertTrue(mainPage.isAnswerVisible(index), "Ответ на вопрос " + index + " не отображается");

        String answerText = mainPage.getAnswerText(index);
        assertTrue(answerText.contains(expectedText), "Текст ответа не содержит ожидаемую фразу. Получено: " + answerText);
    }
}