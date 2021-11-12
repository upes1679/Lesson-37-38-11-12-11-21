package com.jdbc_demo;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    private static String connectionString = "jdbc:sqlite:C:\\Users\\markf\\IdeaProjects\\FirstJDBC\\DB\\myfirstdb.db";

    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO(connectionString);
//        studentDAO.createStudent("Mark",123456,"Mark@gmail.com");
//        Student student= studentDAO.getStudent("Mark");
//        studentDAO.updateStudent("Student",467856,"Mark@gmail.com");
//        studentDAO.deleteStudent("Mark@gmail.com");

        ArrayList<Student> students = studentDAO.getStudents();
        for (Student s : students) {
            System.out.println(s.getEmail());
        }
    }

    private static void createTable() throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = DriverManager.getConnection(connectionString);

            statement = conn.createStatement();
            statement.execute("CREATE TABLE students (name Text, phone Integer, email Text)");
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        } finally {
            statement.close();
            conn.close();
        }
    }

    public static void insertValues() {
        try (Connection conn =
                     DriverManager.getConnection(connectionString);
             Statement statement = conn.createStatement();) {
            statement.executeUpdate("INSERT INTO `students` (`name`,`phone`, `email`)" +
                    " Values ('David',456789,'david@gmail.com')");
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }
    }

    public static void readValues() {
        try (Connection conn =
                     DriverManager.getConnection(connectionString);
             Statement statement = conn.createStatement();) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE name = 'John'");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                System.out.println("Student name is " + name + ", phone: " + phone + ", email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }

    }
}
