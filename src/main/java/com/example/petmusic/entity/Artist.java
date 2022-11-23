package com.example.petmusic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Artist {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "instrument", length = 50)
    private String instrument;
    @Basic
    @Column(name = "photo")
    private byte[] photo;

    @Basic
    @Column(name = "artist_name", length = 100)
    private String artistName;

    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;

    public Artist(Long id, String instrument, byte[] photo, String artistName) {
        this.id = id;
        this.instrument = instrument;
        this.photo = photo;
        this.artistName = artistName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (!Objects.equals(id, artist.id)) return false;
        if (!Objects.equals(instrument, artist.instrument)) return false;
        if (!Arrays.equals(photo, artist.photo)) return false;
        if (!Objects.equals(artistName, artist.artistName)) return false;
        return Objects.equals(band, artist.band);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (instrument != null ? instrument.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(photo);
        result = 31 * result + (artistName != null ? artistName.hashCode() : 0);
        result = 31 * result + (band != null ? band.hashCode() : 0);
        return result;
    }
}
