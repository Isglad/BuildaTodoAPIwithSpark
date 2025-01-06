package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.model.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oTodoDaoTest {

    private Sql2oTodoDao dao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new Sql2oTodoDao(sql2o);
        // Keep connection open through entire test so that it is not wiped out.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void creatingTodoSetsId() throws Exception {
        Todo todo = new Todo("Project1", true);
        int originalTodoId = todo.getId();

        dao.create(todo);

        assertNotEquals(originalTodoId, todo.getId());
    }

    @Test
    public void addedTodosAreReturnedFromFindAll() throws Exception {
        Todo todo = new Todo("Project1", true);

        dao.create(todo);

        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void noTodoReturnsEmptyList()  throws Exception {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void existingTodoCanBeFoundById() throws Exception {
        Todo todo = new Todo("Project1", true);

        dao.create(todo);

        Todo existingTodo = dao.findById(todo.getId());

        assertEquals(todo, existingTodo);
    }

    @Test
    public void existingTodoCanBeDeletedById() throws Exception {
        Todo todo = new Todo("Project1", true);

        dao.create(todo);

        dao.delete(todo.getId());

        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void existingTodoCanBeUpdatedById() throws Exception {
        Todo todo = new Todo("Project1", true);
        dao.create(todo);

        todo.setName("Project 2");
        todo.setCompleted(false);
        dao.update(todo.getId(), todo);

        Todo updatedTodo = dao.findById(todo.getId());
        assertEquals("Project 2", updatedTodo.getName());
        assertFalse("false", updatedTodo.isCompleted());
    }
}