package dev.wegner.academy_app.student;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = Student.TABLE_NAME)
public class Student implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "student";

    public static final String ID_COLUMN = "id";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String EMAIL_COLUMN = "email";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN)
    private Long id;

    @Column(name = FIRST_NAME_COLUMN, nullable = false)
    private String firstName;

    @Column(name = LAST_NAME_COLUMN, nullable = false)
    private String lastName;

    @Column(name = EMAIL_COLUMN, nullable = false, unique = true)
    private String email;

    protected Student()
    {
        // JPA
    }

    private Student( String firstName, String lastName, String email )
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static Student create( final String firstName, final String lastName, final String email )
    {
        return new Student(firstName, lastName, email);
    }

    public Long getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }
}