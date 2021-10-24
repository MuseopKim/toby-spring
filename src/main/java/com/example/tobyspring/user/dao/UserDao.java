package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.*;

@NoArgsConstructor
public abstract class UserDao {

    private DataSource dataSource;
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    // 수정자 메소드 DI
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO users (id, name, password) VALUES (?,?,?)");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());

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
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = makeStatement(connection);
            preparedStatement.execute();
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

    public int getCount() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            StatementStrategy strategy = new DeleteAllStatement();

            preparedStatement = strategy.makePreparedStatement(connection);
            preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
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

    abstract protected PreparedStatement makeStatement(Connection connection) throws SQLException;
}
