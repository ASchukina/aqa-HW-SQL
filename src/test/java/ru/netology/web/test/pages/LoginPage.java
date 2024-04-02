package ru.netology.web.test.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.test.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement heading = $(byText("Интернет Банк"));
    private SelenideElement loginInput = $("[data-test-id='login'] input");
    private SelenideElement passwordInput = $("[data-test-id='password'] input");
    private SelenideElement actionButton = $("[data-test-id='action-login']");
    private SelenideElement loginNotification = $("[data-test-id='login'] .input__sub");
    private SelenideElement passwordNotification = $("[data-test-id='password'] .input__sub");
    private SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    public LoginPage() {
        heading.shouldBe(Condition.visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginInput.setValue(info.getLogin());
        passwordInput.setValue(info.getPassword());
        actionButton.click();
        return new VerificationPage();
    }

    public void emptyAuthorisation(DataHelper.AuthInfo info) {
//        loginInput.setValue("");
//        passwordInput.setValue("");
        actionButton.click();
        loginNotification.shouldBe(Condition.visible);
        passwordNotification.shouldBe(Condition.visible);
        loginNotification.shouldHave(Condition.text("Поле обязательно для заполнения"));
        passwordNotification.shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    public void unSuccessfulLogin() {
        errorNotification.shouldBe(Condition.visible);
        errorNotification.shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }
}
