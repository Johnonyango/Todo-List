package com.john.JdbcUtill;

import java.sql.*;
import java.time.LocalDate;

public class DbConnection implements DbConnectionI {
    private String username;
    private String password;
    private String url;
    private Connection connection;

    public DbConnection() throws SQLException, ClassNotFoundException {
        this.url = "jdbc:mysql://localhost:6660/organization?useSSL=false";
        this.username = "root";
        this.password = "";
        this.connect();
    }

    private void connect() throws SQLException, ClassNotFoundException {
        this.connection = this.openConnection();
    }
    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet executeQuery(String sql)  throws SQLException {
        System.out.println(sql);
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(sql);
    }

    @Override
    public boolean execute(PreparedStatement preparedStatement)  throws SQLException {
        return preparedStatement.execute();
    }

    @Override
    public boolean execute(String sql)  throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.execute(sql);
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }
    public static Date getSQLDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

    public static LocalDate getUtilDate(Date sqlDate) {
        return sqlDate.toLocalDate();
    }
}