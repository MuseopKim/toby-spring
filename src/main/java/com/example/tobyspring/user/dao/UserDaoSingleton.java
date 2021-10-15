package com.example.tobyspring.user.dao;

public class UserDaoSingleton {

    private static UserDaoSingleton INSTANCE;
    private ConnectionMaker connectionMaker;

    private UserDaoSingleton(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public static synchronized UserDaoSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoSingleton(new DConnectionMaker());
        }

        return INSTANCE;
    }
}
