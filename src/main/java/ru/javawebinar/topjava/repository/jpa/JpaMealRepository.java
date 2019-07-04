package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    public JpaMealRepository() {
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.getUser() == null) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
        } else if (meal.getUser().getId() != userId) {
            return null;
        }

        if (meal.isNew()) {
            em.persist(meal);
            em.flush();
            return meal;
        } else {
//            if (em.find(Meal.class, meal.getId()) == null) return null;
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

//        return em.createQuery("DELETE FROM Meal m WHERE m.id=:id")
//                .setParameter("id", id).executeUpdate() != 0;
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId");
        return query.setParameter("id", id).setParameter("userId", userId).executeUpdate() != 0;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        TypedQuery<Meal> tq = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId", Meal.class);
        List<Meal> meals = tq.setParameter("id", id).setParameter("userId", userId)
                .getResultList();
        Meal result = DataAccessUtils.singleResult(meals);
        return result;
//        return em.find(Meal.class, id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        TypedQuery<Meal> tq = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC", Meal.class);
        return tq.setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        TypedQuery<Meal> tq
                = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId " +
                "AND m.dateTime >= :startDate AND m.dateTime <= :endDate ORDER BY m.dateTime DESC", Meal.class);
        return tq.setParameter("userId", userId).setParameter("endDate", endDate)
                .setParameter("startDate", startDate).getResultList();
    }
}