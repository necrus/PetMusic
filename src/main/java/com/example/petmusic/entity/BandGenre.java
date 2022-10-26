package com.example.petmusic.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "band_genre", schema = "public", catalog = "pet_music")
@IdClass(BandGenrePK.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BandGenre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "band_id", nullable = false)
    private Long bandId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genre_id", nullable = false)
    private Long genreId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BandGenre bandGenre = (BandGenre) o;
        return bandId != null && Objects.equals(bandId, bandGenre.bandId)
                && genreId != null && Objects.equals(genreId, bandGenre.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bandId, genreId);
    }
}
