package com.example.petmusic.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "album_name", nullable = false, length = 100)
    private String albumName;
    @Basic
    @Column(name = "year")
    private Integer year;
    @Basic
    @Column(name = "album_art")
    private String albumArt;
    @Basic
    @Column(name = "band_id")
    private Long bandId;

    @Column(name = "art")
    private byte[] art;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (!Objects.equals(id, album.id)) return false;
        if (!Objects.equals(albumName, album.albumName)) return false;
        if (!Objects.equals(year, album.year)) return false;
        if (!Objects.equals(albumArt, album.albumArt)) return false;
        return Objects.equals(bandId, album.bandId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (albumName != null ? albumName.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (albumArt != null ? albumArt.hashCode() : 0);
        result = 31 * result + (bandId != null ? bandId.hashCode() : 0);
        return result;
    }
}
