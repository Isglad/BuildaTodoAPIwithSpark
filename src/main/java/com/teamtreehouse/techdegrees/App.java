package com.teamtreehouse.techdegrees;


import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.exc.ApiError;
import com.teamtreehouse.techdegrees.model.Todo;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        String datasource = "jdbc:h2:~/todos.db"; // Database connection string
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("java TodoApi <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0])); // Set the port from args
            datasource = args[1]; // USe the datasource argument from args
        }

        // Initialize Sql2o and Todo DAO
        Sql2o sql2o = new Sql2o(
                String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource)
                , "", "");
        TodoDao todoDao = new Sql2oTodoDao(sql2o);
        Gson gson = new Gson();

        // Route to find all Todos
        get("/api/v1/todos", "application/json", (req, res) -> {
            return todoDao.findAll(); // Fetch all Todos from DB
        }, gson::toJson);

        // Route to create a new todo
        post("/api/v1/todos", "application/json", (req,res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class); // Deserialize request body
            todoDao.create(todo); // Add Todo to DB
            res.status(201); // Created status
            res.type("application/json");
            return todo;
        }, gson::toJson);

        // Route to update a Todo by ID
        put("/api/v1/todos/:id", "application/json", (req,res) -> {
            int id = Integer.parseInt(req.params("id"));
            Todo todo = gson.fromJson(req.body(), Todo.class); // Deserialize request body
            todoDao.update(id, todo); // Update Todo in DB
            return todo;
        }, gson::toJson);

        // Route to delete a Todo by ID
        delete("/api/v1/todos/:id", "application/json", (req,res) -> {
            int id = Integer.parseInt(req.params("id"));
            todoDao.delete(id); // Delete Todo from DB
            res.status(204); // No content (success)
            return "";
        });

        exception(ApiError.class, (exc, req, res) -> {
            ApiError err = (ApiError) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatus());
            res.body(gson.toJson(jsonMap));
        });

        after((req, res) -> {
            res.type("application/json");
        });
    }
}
