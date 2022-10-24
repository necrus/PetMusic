package com.example.petmusic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Artist {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "instrument", nullable = true, length = 50)
    private String instrument;
    @Basic
    @Column(name = "photo", nullable = true, length = 255)
    private String photo;
    @Basic
    @Column(name = "band_id", nullable = true)
    private Long bandId;
    @Basic
    @Column(name = "artist_name", nullable = true, length = 100)
    private String artistName;


}
