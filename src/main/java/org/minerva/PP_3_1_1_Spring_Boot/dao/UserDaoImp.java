package org.minerva.PP_3_1_1_Spring_Boot.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.minerva.PP_3_1_1_Spring_Boot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImp.class);


    @Override
    public User getUserByID (long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List <User> getAllUsers () {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void addUser (User user) {
        entityManager.persist(user);
        logger.info("Saved user: {}", user);
    }

    @Override
    public User updateUser (User user) {
        User newUser = entityManager.merge(user);
        logger.info("Update user with ID={}: {}", user.getId(), user);
        return newUser;
    }

    @Override
    public void deleteUser (long id) {
        entityManager.remove(getUserByID(id));
        logger.info("Deleted user with ID={}", id);
    }

    @Override
    public boolean existsEmail (String email) {
        String sql = "select u from User u where u.email = :email";
        List <User> list = entityManager.createQuery(sql, User.class).setParameter("email", email).getResultList();
        return list.size() > 0;
    }
}
