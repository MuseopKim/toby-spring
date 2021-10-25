package com.example.tobyspring.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy strategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.dataSource.getConnection();

            preparedStatement = strategy.makePreparedStatement(connection);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw exception;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException exception) {

                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exception) {

                }
            }
        }
    }
}
