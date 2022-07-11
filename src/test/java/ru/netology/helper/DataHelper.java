package ru.netology.helper;

import com.github.javafaker.Faker;
import ru.netology.data.UserData;

public class DataHelper {

    private static Faker faker = new Faker();

    public static UserData getUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static String getRandomPassword() {
        String randomPassword = faker.internet().password();
        return randomPassword;
    }

    public static String getValidVerifyCode(String login) {
        String verifyCode = SQLHelper.getValidVerifyCode(login);
        return verifyCode;
    }

    public static String getInvalidVerifyCode(){
        int invalidVerifyCode = faker.number().numberBetween(100_000, 999_999);
        return String.valueOf(invalidVerifyCode);
    }
}
