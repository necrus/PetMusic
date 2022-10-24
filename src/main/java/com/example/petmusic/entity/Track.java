package com.example.petmusic.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
public class Track {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "track_name", nullable = false, length = 100)
    private String trackName;
    @Basic
    @Column(name = "length", nullable = true)
    private Time length;
    @Basic
    @Column(name = "album_id", nullable = true)
    private Long albumId;


}
