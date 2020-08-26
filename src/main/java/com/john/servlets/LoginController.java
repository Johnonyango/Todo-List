package com.john.servlets;

import com.john.DAO.LoginDb;
import com.john.JdbcUtill.DbConnection;
import com.john.javaBean.LoginBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginDb loginDb;
DbConnection dbConnection;

    public void init() {
        dbConnection = (DbConnection) getServletContext().getAttribute("dbConnection");
        loginDb = new LoginDb(dbConnection);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        authenticate(request, response);
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword(password);

        try {
            if (loginDb.validate(loginBean)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                RequestDispatcher dispatcher = request.getRequestDispatcher("list");
                dispatcher.forward(request, response);
            return;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        response.sendRedirect("login.jsp");

    }
}
