package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }
    SessionFactory sessionFactory = Util.getSessionFactory();
    Transaction transaction = null;

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS usersdb (
                id BIGINT(19) NOT NULL AUTO_INCREMENT,
                name VARCHAR(45) NOT NULL,
                lastName VARCHAR(45) NOT NULL,
                age TINYINT(3) NOT NULL,
                PRIMARY KEY (id))
                """;
        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS usersdb";
        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("truncate usersdb").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        }
    }
}
