package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.UserData;
import ru.netology.helper.DataHelper;
import ru.netology.helper.SQLHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerifyPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {

    UserData user;
    LoginPage loginPage;

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
        user = DataHelper.getUser();
        loginPage = new LoginPage();
    }

    @AfterEach
    public void tearDown() {
        SQLHelper.resetUserStatus(user.getLogin());
        SQLHelper.resetVerifyCode();
    }

    @Test
    public void shouldHappyPath() {
        loginPage.input(user.getLogin(), user.getPassword());
        VerifyPage verifyPage = loginPage.success();
        verifyPage.input(DataHelper.getValidVerifyCode(user.getLogin()));
        DashboardPage dashboardPage = verifyPage.success();
    }

    @Test
    public void shouldClearFieldPassword() {
        loginPage.input(user.getLogin(), null);
        loginPage.emptyPassword();
    }

    @Test
    public void shouldBlockUserAfterThreeAuth() {
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        loginPage.input(user.getLogin(), DataHelper.getRandomPassword());
        loginPage.failed();
        assertEquals("blocked", SQLHelper.getUserStatus(user.getLogin()));
    }
}
