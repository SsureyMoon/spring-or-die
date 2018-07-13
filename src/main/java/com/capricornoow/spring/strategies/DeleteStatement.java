package com.capricornoow.spring.strategies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStatement implements StatementStrategy {
    private String userId;

    public DeleteStatement(String userId) {
        this.userId = userId;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("delete from users where id = ?");
        ps.setString(1, userId);
        return ps;
    }
}
