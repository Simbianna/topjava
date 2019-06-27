package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MealTestData {

    public static final int TRUE_MEAL_ID = 100002;
    public static final int FAKE_MEAL_ID = 4;
    public static final int FALSE_MEAL_ID = 100003;
    public static final int NEW_MEAL_ID = 100010;


    public static final int TRUE_USER_ID = START_SEQ;
    public static final int FALSE_USER_ID = 6;

    public static final Meal TRUE_MEAL = new Meal(TRUE_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Заврак", 500);

    public static final Meal FALSE_MEAL = new Meal(FALSE_MEAL_ID, LocalDateTime.of(2000, Month.MAY,
            14, 10, 0), "Завтрак", 500);

    public static final Meal NEW_MEAL = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "UpdatedDesc", 330);

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(100008, LocalDateTime.of(2015, Month.MAY, 31, 18, 0), "Ужин", 510),
            new Meal(100006, LocalDateTime.of(2015, Month.MAY, 31, 12, 0), "Завтрак", 1000),
            new Meal(100007, LocalDateTime.of(2015, Month.MAY, 31, 07, 0), "Обед", 500),
            new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Обед", 500),
            new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30, 15, 0), "Обед", 1000),
            new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Заврак", 500),
            new Meal(100005, LocalDateTime.of(2015, Month.MAY, 30, 02, 0), "Ужин", 500));

    public static final List<Meal> MEALS_FILTERED_BY_DATE = Arrays.asList(

            new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Обед", 500),
            new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30, 15, 0), "Обед", 1000),
            new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Заврак", 500),
            new Meal(100005, LocalDateTime.of(2015, Month.MAY, 30, 02, 0), "Ужин", 500));


    public static final List<Meal> MEALS_AFTER_DELETE = Arrays.asList(
            new Meal(100008, LocalDateTime.of(2015, Month.MAY, 31, 18, 0), "Ужин", 510),
            new Meal(100006, LocalDateTime.of(2015, Month.MAY, 31, 12, 0), "Завтрак", 1000),
            new Meal(100007, LocalDateTime.of(2015, Month.MAY, 31, 07, 0), "Обед", 500),
            new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Обед", 500),
            new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30, 15, 0), "Обед", 1000),
            new Meal(100005, LocalDateTime.of(2015, Month.MAY, 30, 02, 0), "Ужин", 500));

    public static final List<Meal> MEALS_WITH_NEW_MEAL = Arrays.asList(
            new Meal(100008, LocalDateTime.of(2015, Month.MAY, 31, 18, 0), "Ужин", 510),
            new Meal(100006, LocalDateTime.of(2015, Month.MAY, 31, 12, 0), "Завтрак", 1000),
            new Meal(100007, LocalDateTime.of(2015, Month.MAY, 31, 07, 0), "Обед", 500),
            new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Обед", 500),
            new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30, 15, 0), "Обед", 1000),
            new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Заврак", 500),
            new Meal(100005, LocalDateTime.of(2015, Month.MAY, 30, 02, 0), "Ужин", 500),
            new Meal(100009, LocalDateTime.of(2001, Month.JUNE, 10, 10, 0), "New", 200)
            );


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }


    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("").isEqualTo(expected);
    }

}
