package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class VerifyPage {

    private SelenideElement codeInput = $x("//span[@data-test-id='code']//input");
    private SelenideElement codeInputEmptyNotification = $x("//span[@data-test-id='code']//span[@class='input_sub']");
    private SelenideElement verifyButton = $x("//button[@data-test-id='action-verify']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");
    private SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");

    public void VerifyCode() {
        codeInput.should(visible);
        verifyButton.should(visible);
        errorNotification.should(hidden);
        errorButton.should(hidden);
    }

    public void input(String code) {
        codeInput.val(code);
        verifyButton.click();
    }

    public DashboardPage success() {
        errorNotification.should(hidden);
        errorButton.should(hidden);
        return new DashboardPage();
    }

    public void failed() {
        errorNotification.should(visible);
        errorNotification.$x(".//div[@class='notification__content']")
                .should(text("Ошибка! Неверно указан код! Попробуйте еще раз."));
        errorButton.click();
        errorNotification.should(hidden);
    }

    public void emptyCode() {
        codeInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }
}
