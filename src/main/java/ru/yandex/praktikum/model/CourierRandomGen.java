package ru.yandex.praktikum.model;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierRandomGen {
    public static DataCourier getRandom() {
        String login = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(4);
        String firstName = RandomStringUtils.randomAlphabetic(9);
        return new DataCourier(login, password, firstName);
    }
}
