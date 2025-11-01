package com.example.demo.entity;
/*means this file belongs to entity folder*/



import jakarta.persistence.*;//means importing all JPA tools (for database stuff). The * means import everything


@Entity//telling spring boot that this is a database table
@Table(name = "student")//naming the mysql table student
public class Student {
    @Id//it's the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//this will auto generate a value as id .
    private long id;//that auto generated value is stored in this variable named id having a datatype long

    //@column is used to create a column with specific instruction.
    @Column( unique = true, nullable = false)//this lines says to create a column with unique ktuid and nullable = false means this field cannot be empty
    private String ktuId;

    private String name;

    @Column( unique = true,nullable = false)//no two students can have same email and this field cannot be empty(nullabel =false)
    private String email;

    @Column(nullable = false)//no unique used because passwords of students can be same
    private String password;

    public Student() {

    }

    //this is a special constructor.this is responsible for creating objects(students).this empty constructor is filled automatically by spring boot.
    //JPA()java persistant API helps to easily map java objects to database
    //Think of it like: A blank form that JPA fills in when reading from database


    //constructor overloading : constructor with same name but different parameter

    //this constructor is used to create student object quickly without  reading database.this is a way of manually creating this
    public Student(String ktuId, String name, String email, String password) {
        this.ktuId = ktuId;
        this.name = name;
        this.email = email;
        this.password = password;

    }







}
