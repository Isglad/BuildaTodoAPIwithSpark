-- Create the todos table if it doesn't already exist

CREATE TABLE IF NOT EXISTS todos (
    id INTEGER PRIMARY KEY auto_increment, -- Auto-incrementing integer ID for each todo
    name VARCHAR, -- Name or Title of the todo item
    isCompleted BOOLEAN -- Status indicating if the todo is completed (true/false)
);