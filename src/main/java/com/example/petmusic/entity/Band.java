package com.example.petmusic.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Band band = (Band) o;
        return id != null && Objects.equals(id, band.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
