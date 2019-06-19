package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Integer> mealRefersToUser  = new ConcurrentHashMap<>();

    {
        for (Meal MEAL : MealsUtil.MEALS) {
            save(MEAL, SecurityUtil.authUserId());
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            mealRefersToUser.put(meal.getId(), userId);
            return meal;
        }
        // treat case: update, but absent in storage
        mealRefersToUser.putIfAbsent(meal.getId(), userId);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return checkMealRefersToUser(mealId, userId)
                && repository.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Meal meal = repository.get(mealId);
        return meal != null && checkMealRefersToUser(mealId, userId) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }

    @Override
    public Collection<Meal> getAllForUser(int userId) {
        return getAll().parallelStream()
                .filter(m -> mealRefersToUser.get(m.getId()) == userId)
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkMealRefersToUser(int mealId, int userId){
        return mealRefersToUser.get(mealId) == userId;
    }
}

