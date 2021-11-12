package com.jdbc_demo;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO implements DAO{
    private String connString;

    public StudentDAO(String connString){
        this.connString=connString;
    }

    @Override
    public Student getStudent(String name) {
        try(Connection conn=
                    DriverManager.getConnection(connString);
            PreparedStatement statement=conn.prepareStatement("SELECT * FROM students WHERE name = ?");){
            statement.setString(1,name);
            ResultSet resultSet=statement.executeQuery();

            Student student = null;

            while (resultSet.next()){
                int phone=resultSet.getInt("phone");
                String email=resultSet.getString("email");
                student=new Student(name,phone,email);
            }

            return student;
        }
        catch (SQLException e){
            System.out.println("SQL Exception"+e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<Student> getStudents() {
        try (Connection conn = DriverManager.getConnection(connString);
             Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet=statement.executeQuery("SELECT * FROM students");
            ArrayList<Student> students=new ArrayList<>();

            while(resultSet.next()){
                String name= resultSet.getString("name");
                int phone=resultSet.getInt("phone");
                String email=resultSet.getString("email");

                Student student=new Student(name,phone,email);
                students.add(student);
            }

            return students;
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
            return null;
        }
    }

    @Override
    public void createStudent(String name, int phone, String email) {
        try(Connection conn=
                    DriverManager.getConnection(connString);
            PreparedStatement statement=conn.prepareStatement("INSERT INTO `students` values (?,?,?)");){
            statement.setString(1,name);
            statement.setInt(2,phone);
            statement.setString(3,email);

            int rowsAffected= statement.executeUpdate();
            System.out.println(rowsAffected);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateStudent(String name, int phone, String email) {
        try(Connection conn=
                    DriverManager.getConnection(connString);
            PreparedStatement statement=conn.prepareStatement("UPDATE `students` SET name=?, phone=? WHERE email=?");){
            statement.setString(1,name);
            statement.setInt(2,phone);
            statement.setString(3,email);

            int rowsAffected= statement.executeUpdate();
            System.out.println(rowsAffected);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(String email) {
        try(Connection conn=
                    DriverManager.getConnection(connString);
            PreparedStatement statement=conn.prepareStatement("DELETE FROM `students` WHERE email=?");){
            statement.setString(1,email);

            int rowsAffected= statement.executeUpdate();
            System.out.println(rowsAffected);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
