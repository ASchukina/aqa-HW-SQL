package ru.netology.web.test.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement header = $("[data-test-id='dashboard']");

    public DashboardPage() {
        header.shouldHave(Condition.text("Личный кабиент")).shouldBe(Condition.visible);
    }
}
