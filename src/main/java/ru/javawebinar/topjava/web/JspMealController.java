package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String meals(Model model) {
        int userId = SecurityUtil.authUserId();
        model.addAttribute("meals",
                MealsUtil.getWithExcess(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }


    @RequestMapping(params = {"action"}, method = RequestMethod.GET)
    private String create(@RequestParam(defaultValue = "create") String action,
                          Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "new", 1000);
        mealService.create(meal, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(params = {"action", "id"}, method = RequestMethod.GET)
    private String getOrDelete(@RequestParam(defaultValue = "update") String action,
                       @RequestParam("id") int id,
                       Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = mealService.get(id, userId);
        if (action.equals("delete")) {
            mealService.delete(id, userId);
            model.addAttribute("meal", meal);
            return "redirect:meals";
        }
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(params = {"action", "startDate", "endDate", "startTime", "endTime"}, method = RequestMethod.GET)
    private String mealsFiltred(@RequestParam(defaultValue = "filter") String action,
                                @RequestParam("startDate") String startDateParam,
                                @RequestParam("endDate") String endDateParam,
                                @RequestParam("startTime") String startTimeParam,
                                @RequestParam("endTime") String endTimeParam,
                                Model model) {

        LocalDate startDate = parseLocalDate(startDateParam);
        LocalDate endDate = parseLocalDate(endDateParam);
        LocalTime startTime = parseLocalTime(startTimeParam);
        LocalTime endTime = parseLocalTime(endTimeParam);
        int userId = SecurityUtil.authUserId();
        List<Meal> mealsDateFiltered = mealService.getBetweenDates(startDate, endDate, userId);
        model.addAttribute("meals",
                MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }


    @RequestMapping(params = {"id", "dateTime", "description", "calories"}, method = RequestMethod.POST)
    private String update(@RequestParam("id") int id,
                          @RequestParam("dateTime") String dateTime,
                          @RequestParam("description") String description,
                          @RequestParam("calories") int calories,
                          Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = mealService.get(id, userId);
        meal.setDateTime(LocalDateTime.parse(dateTime));
        meal.setDescription(description);
        meal.setCalories(calories);
        mealService.update(meal, userId);
        model.addAttribute("meal", meal);
        return "redirect:meals";
    }

}
