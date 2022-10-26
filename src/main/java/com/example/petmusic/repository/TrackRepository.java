package com.example.petmusic.repository;

import com.example.petmusic.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByAlbumId(Long id);
}
