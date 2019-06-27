package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MealTestData {

    public static final int TRUE_MEAL_ID = 2;
    public static final int FALSE_MEAL_ID = START_SEQ + 4;
    public static final int TRUE_USER_ID = START_SEQ;
    public static final int FALSE_USER_ID = 6;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));

    public static final List<Meal> MEALS_FILTERED_BY_DATE = Arrays.asList(
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));


    public static final List<Meal> MEALS_AFTER_DELETE = Arrays.asList(
            new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));

    public static final Meal TRUE_MEAL = new Meal(TRUE_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);

    public static final Meal FALSE_MEAL = new Meal(FALSE_MEAL_ID, LocalDateTime.of(2015, Month.MAY,
            14, 10, 0), "Завтрак", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }


//    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
//        assertMatch(actual, Arrays.asList(expected));
//    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("description").isEqualTo(expected);
    }

}
