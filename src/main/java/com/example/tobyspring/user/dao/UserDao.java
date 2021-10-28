package com.example.tobyspring.user.dao;

import com.example.tobyspring.user.domain.User;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@NoArgsConstructor
public class UserDao {

    private ConnectionMaker connectionMaker;
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper = new RowMapper<User>() {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
                new Object[]{id}, this.userRowMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", this.userRowMapper);
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }
}
