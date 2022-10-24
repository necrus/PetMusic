package com.example.petmusic.controller;

import com.example.petmusic.entity.Track;
import com.example.petmusic.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MusicController {
    TrackRepository trackRepository;

    @Autowired
    public void setTrackRepository(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Track> tracks = trackRepository.findAll();
        model.addAttribute("tracks", tracks);
        return "index";
    }
}
