package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

public interface TodoDao {
    void create(Todo todo) throws DaoException; // Add a new Todo

    void update(int id, Todo todo); // Update an existing Todo

    void delete(int id); // Delete a Todo by Id

    List<Todo> findAll(); // Retrieve all Todos

}
