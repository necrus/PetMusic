package com.example.petmusic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Genre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "genre_name", nullable = false, length = 50)
    private String genreName;


}
