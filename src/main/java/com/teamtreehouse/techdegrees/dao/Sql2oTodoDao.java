package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.Todo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oTodoDao implements TodoDao{

    private final Sql2o sql2o;

    public Sql2oTodoDao(Sql2o sql2o) {
        this.sql2o = sql2o;

        //  Create todos table if it doesn't exist
        try (Connection conn = sql2o.open()) {

        }
    }

    @Override
    public void create(Todo todo) throws DaoException {

    }

    @Override
    public void update(int id, Todo todo) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Todo> findAll() {
        return List.of();
    }
}
