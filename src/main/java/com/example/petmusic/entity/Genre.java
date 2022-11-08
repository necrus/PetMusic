package com.example.petmusic.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Genre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "genre_name", nullable = false, length = 50)
    private String genreName;

    @ManyToMany
    @JoinTable(name = "band_genre",
            joinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "band_id", referencedColumnName = "id"))
    private List<Band> bands;


    public Genre(Long id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }


}
