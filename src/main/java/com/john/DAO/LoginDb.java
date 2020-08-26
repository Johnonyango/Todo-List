package com.john.DAO;

import com.john.JdbcUtill.DbConnection;
import com.john.javaBean.LoginBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDb {
    public LoginDb(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    DbConnection dbConnection;
    public boolean validate(LoginBean loginBean) throws ClassNotFoundException {

        try {
            PreparedStatement st = this
                    .dbConnection
                    .getConnection()
                    .prepareStatement("select * from users where username = ? and password = ? ");
            st.setString(1, loginBean.getUsername());
            st.setString(2, loginBean.getPassword());
            ResultSet rs = dbConnection.executeQuery(st);


            if (rs.next())
                return true;

        } catch (SQLException e) {
          e.printStackTrace();
        }
        return false;
    }
}
