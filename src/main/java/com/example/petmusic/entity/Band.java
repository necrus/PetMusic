package com.example.petmusic.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
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
    private List<Artist> artists = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "band_genre",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new LinkedHashSet<>();


    public Band(Long id, String country, String bandName) {
        this.id = id;
        this.country = country;
        this.bandName = bandName;
    }

}
