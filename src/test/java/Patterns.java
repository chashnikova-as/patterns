import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * АТ формы заказа доставки карты
 */
public class Patterns {

    public static ClaimData claimData = new ClaimData();

    @BeforeEach
    void openUrl() {
        open("http://localhost:9999");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ClaimSuccess.csv", delimiter = '|', numLinesToSkip = 1)
    void sendClaimSuccess(String city, String daysToMeet, String name, String phone) {
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(claimData.generateMeetingDate( Integer.parseInt(daysToMeet)));
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Забронировать")).click();
        form.$(withText("Успешно!")).waitUntil(visible, 15000);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ClaimWithoutAgreement.csv", delimiter = '|', numLinesToSkip = 1)
    void sendClaimWithoutAgreement(String city, String daysToMeet, String name, String phone, String expectedTextColor) {
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(claimData.generateMeetingDate(Integer.parseInt(daysToMeet)));
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$(withText("Забронировать")).click();
        String checkboxTextColor = form.$(".checkbox__text").getCssValue("color");
        assertNotEquals(expectedTextColor, checkboxTextColor, "Цвет текста флага должен был измениться!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/IncorrectCity.csv", delimiter = '|', numLinesToSkip = 1)
    void sendClaimIncorrectCity(String city, String daysToMeet, String name, String phone, String expectedErrorMessage) {
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(claimData.generateMeetingDate(Integer.parseInt(daysToMeet)));
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Забронировать")).click();
        String actualErrorMessage = form.$("[data-test-id='city'] .input__sub").getText();
        assertEquals(expectedErrorMessage, actualErrorMessage, "Текст ошибки при некорректном имени города неверен!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/IncorrectDate.csv", delimiter = '|', numLinesToSkip = 1)
    void sendClaimIncorrectDate(String city, String date, String name, String phone, String expectedErrorMessage) {
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(date);
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Забронировать")).click();
        String actualErrorMessage = form.$("[data-test-id='date'] .input__sub").getText();
        assertEquals(expectedErrorMessage, actualErrorMessage, "Текст ошибки при некорректной дате встречи неверен!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/EarlyDate.csv", delimiter = '|', numLinesToSkip = 1)
    void sendClaimEarlyDate(String city, String daysToMeet, String name, String phone, String expectedErrorMessage) {
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(claimData.generateMeetingDate(Integer.parseInt(daysToMeet)));
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Забронировать")).click();
        String actualErrorMessage = form.$("[data-test-id='date'] .input__sub").getText();
        assertEquals(expectedErrorMessage, actualErrorMessage, "Текст ошибки при некорректной дате встречи неверен!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/IncorrectName.csv", delimiter = '|', numLinesToSkip = 1)
    void sendClaimIncorrectName(String city, String daysToMeet, String name, String phone, String expectedErrorMessage) {
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(claimData.generateMeetingDate(Integer.parseInt(daysToMeet)));
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Забронировать")).click();
        String actualErrorMessage = form.$("[data-test-id='name'] .input__sub").getText();
        assertEquals(expectedErrorMessage, actualErrorMessage, "Текст ошибки при некорректном имени неверен!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/IncorrectPhoneNumber.csv", delimiter = '|', numLinesToSkip = 1)
    void sendClaimIncorrectPhoneNumber(String city, String daysToMeet, String name, String phone, String expectedErrorMessage) {
        SelenideElement form = $("#root");
        form.$("[data-test-id='city'] input").setValue(city);
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(claimData.generateMeetingDate(Integer.parseInt(daysToMeet)));
        form.$("[data-test-id='name'] input").setValue(name);
        form.$("[data-test-id='phone'] input").setValue(phone);
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Забронировать")).click();
        String actualErrorMessage = form.$("[data-test-id='phone'] .input__sub").getText();
        assertEquals(expectedErrorMessage, actualErrorMessage, "Текст ошибки при некорректном имени неверен!");
    }

    @Test
    void sendEmptyForm() {
        $$("button").find(exactText("Забронировать")).click();
        $("#root .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void changeMeetingDateWithoutReloadPageSuccess() {
        SelenideElement form = $("#root");
        ClaimData client = claimData.generateAllClaimData();
        form.$("[data-test-id='city'] input").setValue(client.getCity());
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(client.getDateMeeting());
        form.$("[data-test-id='name'] input").setValue(client.getName());
        form.$("[data-test-id='phone'] input").setValue(client.getPhone());
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Запланировать")).click();
        form.$(withText("Успешно!")).waitUntil(visible, 15000);
        refresh(); //обновляем страницу
        String newMeetingDate = claimData.generateMeetingDate();
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(newMeetingDate);
        form.$(withText("Запланировать")).click();
        form.$("[data-test-id='replan-notification']").shouldBe(visible);
        form.$("[data-test-id='replan-notification'] button").click();
        form.$("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на \n"+newMeetingDate));
    }

    @Test
    void changeMeetingDateReloadPageSuccess() {
        SelenideElement form = $("#root");
        ClaimData client = claimData.generateAllClaimData();
        form.$("[data-test-id='city'] input").setValue(client.getCity());
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(client.getDateMeeting());
        form.$("[data-test-id='name'] input").setValue(client.getName());
        form.$("[data-test-id='phone'] input").setValue(client.getPhone());
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Запланировать")).click();
        form.$(withText("Успешно!")).waitUntil(visible, 15000);
        refresh(); //обновляем страницу
        String newMeetingDate = claimData.generateMeetingDate();
        form.$("[data-test-id='city'] input").setValue(client.getCity());
        form.$("[data-test-id='date'] input").doubleClick().sendKeys("\b");
        form.$("[data-test-id='date'] input").setValue(newMeetingDate);
        form.$("[data-test-id='name'] input").setValue(client.getName());
        form.$("[data-test-id='phone'] input").setValue(client.getPhone());
        form.$("[data-test-id='agreement']").click();
        form.$(withText("Запланировать")).click();
        form.$("[data-test-id='replan-notification']").shouldBe(visible);
        form.$("[data-test-id='replan-notification'] button").click();
        form.$("[data-test-id='success-notification'] .notification__content").shouldHave(exactText("Встреча успешно запланирована на \n"+newMeetingDate));
    }
}
