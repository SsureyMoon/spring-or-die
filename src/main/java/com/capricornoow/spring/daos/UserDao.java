package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;
import com.capricornoow.spring.strategies.AddStatement;
import com.capricornoow.spring.strategies.DeleteAllStatement;
import com.capricornoow.spring.strategies.DeleteStatement;
import com.capricornoow.spring.strategies.StatementStrategy;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        StatementStrategy stmt = new AddStatement(user);
        jdbcContextWithStatementStrategy(stmt);
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

    public void delete(String id) throws SQLException {
        StatementStrategy stmt = new DeleteStatement(id);
        jdbcContextWithStatementStrategy(stmt);
    }

    public void deleteAll() throws SQLException {
        StatementStrategy stmt = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(stmt);
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

    private void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = stmt.makePreparedStatement(conn);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
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
