package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.Level;
import com.capricornoow.spring.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    private User user1;
    private User user2;
    private User user3;


    @Before
    public void setUp() {
        user1 = new User("id1", "일이삼", "pass1", Level.BASIC, 1, 0);
        user2 = new User("id2", "사오육", "pass2", Level.SILVER, 55, 10);
        user3 = new User("id3", "칠팔구", "pass3", Level.GOLD, 100, 40);
    }

    @Test
    public void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount(), is(2));

        User user1get = userDao.get(user1.getId());
        checkSameUser(user1get, user1);

        User user2get = userDao.get(user2.getId());
        checkSameUser(user2get, user2);

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));
    }

    @Test(expected=DataAccessException.class)
    public void duplicateKey() {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        userDao.add(user1);
    }

    @Test(expected=EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.get("unknown_id");
    }

    @Test
    public void count() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        assertThat(userDao.getCount(), is(1));

        userDao.add(user2);
        assertThat(userDao.getCount(), is(2));

        userDao.add(user3);
        assertThat(userDao.getCount(), is(3));

        userDao.delete(user3.getId());
        assertThat(userDao.getCount(), is(2));

        userDao.delete(user2.getId());
        assertThat(userDao.getCount(), is(1));

        userDao.delete(user1.getId());
        assertThat(userDao.getCount(), is(0));
    }

    @Test
    public void getAll() throws SQLException {
        userDao.deleteAll();
        List<User> users = userDao.getAll();
        assertThat(users.size(), is(0));

        userDao.add(user1);
        users = userDao.getAll();
        assertThat(users.size(), is(1));
        checkSameUser(user1, users.get(0));

        userDao.add(user2);
        users = userDao.getAll();
        assertThat(users.size(), is(2));
        checkSameUser(user1, users.get(0));
        checkSameUser(user2, users.get(1));

        userDao.add(user3);
        users = userDao.getAll();
        assertThat(users.size(), is(3));
        checkSameUser(user1, users.get(0));
        checkSameUser(user2, users.get(1));
        checkSameUser(user3, users.get(2));
    }

    @After
    public void tearDown() throws SQLException {
        userDao.deleteAll();
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));
    }
}
