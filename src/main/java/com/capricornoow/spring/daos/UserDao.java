package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update(
                "insert into users(id, name, password) values(?,?,?)",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, (rs, rowNum) -> {
            // 이미 첫번째 row를 가리키고 있어서 rs.next 호출 필요 없다.
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        });
    }

    public List<User> getAll() throws SQLException {
        return this.jdbcTemplate.query("select * from users order by id", (rs, rowNum) -> {
            // 이미 첫번째 row를 가리키고 있어서 rs.next 호출 필요 없다.
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        });
    }

    public void delete(final String id) throws SQLException {
        this.jdbcTemplate.update("delete from users where id = ?", id);
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("select count(*) as count from users", Integer.class);
    }
}
