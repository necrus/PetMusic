package com.example.petmusic.repository;

import com.example.petmusic.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Long> {

}