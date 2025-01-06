package com.teamtreehouse.techdegrees;

import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oTodoDao;
import com.teamtreehouse.techdegrees.model.Todo;
import com.teamtreehouse.techdegrees.testing.ApiClient;
import com.teamtreehouse.techdegrees.testing.ApiResponse;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;

public class AppTest {

    public static final String PORT = "4568";
    public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
    private Connection conn;
    private ApiClient client;
    private Gson gson;
    private Sql2oTodoDao todoDao;

    @BeforeClass
    public static void startServer() throws Exception {
        String[] args = {PORT, TEST_DATASOURCE};
        App.main(args);
    }

    @AfterClass
    public static void stopServer() throws Exception {
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(TEST_DATASOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        todoDao = new Sql2oTodoDao(sql2o);
        conn = sql2o.open();
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void allCreatedTodosAreReturnedWithFindAll() throws Exception {
        // Arrange
        Todo todo1 = new Todo("Project1", false);
        todoDao.create(todo1);
        Todo todo2 = new Todo("Project2", false);
        todoDao.create(todo2);
        Todo todo3 = new Todo("Project3", true);
        todoDao.create(todo3);
        Todo todo4 = new Todo("Project3", true);
        todoDao.create(todo4);

        // Act
        ApiResponse res = client.request("GET",
                "/api/v1/todos");
        Todo[] allTodos = gson.fromJson(res.getBody(), Todo[].class);

        // Assert
        assertEquals(4, allTodos.length);
    }

    @Test
    public void aTodoCanBeFoundById() throws Exception {
        // Arrange
        Todo todo = newTestTodo();
        todoDao.create(todo);

        // Act
        ApiResponse res = client.request("GET",
                "/api/v1/todos/" + todo.getId());
        Todo retrieved = gson.fromJson(res.getBody(), Todo.class);

        // Assert
        assertEquals(todo, retrieved);
    }

    @Test
    public void nonExistingTodoReturns404NotFoundStatus() throws Exception {
        // Act
        ApiResponse res = client.request("GET", "/api/v1/todos/23");

        //Assert
        assertEquals(404, res.getStatus());
    }

    @Test
    public void creatingTodoReturnsCreatedStatus() throws Exception {
        // Arrange
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "Project Test");
        requestBody.put("isCompleted", String.valueOf(true));

        // Act
        ApiResponse res = client.request("POST", "/api/v1/todos", gson.toJson(requestBody));

        // Assert
        assertEquals(201, res.getStatus()); // Ensure response status is 201 Created
        assertNotNull(res.getBody()); // Ensure the response body is not null
    }

    @Test
    public void todoCanSuccessfullyBeUpdated() throws Exception {
        // Arrange
        Todo todo = newTestTodo();
        todoDao.create(todo);
        Map<String, Object> values = new HashMap<>();
        values.put("name", "VerifiedTodo");
        values.put("isCompleted", true);

        // Act
        ApiResponse res = client.request("PUT",
                "/api/v1/todos/" + todo.getId(), gson.toJson(values));
        Todo updatedTodo = gson.fromJson(res.getBody(), Todo.class);

        // Assert
        assertEquals("VerifiedTodo", updatedTodo.getName());
        assertTrue(updatedTodo.isCompleted());
        assertEquals(200, res.getStatus());
    }

    @Test
    public void todoCanBeDeletedSuccessfully() throws Exception {
        // Arrange
        Todo todo = newTestTodo();
        todoDao.create(todo);
        int id = todo.getId();

        // Act
        ApiResponse res = client.request("DELETE",
                "/api/v1/todos/" + todo.getId());

        // Assert
        assertEquals(204, res.getStatus());
        assertNull(todoDao.findById(id));
    }

    private static Todo newTestTodo() {
        return new Todo("TestTodo", false);
    }
}