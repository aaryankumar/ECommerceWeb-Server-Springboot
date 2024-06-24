package com.ecommerceProject.ecom.entity;

import com.ecommerceProject.ecom.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity//
@Data //for creating getters and setters
@Table(name = "users")//it specifies the table in the database with which this entity is mapped.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//it will auto increment the id while creating new roe
    private long id;
    private String email;
    private String password;
    private String name;
    private UserRole role;

    @Lob //for storing large data
    @Column(columnDefinition = "longblob")
    private byte[] img; //for storing user image

}
