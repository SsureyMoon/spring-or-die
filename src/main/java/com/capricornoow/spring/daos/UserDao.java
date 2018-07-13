package com.capricornoow.spring.daos;

import com.capricornoow.spring.domain.User;
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
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(
                    "insert into users(id, name, password) values(?,?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps!= null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
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
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(
                    "delete from users where id = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps!= null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public void deleteAll() throws SQLException {
        Connection conn = null;
        PreparedStatement ps =null;

        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("delete from users");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if(ps!= null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
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
