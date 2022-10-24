package com.example.petmusic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Band {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "country", nullable = true, length = 50)
    private String country;
    @Basic
    @Column(name = "band_name", nullable = false, length = 100)
    private String bandName;


}
