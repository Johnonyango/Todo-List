package com.john.DAO;

import com.john.JdbcUtill.DbConnection;
import com.john.javaBean.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDb {
    DbConnection dbConnection;
    public int registerEmployee(User employee) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "INSERT INTO users" +
                "  (first_name, last_name, username, password) VALUES " +
                " (?, ?, ?, ?);";

        int result = 0;
        try{
            PreparedStatement st = this
                    .dbConnection
                    .getConnection()
                    .prepareStatement(INSERT_USERS_SQL);
            st.setString(1, employee.getFirstName());
            st.setString(2, employee.getLastName());
            st.setString(3, employee.getUsername());
            st.setString(4, employee.getPassword());

            System.out.println(st);
            result = st.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
