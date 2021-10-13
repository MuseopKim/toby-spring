package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;

import java.sql.*;

public abstract class UserDao {

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (id, name, password) VALUES (?,?,?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
