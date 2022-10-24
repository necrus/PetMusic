package com.example.petmusic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Album {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "album_name", nullable = false, length = 100)
    private String albumName;
    @Basic
    @Column(name = "year", nullable = true)
    private Integer year;
    @Basic
    @Column(name = "album_art", nullable = true, length = 255)
    private String albumArt;
    @Basic
    @Column(name = "band_id", nullable = true)
    private Long bandId;


}
