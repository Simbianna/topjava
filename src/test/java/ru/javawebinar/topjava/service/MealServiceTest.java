package ru.javawebinar.topjava.service;

import org.junit.ComparisonFailure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(TRUE_MEAL_ID, TRUE_USER_ID);
        assertMatch(meal, TRUE_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getFailedWrongUserId() throws Exception {
        Meal meal = service.get(TRUE_MEAL_ID, FALSE_USER_ID);
        assertMatch(meal, TRUE_MEAL);
    }


    @Test(expected = ComparisonFailure.class)
    public void getFailedWrongMealId() throws Exception {
        Meal meal = service.get(TRUE_MEAL_ID, TRUE_USER_ID);
        assertMatch(meal, FALSE_MEAL);
    }

    @Test
    public void delete() {
        service.delete(TRUE_MEAL_ID, TRUE_USER_ID);
        assertMatch(service.getAll(TRUE_USER_ID),MEALS_AFTER_DELETE);
    }

    @Test(expected = ru.javawebinar.topjava.util.exception.NotFoundException.class)
    public void deleteWrongUserId() {
        service.delete(TRUE_MEAL_ID, FALSE_USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongMealId() {
        service.delete(FALSE_MEAL_ID, TRUE_USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> filtredMeal = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), TRUE_USER_ID);
        assertMatch(filtredMeal, MEALS_FILTERED_BY_DATE);
    }

    @Test(expected = AssertionError.class)
    public void getBetweenDatesFailed() {
        List<Meal> filteredMeal = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), TRUE_USER_ID);
        assertMatch(filteredMeal, MEALS);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> filteredMeal = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30,0,0),
                LocalDateTime.of(2015, Month.MAY, 30, 23, 59), TRUE_USER_ID);
        assertMatch(filteredMeal, MEALS_FILTERED_BY_DATE);
    }

    @Test(expected = AssertionError.class)
    public void getBetweenDateTimesFailed() {
        List<Meal> filteredMeal = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30,0,0),
                LocalDateTime.of(2015, Month.MAY, 30, 23, 59), TRUE_USER_ID);
        assertMatch(filteredMeal, MEALS);
    }

    @Test
    public void getAll() {
        List<Meal> list = service.getAll(TRUE_USER_ID);
        assertMatch(list, MEALS);
    }

    @Test
    public void update() throws Exception{
        Meal updated = new Meal(TRUE_MEAL);
        updated.setDescription("UpdatedDesc");
        updated.setCalories(330);
        service.update(updated,TRUE_USER_ID);
        assertMatch(service.get(TRUE_MEAL_ID, TRUE_USER_ID), updated);
    }

    @Test
    public void create() {
    }
}