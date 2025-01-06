-- Create the todos table if it doesn't already exist

CREATE TABLE IF NOT EXISTS todos (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Auto-incrementing integer ID for each todo
    name VARCHAR(255) NOT NULL, -- Name or Title of the todo item
    isCompleted BOOLEAN NOT NULL -- Status indicating if the todo is completed (true/false)
);