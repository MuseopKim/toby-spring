package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class UserDao {

    private DataSource dataSource;
    private ConnectionMaker connectionMaker;
    private JdbcTemplate jdbcTemplate;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) {
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setString(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));

            return user;
        } catch (SQLException exception) {
            throw exception;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException exception) {
                }
            }

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

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }
}
