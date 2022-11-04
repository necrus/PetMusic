package com.example.petmusic.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Track {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "track_name", nullable = false, length = 100)
    private String trackName;
    @Basic
    @Column(name = "length")
    private Time length;
    @Basic
    @Column(name = "album_id")
    private Long albumId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (!Objects.equals(id, track.id)) return false;
        if (!Objects.equals(trackName, track.trackName)) return false;
        if (!Objects.equals(length, track.length)) return false;
        return Objects.equals(albumId, track.albumId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (trackName != null ? trackName.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (albumId != null ? albumId.hashCode() : 0);
        return result;
    }
}
