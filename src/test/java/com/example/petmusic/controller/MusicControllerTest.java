package com.example.petmusic.controller;

import com.example.petmusic.entity.Album;
import com.example.petmusic.entity.Band;
import com.example.petmusic.entity.Track;
import com.example.petmusic.repository.AlbumRepository;
import com.example.petmusic.repository.BandRepository;
import com.example.petmusic.repository.TrackRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc//(secure = false)
//@ExtendWith(SpringExtension.class)
//@WebMvcTest//(MusicController.class)
//@ContextConfiguration(classes = {WebSecurityConfig.class})
//@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
class MusicControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrackRepository trackRepository;
    @MockBean
    AlbumRepository albumRepository;
    @MockBean
    BandRepository bandRepository;

    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"}, authorities = {"ALL"})
    void index() throws Exception {
        when(trackRepository.findAll()).thenReturn(Arrays.asList(
                new Track(1L, "track1", Time.valueOf("01:01:01"), 1L),
                new Track(2L, "track2", Time.valueOf("02:02:02"), 2L)
        ));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("track1")));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void bandsAll() throws Exception {
        when(bandRepository.findAll()).thenReturn(Arrays.asList(
                new Band(1L, "country1", "band1"),
                new Band(2L, "country2", "band2")
        ));

        mockMvc.perform(get("/bands"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("band1")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void albumsAll() throws Exception {
        when(albumRepository.findAll()).thenReturn(Arrays.asList(
                new Album(1L, "album1", 1991, "", 1L),
                new Album(2L, "album2", 1992, "", 2L)
        ));

        mockMvc.perform(get("/albums"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("album1")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void albumsWithParameters() throws Exception {
        when(albumRepository.findByBandId(1L)).thenReturn(Arrays.asList(
                new Album(1L, "album1", 1991, "", 1L),
                new Album(2L, "album2", 1992, "", 1L)
        ));

        mockMvc.perform(get("/albums?band_id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("album1")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void tracksAll() throws Exception {
        when(trackRepository.findAll()).thenReturn(Arrays.asList(
                new Track(1L, "track1", Time.valueOf("01:01:01"), 1L),
                new Track(2L, "track2", Time.valueOf("02:02:02"), 2L)
        ));

        mockMvc.perform(get("/tracks"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("track1")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void tracksWithParameters() throws Exception {
        when(trackRepository.findByAlbumId(1L)).thenReturn(Arrays.asList(
                new Track(1L, "track1", Time.valueOf("01:01:01"), 1L),
                new Track(2L, "track2", Time.valueOf("02:02:02"), 1L)
        ));
        when(albumRepository.findById(1L)).thenReturn(Optional.of(
                new Album(1L, "album1", 1991, "", 1L)
        ));

        mockMvc.perform(get("/tracks?album_id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("track1")))
                .andExpect(content().string(containsString("album1")));
    }

    @Test
    void addBandForm() throws Exception {
        mockMvc.perform(get("/addband"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Добавление группы")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addBand() throws Exception {
        Band band = new Band(null, "test", "test");
        mockMvc.perform(post("/addband")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("bandName=test&country=test"))
                .andExpect(status().is3xxRedirection());
        verify(bandRepository).save(Mockito.eq(band));

    }

    @Test
    void addAlbumForm() throws Exception {
        mockMvc.perform(get("/addalbum"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Добавление альбома")));
    }

    @Test
    void addAlbum() throws Exception {
        Album album = new Album(null, "test", 1990, "", 1L);
        mockMvc.perform(post("/addalbum")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("albumName=test&year=1990&albumArt=&bandId=1"))
                .andExpect(status().is3xxRedirection());
        verify(albumRepository).save(Mockito.eq(album));
    }

    @Test
    void addTrackForm() throws Exception {
        mockMvc.perform(get("/addtrack"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Добавление трека")));
    }

    @Test
    void addTrack() throws Exception {
        Track track = new Track(null, "track1", Time.valueOf("01:01:01"), 1L);
        mockMvc.perform(post("/addtrack")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("trackName=track1&length=01:01:01&albumId=1"))
                .andExpect(status().is3xxRedirection());
        verify(trackRepository).save(Mockito.eq(track));
    }
}