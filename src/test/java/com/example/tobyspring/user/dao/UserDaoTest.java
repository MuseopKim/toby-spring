package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.transaction.Transactional;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        userDao = context.getBean("userDao", UserDao.class);
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        User user1 = new User();
        user1.setId("Id1");
        user1.setName("Name1");
        user1.setPassword("spring");

        User user2 = new User();
        user2.setId("Id2");
        user2.setName("Name2");
        user2.setPassword("spring");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        User user1get = userDao.get(user1.getId());
        assertThat(user1get.getName()).isEqualTo(user1.getName());
        assertThat(user1get.getPassword()).isEqualTo(user1.getPassword());

        User user2get = userDao.get(user2.getId());
        assertThat(user2get.getName()).isEqualTo(user2.getName());
        assertThat(user2get.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }
}
