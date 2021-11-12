package com.singleton_connection;

import com.jdbc_demo.Student;

import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        ArrayList<Student> students1=getAll();
//        ArrayList<Student> students2=getAll();
//        System.out.println(printDetails(students1));
//        System.out.println(printDetails(students2));

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1: "+printDetails(getAll()));
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t2: "+printDetails(getAll()));
            }
        });
        t1.start();
        t2.start();


        try {
            Thread.sleep(3000);
            System.out.println((SingletonConnection.getInstance().getInstanceCount()));
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Student> getAll() {
        try  {
            Connection conn = SingletonConnection.getInstance().getConnection();
            Statement statement = conn.createStatement();

            Thread.sleep(1000);

            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            ArrayList<Student> students = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int phone = resultSet.getInt("phone");
                String email = resultSet.getString("email");

                Student student = new Student(name, phone, email);
                students.add(student);
            }

            return students;
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String printDetails(ArrayList<Student> students){
        StringBuilder builder=new StringBuilder();
        for (Student s:students) {
            builder.append(s.getName()+", ");
        }

        return  builder.toString();
    }
}