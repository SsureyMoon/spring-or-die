package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

public class UserDaoTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        User user1 = new User("whiteship1", "나유저", "passpass1");
        User user2 = new User("whiteship2", "너도유저", "passpass2");

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
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));

        userDao.get("unknown_id");
    }

    @Test
    public void count() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao userDao = context.getBean("userDao", UserDao.class);
        User user1 = new User("id1", "일이삼", "pass1");
        User user2 = new User("id2", "사오육", "pass2");
        User user3 = new User("id3", "칠팔구", "pass3");

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
}
