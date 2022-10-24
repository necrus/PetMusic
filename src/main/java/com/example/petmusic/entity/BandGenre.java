package com.example.petmusic.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "band_genre", schema = "public", catalog = "pet_music")
@IdClass(BandGenrePK.class)
@Data
public class BandGenre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "band_id", nullable = false)
    private long bandId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genre_id", nullable = false)
    private long genreId;


}
