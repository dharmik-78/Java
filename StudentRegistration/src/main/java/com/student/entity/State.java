package com.student.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "states")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
