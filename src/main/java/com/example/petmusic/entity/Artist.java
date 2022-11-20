package com.example.petmusic.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Artist artist = (Artist) o;
        return id != null && Objects.equals(id, artist.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
