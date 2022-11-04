package com.example.petmusic.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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
