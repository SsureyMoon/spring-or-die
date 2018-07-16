package com.capricornoow.spring.daos;

import com.capricornoow.spring.contexts.JdbcContext;
import com.capricornoow.spring.domain.User;
import com.capricornoow.spring.strategies.StatementStrategy;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;
    private JdbcContext jdbcContext;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(final User user) throws SQLException {
        StatementStrategy stmt = conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into users(id, name, password) values(?,?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            return ps;
        };
        this.jdbcContext.workWithStatementStrategy(stmt);
    }

    public User get(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(
                    "select * from users where id = ?");
            ps.setString(1, id);

            rs = ps.executeQuery();
            if(rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {}

            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
        if(user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void delete(final String id) throws SQLException {
        StatementStrategy stmt = conn -> {
            PreparedStatement ps = conn.prepareStatement("delete from users where id = ?");
            ps.setString(1, id);
            return ps;
        };
        this.jdbcContext.workWithStatementStrategy(stmt);
    }

    public void deleteAll() throws SQLException {
        StatementStrategy stmt = conn -> conn.prepareStatement("delete from users");
        this.jdbcContext.workWithStatementStrategy(stmt);
    }

    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(
                    "select count(*) as count from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {}

            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
