package com.example.demo.domains.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    @Column(name="product_id")
    private long id;
}
