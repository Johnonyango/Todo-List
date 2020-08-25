package com.john.DAO;

import com.john.JdbcUtill.DbConnection;
import com.john.javaBean.LoginBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDb {
    DbConnection dbConnection;

    public boolean validate(LoginBean loginBean) throws ClassNotFoundException {
        boolean status = false;

    try {
        Connection connection = dbConnection.getConnection();
        PreparedStatement st = connection.prepareStatement("select * from users where username = ? and password = ? ");
        st.setString(1, loginBean.getUsername());
        st.setString(2, loginBean.getPassword());
        ResultSet rs = dbConnection.executeQuery(st);
        if (rs.next())
            return true;
    } catch (SQLException throwable) {
        throwable.printStackTrace();
    }
        return status;
    }
}
