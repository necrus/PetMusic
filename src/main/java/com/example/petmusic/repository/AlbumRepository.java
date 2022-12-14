package com.example.petmusic.repository;

import com.example.petmusic.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByBandId(Long id);
}