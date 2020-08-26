package com.john.DAO.todo;

import com.john.JdbcUtill.DbConnection;
import com.john.models.Todo;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoDb implements TodoDbI {
    DbConnection dbConnection;
    PreparedStatement st;

    private static final String INSERT_TODOS_SQL = "INSERT INTO todos" +
            "  (title, username, description, target_date,  is_done) VALUES " + " (?, ?, ?, ?, ?);";

    private static final String SELECT_TODO_BY_ID = "select id,title,username,description,target_date,is_done from todos where id =?";
    private static final String SELECT_ALL_TODOS = "select * from todos";
    private static final String DELETE_TODO_BY_ID = "delete from todos where id = ?;";
    private static final String UPDATE_TODO = "update todos set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?;";

    public TodoDb(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void insertTodo(Todo todo) {
        System.out.println(INSERT_TODOS_SQL);

        try {
                st = dbConnection.getConnection().prepareStatement(INSERT_TODOS_SQL);
                st.setString(1, todo.getTitle());
                st.setString(2, todo.getUsername());
                st.setString(3, todo.getDescription());
                st.setDate(4, dbConnection.getSQLDate(todo.getTargetDate()));
                st.setBoolean(5, todo.getStatus());

                System.out.println(st);
                st.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Todo selectTodo(long todoId) {
        Todo todo = null;
        try {
        Connection connection = dbConnection.getConnection();
        PreparedStatement st = connection.prepareStatement(SELECT_TODO_BY_ID);
            st.setLong(1, todoId);
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate targetDate = rs.getDate("target_date").toLocalDate();
                boolean isDone = rs.getBoolean("is_done");
                todo = new Todo(id, title, username, description, targetDate, isDone);
            }
        } catch (SQLException exception) {
        exception.printStackTrace();
        }
        return todo;
    }

    @Override
    public List< Todo > selectAllTodos() {
        List < Todo > todos = new ArrayList< >();

        //  Establishing a Connection
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement st = connection.prepareStatement(SELECT_ALL_TODOS);
            System.out.println(st);
            ResultSet rs = st.executeQuery();

            // Process the ResultSet object.
            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String username = rs.getString("username");
                String description = rs.getString("description");
                LocalDate targetDate = rs.getDate("target_date").toLocalDate();
                boolean isDone = rs.getBoolean("is_done");
                todos.add(new Todo(id, title, username, description, targetDate, isDone));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return todos;
    }

    @Override
    public boolean deleteTodo(int id) throws SQLException {
        boolean rowDeleted = false;
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement st = connection.prepareStatement(DELETE_TODO_BY_ID);
        st.setInt(1, id);
            rowDeleted = st.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowDeleted;
    }

    @Override
    public boolean updateTodo(Todo todo) throws SQLException {
        boolean rowUpdated;
        PreparedStatement st = this
                .dbConnection
                .getConnection()
                .prepareStatement(UPDATE_TODO);
        st.setString(1, todo.getTitle());
            st.setString(2, todo.getUsername());
            st.setString(3, todo.getDescription());
            st.setDate(4, dbConnection.getSQLDate(todo.getTargetDate()));
            st.setBoolean(5, todo.getStatus());
            st.setLong(6, todo.getId());
            rowUpdated = st.executeUpdate() > 0;
        return rowUpdated;
    }
}
