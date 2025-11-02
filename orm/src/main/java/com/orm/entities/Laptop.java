package com.orm.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Laptop {
    @Id
    public String id;
    public String brand;
    @ManyToMany
    public List<Employee> employees;
}
