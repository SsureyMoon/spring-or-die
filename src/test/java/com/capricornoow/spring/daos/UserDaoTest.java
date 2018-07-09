package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

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

        User user = new User();
        user.setId("whiteship1");
        user.setName("나유저");
        user.setPassword("passpass");

        userDao.add(user);
        assertThat(userDao.getCount(), is(1));

        User user2 = userDao.get(user.getId());
        assertThat(user2.getName(), is(user.getName()));
        assertThat(user2.getPassword(), is(user.getPassword()));

        userDao.deleteAll();
        assertThat(userDao.getCount(), is(0));
    }
}
