package com.ecf.zevent.service;


import com.ecf.zevent.model.Streamer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StreamerServiceTest {

    @Autowired
    private StreamerService streamerService;

    @Test
    public void testCreateAndSaveNewStreamer () {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube");

        Streamer streamerCreated = this.streamerService.save(streamer);

        try{
            Streamer streamerExpected = this.streamerService.findById(streamerCreated.getId());
            assertNotNull(streamerExpected);
            assertEquals(streamerExpected.getId(), streamerCreated.getId());
            assertEquals(streamerExpected.getFirstName(), streamerCreated.getFirstName());
            assertEquals(streamerExpected.getLastName(), streamerCreated.getLastName());

        } catch (Throwable ex) {
            fail(ex.toString());
        }
    }

    @Test
    public void testStreamerUpdated() {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube");

        Streamer streamerCreated = this.streamerService.save(streamer);

        streamerCreated.setFirstName("alexandre");
        this.streamerService.save(streamer);

        try{
            Streamer streamerExpected = this.streamerService.findById(streamerCreated.getId());
            assertNotNull(streamerExpected);
            assertEquals(streamerExpected.getId(), streamerCreated.getId());
            assertEquals(streamerExpected.getLastName(), streamerCreated.getLastName());
            assertEquals("alexandre", streamerExpected.getFirstName());
        } catch (Throwable ex) {
            fail(ex.toString());
        }
    }

    @Test
    public void testDeleteStreamer() {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube");
        final Streamer streamerSaved = this.streamerService.save(streamer);

        assertThrows(ResourceNotFoundException.class, () -> {
            this.streamerService.delete(streamerSaved.getId());
            Streamer streamerDeleted = this.streamerService.findById(streamerSaved.getId());
            assertNull(streamerDeleted);
        });

    }

    @Test
    public void testListAll() {
        List<Streamer> streamers = List.of(
                this.newSTreamer("anne-marie", "thiam", 67, "twitch"),
                this.newSTreamer("sarah", "hedgar", 39, "drama"),
                this.newSTreamer("david", "hedgar", 45, "youtube")
        );

        streamers.forEach(streamer -> this.streamerService.save(streamer));

        List<Streamer> streamersSaved = this.streamerService.listAll();

        assertEquals(streamers.size(), streamersSaved.size());

        streamersSaved.forEach(System.out::println);

    }

    private Streamer newSTreamer(String firstName, String lastName, int age, String chaine) {
        Streamer streamer = new Streamer();
        streamer.setFirstName(firstName);
        streamer.setLastName(lastName);
        streamer.setMatricule(UUID.randomUUID().toString());
        streamer.setEmail(firstName + lastName + Math.random() + "@email.com");
        streamer.setAge(age);
        streamer.setChaine(chaine);
        streamer.setCreatedAt(LocalDateTime.now());
        return streamer;
    }

}
