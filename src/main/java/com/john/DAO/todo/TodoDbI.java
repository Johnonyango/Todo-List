package com.john.DAO.todo;

import com.john.models.Todo;

import java.sql.SQLException;
import java.util.List;

public interface TodoDbI {
    void insertTodo(Todo todo) throws SQLException;

    Todo selectTodo(long todoId);

    List<Todo> selectAllTodos();

    boolean deleteTodo(int id) throws SQLException;

    boolean updateTodo(Todo todo) throws SQLException;
}
