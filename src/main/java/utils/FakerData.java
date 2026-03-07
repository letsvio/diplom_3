package utils;

import net.datafaker.Faker;

public class FakerData {

    private static final Faker faker = new Faker();

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String password() {
        return faker.internet().password(8, 16);
    }

    public static String name() {
        return faker.name().firstName();
    }
}
