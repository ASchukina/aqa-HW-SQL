package ru.netology.web.test.test;

import org.junit.jupiter.api.*;
import ru.netology.web.test.data.DataHelper;
import ru.netology.web.test.data.SQLHelper;
import ru.netology.web.test.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.test.data.SQLHelper.cleanAuthCodes;
import static ru.netology.web.test.data.SQLHelper.cleanDatabase;

public class AuthTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999/", LoginPage.class);
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void shouldBeSuccessfulAuthorisation() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Пустые поля логина и пароля")
    public void shouldNotAuthoriseWithoutLoginAbdPassword() {
        var authInfo = DataHelper.getAuthInfo();
        loginPage.emptyAuthorisation(authInfo);
    }

    @Test
    @DisplayName("Пользователя нет в базе")
    public void shouldNotAuthoriseWithRandomUserNotAddingToBase() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.unSuccessfulLogin();
    }

    @Test
    @DisplayName("Неверный код")
    public void shouldNotAuthoriseWithIncorrectCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.incorrectCode();
    }

    @Test
    @DisplayName("Пустой код")
    public void shouldNotAuthoriseWithoutCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify("");
        verificationPage.codeEmpty();
    }
}
