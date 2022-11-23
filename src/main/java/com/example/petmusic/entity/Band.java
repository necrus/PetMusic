package com.example.petmusic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Band {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "country", length = 50)
    private String country;

    @Basic
    @Column(name = "band_name", nullable = false, length = 100)
    private String bandName;

    @OneToMany(mappedBy = "band", orphanRemoval = true)
    @ToString.Exclude
    private List<Artist> artists = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "band_genre",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ToString.Exclude
    private Set<Genre> genres = new LinkedHashSet<>();


    public Band(Long id, String country, String bandName) {
        this.id = id;
        this.country = country;
        this.bandName = bandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Band band = (Band) o;

        if (!Objects.equals(id, band.id)) return false;
        if (!Objects.equals(country, band.country)) return false;
        return Objects.equals(bandName, band.bandName);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (bandName != null ? bandName.hashCode() : 0);
        return result;
    }
}
