package com.ecf.zevent.service;


import com.ecf.zevent.model.Rule;
import com.ecf.zevent.model.Streamer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class StreamerServiceTest {

    @Autowired
    private StreamerService streamerService;

    @Test
    public void testCreateAndSaveNewStreamer () {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube", Rule.STREAMER);

        Streamer streamerCreated = this.streamerService.save(streamer);

        try{
            Streamer streamerExpected = this.streamerService.findById(streamerCreated.getId());
            assertNotNull(streamerExpected);
            assertNotNull(streamerExpected.getCreatedAt());
            assertNull(streamerCreated.getUpdatedAt());
            assertEquals(streamerExpected.getId(), streamerCreated.getId());
            assertEquals(streamerExpected.getFirstName(), streamerCreated.getFirstName());
            assertEquals(streamerExpected.getLastName(), streamerCreated.getLastName());
            assertEquals(Rule.STREAMER, streamerExpected.getRule());
        } catch (Throwable ex) {
            fail(ex.toString());
        }
    }

    @Test
    public void testStreamerUpdated() {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube", Rule.STREAMER);

        Streamer streamerCreated = this.streamerService.save(streamer);

        streamerCreated.setFirstName("alexandre");
        this.streamerService.save(streamer);

        try{
            Streamer streamerExpected = this.streamerService.findById(streamerCreated.getId());
            assertNotNull(streamerExpected);
            assertNotNull(streamerExpected.getCreatedAt());
            assertNotNull(streamerCreated.getUpdatedAt());
            assertEquals(streamerExpected.getId(), streamerCreated.getId());
            assertEquals(streamerExpected.getLastName(), streamerCreated.getLastName());
            assertEquals(Rule.STREAMER, streamerExpected.getRule());
            assertEquals("alexandre", streamerExpected.getFirstName());
        } catch (Throwable ex) {
            fail(ex.toString());
        }
    }

    @Test
    public void testDeleteStreamer() {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube", Rule.STREAMER);
        final Streamer streamerSaved = this.streamerService.save(streamer);

        assertThrows(ResourceNotFoundException.class, () -> {
            this.streamerService.delete(streamerSaved.getId());
            Streamer streamerDeleted = this.streamerService.findById(streamerSaved.getId());
            assertNull(streamerDeleted);
        });

    }

    @Test
    public void testListAll() {
        int streamerCount = this.streamerService.listAll().size();

        List<Streamer> streamers = List.of(
                this.newSTreamer("anne-marie", "thiam", 67, "twitch", Rule.USER),
                this.newSTreamer("sarah", "hedgar", 39, "drama", Rule.STREAMER),
                this.newSTreamer("david", "hedgar", 45, "youtube", Rule.ADMIN)
        );

        streamers.forEach(streamer -> this.streamerService.save(streamer));

        assertEquals(streamerCount + streamers.size(), this.streamerService.listAll().size());


    }

    private Streamer newSTreamer(String firstName, String lastName, int age, String chaine, Rule rule) {
        Streamer streamer = new Streamer();
        streamer.setFirstName(firstName);
        streamer.setLastName(lastName);
        streamer.setMatricule(UUID.randomUUID().toString());
        streamer.setEmail(firstName + lastName + Math.random() + "@email.com");
        streamer.setAge(age);
        streamer.setChaine(chaine);
        streamer.setRule(rule);
        return streamer;
    }

}
