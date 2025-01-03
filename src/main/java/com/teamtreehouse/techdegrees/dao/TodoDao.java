package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;

import java.util.List;

public interface TodoDao {
    void create(Todo todo) throws DaoException; // Add a new Todo

    List<Todo> findAll(); // Retrieve all Todos

    Todo findById(int id);

    void update(int id, Todo todo) throws DaoException; // Update an existing Todo

    void delete(int id) throws DaoException; // Delete a Todo by Id


}
