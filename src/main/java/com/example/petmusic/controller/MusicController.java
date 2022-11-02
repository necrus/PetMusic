package com.example.petmusic.controller;

import com.example.petmusic.entity.Album;
import com.example.petmusic.entity.Band;
import com.example.petmusic.entity.Track;
import com.example.petmusic.repository.AlbumRepository;
import com.example.petmusic.repository.BandRepository;
import com.example.petmusic.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MusicController {
    TrackRepository trackRepository;
    BandRepository bandRepository;
    AlbumRepository albumRepository;

    @Autowired
    public void setAlbumRepository(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Autowired
    public void setBandRepository(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

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

    @GetMapping("/bands")
    public String bands(Model model) {
        List<Band> bands = bandRepository.findAll();
        model.addAttribute("bands", bands);
        return "bands";
    }

    @GetMapping("/albums")
    public String albums(@RequestParam Optional<Long> band_id, Model model) {
        List<Album> albums;
        if (band_id.isPresent()) {
            albums = albumRepository.findByBandId(band_id.get());
        } else {
            albums = albumRepository.findAll();
        }
        model.addAttribute("albums", albums);
        return "albums";
    }

    @GetMapping("/tracks")
    public String tracks(@RequestParam Optional<Long> album_id, Model model) {
        List<Track> tracks;
        if (album_id.isPresent()) {
            tracks = trackRepository.findByAlbumId(album_id.get());
            Optional<Album> album = albumRepository.findById(album_id.get());
            model.addAttribute("album_name", album.get().getAlbumName());
        } else {
            tracks = trackRepository.findAll();
        }
        model.addAttribute("tracks", tracks);
        return "tracks";
    }

    @GetMapping("/addband")
    public String addBandForm(Band band) {
        return "addbandform";
    }

    @PostMapping("/addband")
    public String addBand(Band band) {
        bandRepository.save(band);
        return "redirect:bands";
    }

    @GetMapping("/addalbum")
    public String addAlbumForm(Album album, Model model) {
        model.addAttribute("bands", bandRepository.findAll());
        return "addalbumform";
    }

    @PostMapping("/addalbum")
    public String addAlbum(Album album) {
        albumRepository.save(album);
        return "redirect:albums";
    }

    @GetMapping("/addtrack")
    public String addTrackForm(Track track, Model model) {
        model.addAttribute("albums", albumRepository.findAll());
        return "addtrackform";
    }

    @PostMapping("/addtrack")
    public String addTrack(Track track) {
        trackRepository.save(track);
        return "redirect:tracks";
    }

    //todo: Удаление/изменение треков/альбомов/групп
    //todo: Сделать обложки альбомов
    //todo: Сделать нормальное оформление страниц
    //todo: Написать тесты


}
