package com.orm.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Department {
    @Id
    private int id;
    private String name;
    @ManyToMany(mappedBy = "departments")
    private List<Employee> employeesxyz = new ArrayList<>();
}
