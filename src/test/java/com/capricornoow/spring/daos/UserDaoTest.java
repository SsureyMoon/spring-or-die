package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

/**
 * Created by smoon on 2018. 7. 8..
 */
public class UserDaoTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("whiteship1");
        user.setName("나유저");
        user.setPassword("passpass");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");
        assertThat(user.getName(), is("나유저"));


        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName() + " 조회 성공");
        assertThat(user.getName(), is(user2.getName()));

        userDao.delete(user.getId());
    }
}
