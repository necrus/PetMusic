package com.example.petmusic.controller;

import com.example.petmusic.entity.Album;
import com.example.petmusic.entity.Band;
import com.example.petmusic.entity.Track;
import com.example.petmusic.repository.AlbumRepository;
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
            album.ifPresent(value -> model.addAttribute("album", value));
        } else {
            tracks = trackRepository.findAll();
        }
        model.addAttribute("tracks", tracks);
        return "tracks";
    }

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
    public String addAlbum(@ModelAttribute Album album, @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
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

    //todo Сделать обложки альбомов
    //todo Сделать нормальное оформление страниц

    @GetMapping("/album_art")
    public ResponseEntity<byte[]> getAlbumArt(@RequestParam Long album_id) {
        Optional<Album> album = albumRepository.findById(album_id);
        if (album.isEmpty()) return null;
        byte[] imageContent = album.get().getArt();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
//                                                 @RequestParam("file") MultipartFile file) {
//        if (!file.isEmpty()) {
//            try {
////                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(".//upload//"
////                + name + "-uploaded.txt")));
////                stream.write(bytes);
////                stream.close();
//                String basePathOfClass = getClass()
//                        .getProtectionDomain().getCodeSource().getLocation().getFile();
//
//
//
//                Files.write(Path.of(basePathOfClass + file.getOriginalFilename()), file.getBytes());
////                storageService.store(file);
//                return "Вы удачно загрузили " + name + " в " + name + "-uploaded !";
//            } catch (Exception e) {
//                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
//            }
//        } else {
//            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
//        }
//    }

}
