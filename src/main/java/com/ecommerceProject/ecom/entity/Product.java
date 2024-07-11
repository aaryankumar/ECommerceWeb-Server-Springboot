package com.ecommerceProject.ecom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long price;
    @Lob //for storing large data
    private String description;

    @Lob
    @Column(columnDefinition = "longblob") //this  annotation is used because we have to store large images
    private byte[] img; //for storing img of product


    @ManyToOne(fetch = FetchType.LAZY, optional = false) //creating relation between Category and products. We have used "ManyToOne" because we want many products in one category
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;
}
