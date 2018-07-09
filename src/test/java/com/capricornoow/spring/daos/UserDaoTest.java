package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    private User user1;
    private User user2;
    private User user3;


    @Before
    public void setUp() {
        user1 = new User("id1", "일이삼", "pass1");
        user2 = new User("id2", "사오육", "pass2");
        user3 = new User("id3", "칠팔구", "pass3");
    }

    @Test
    public void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount(), is(2));

        User user1get = userDao.get(user1.getId());
        assertThat(user1get.getName(), is(user1.getName()));
        assertThat(user1get.getPassword(), is(user1.getPassword()));

        User user2get = userDao.get(user2.getId());
        assertThat(user2get.getName(), is(user2.getName()));
        assertThat(user2get.getPassword(), is(user2.getPassword()));

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));
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

    @After
    public void tearDown() throws SQLException {
        userDao.deleteAll();
    }
}
