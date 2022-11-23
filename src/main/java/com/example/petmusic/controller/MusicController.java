package com.example.petmusic.controller;

import com.example.petmusic.entity.Album;
import com.example.petmusic.entity.Artist;
import com.example.petmusic.entity.Band;
import com.example.petmusic.entity.Track;
import com.example.petmusic.repository.AlbumRepository;
import com.example.petmusic.repository.ArtistRepository;
import com.example.petmusic.repository.BandRepository;
import com.example.petmusic.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class MusicController {
    TrackRepository trackRepository;
    BandRepository bandRepository;
    AlbumRepository albumRepository;
    ArtistRepository artistRepository;

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

    @Autowired
    public void setArtistRepository(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @GetMapping
    public String index() {
//        List<Track> tracks = trackRepository.findAll();
//        model.addAttribute("tracks", tracks);
//        return "index";
        return "redirect:bands";
    }

    @GetMapping("/bands")
    public String bands(Model model) {
        List<Band> bands = bandRepository.findAll();
        bands.sort(Comparator.comparingLong(Band::getId));
        model.addAttribute("bands", bands);
        return "bands";
    }

    @GetMapping("/albums")
    public String albums(@RequestParam Optional<Long> band_id, Model model) {
        List<Album> albums;
        if (band_id.isPresent()) {
            albums = albumRepository.findByBandId(band_id.get());
            Optional<Band> band = bandRepository.findById(band_id.get());
            band.ifPresent(value -> model.addAttribute("band", value));
        } else {
            albums = albumRepository.findAll();
        }
        albums.sort(Comparator.comparingLong(Album::getId));
        model.addAttribute("albums", albums);
        return "albums";
    }

    @GetMapping("/tracks")
    public String tracks(@RequestParam Optional<Long> album_id, Model model) {
        List<Track> tracks;
        if (album_id.isPresent()) {
            tracks = trackRepository.findByAlbumId(album_id.get());
            Optional<Album> album = albumRepository.findById(album_id.get());
            album.ifPresent(value -> model.addAttribute("album", value));
        } else {
            tracks = trackRepository.findAll();
        }
        tracks.sort(Comparator.comparingLong(Track::getId));
        model.addAttribute("tracks", tracks);
        return "tracks";
    }

    // ADD objects:

    @GetMapping("/addband")
    public String addBandForm(Model model) {
        model.addAttribute("band", new Band());
        return "addbandform";
    }

    @PostMapping("/addband")
    public String addBand(@ModelAttribute Band band) {
        bandRepository.save(band);
        return "redirect:/bands";
    }

    @GetMapping("/addalbum")
    public String addAlbumForm(Model model) {
        model.addAttribute("album", new Album());
        model.addAttribute("bands", bandRepository.findAll());
        return "addalbumform";
    }

    @PostMapping("/addalbum")
    public String addAlbum(@ModelAttribute Album album, @RequestParam MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            album.setArt(file.getBytes());
        }
        albumRepository.save(album);
        return "redirect:/albums";
    }

    @GetMapping("/addtrack")
    public String addTrackForm(Model model) {
        model.addAttribute("track", new Track());
        model.addAttribute("albums", albumRepository.findAll());
        return "addtrackform";
    }

    @PostMapping("/addtrack")
    public String addTrack(@ModelAttribute Track track) {
        trackRepository.save(track);
        return "redirect:/tracks";
    }

    @GetMapping("/addartist")
    public String addArtistForm(@RequestParam Long band_id, Model model) {
        Artist artist = new Artist();
        Optional<Band> optionalBand = bandRepository.findById(band_id);
        optionalBand.ifPresent(artist::setBand);
        model.addAttribute("artist", artist);
        return "addartistform";
    }

    @PostMapping("/addartist")
    public String addArtist(@ModelAttribute Artist artist, @RequestParam MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            artist.setPhoto(file.getBytes());
        }
        artistRepository.save(artist);
        return "redirect:/albums?band_id=" + artist.getBand().getId();
    }
    // EDIT objects:

    @GetMapping("/edittrack")
    public String editTrackForm(@RequestParam Long track_id, Model model) {
        model.addAttribute("track", trackRepository.findById(track_id));
        model.addAttribute("albums", albumRepository.findAll());
        return "addtrackform";
    }

    @GetMapping("/editalbum")
    public String editAlbumForm(@RequestParam Long album_id, Model model) {
        model.addAttribute("album", albumRepository.findById(album_id));
        model.addAttribute("bands", bandRepository.findAll());
        return "addalbumform";
    }

    @GetMapping("/editband")
    public String editBandForm(@RequestParam Long band_id, Model model) {
        model.addAttribute("band", bandRepository.findById(band_id));
        return "addbandform";
    }

    @GetMapping("/editartist")
    public String editArtistForm(@RequestParam Long artist_id, Model model) {
        Optional<Artist> optionalArtist = artistRepository.findById(artist_id);
        optionalArtist.ifPresent(artist -> model.addAttribute("artist", artist));
        return "addartistform";
    }

    // DELETE objects:

    @GetMapping("/deletetrack")
    public String deleteTrack(@RequestParam Long track_id) {
        trackRepository.deleteById(track_id);
        return "redirect:/tracks";
    }

    @GetMapping("/deletealbum")
    public String deleteAlbum(@RequestParam Long album_id) {
        albumRepository.deleteById(album_id);
        return "redirect:/albums";
    }

    @GetMapping("/deleteband")
    public String deleteBand(@RequestParam Long band_id) {
        bandRepository.deleteById(band_id);
        return "redirect:/bands";
    }

    @GetMapping("/deleteartist")
    public String deleteArtist(@RequestParam Long artist_id) {
        Optional<Artist> optionalArtist = artistRepository.findById(artist_id);
        artistRepository.deleteById(artist_id);
        return "redirect:/albums?band_id=" + optionalArtist.orElseThrow().getBand().getId();
    }


    // SHOW BLOB images:

    private ResponseEntity<byte[]> getResponseEntity(byte[] imageContent) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        if (imageContent == null) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/placeholder.png")) {
                if (inputStream != null) {
                    imageContent = inputStream.readAllBytes();
                }
            }
        }
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    @GetMapping("/artist_photo")
    public ResponseEntity<byte[]> getArtistPhoto(@RequestParam Long artist_id) throws IOException {
        Optional<Artist> artist = artistRepository.findById(artist_id);
        if (artist.isEmpty()) return null;
        byte[] imageContent = artist.get().getPhoto();

        return getResponseEntity(imageContent);
    }

    @GetMapping("/album_art")
    public ResponseEntity<byte[]> getAlbumArt(@RequestParam Long album_id) throws IOException {
        Optional<Album> album = albumRepository.findById(album_id);
        if (album.isEmpty()) return null;
        byte[] imageContent = album.get().getArt();

        return getResponseEntity(imageContent);
    }

    //todo Сделать добавление/редактирование артистов
    //todo Сделать нормальное оформление страниц
}
