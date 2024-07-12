package com.ecf.zevent.test.service;


import com.ecf.zevent.model.Rule;
import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.service.StreamerService;
import com.ecf.zevent.test.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class StreamerServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(StreamerServiceTest.class);
    @Autowired
    private StreamerService streamerService;
    private static Random random = new Random();

    public static Streamer newSTreamer(String firstName, String lastName, String chaine, Rule rule) {

        Streamer streamer = new Streamer();
        streamer.setUuid(UUID.randomUUID());
        streamer.setPseudo(firstName + "-" + lastName + "-" + random.nextInt(100000));
        streamer.setFirstName(firstName);
        streamer.setLastName(lastName);
        streamer.setEmail(streamer.getPseudo() + "@email.com");
        streamer.setBirthDate(DateUtils.randomBirthDate());
        streamer.setChaine(chaine);
        streamer.setRule(rule);
        return streamer;
    }

    /**
     *
     */
    @Test
    public void testCreateAndSaveNewStreamer () {
        Streamer streamer = this.newSTreamer("david", "hedgar", "youtube", Rule.STREAMER);

        Streamer streamerCreated = this.streamerService.save(streamer);

        try{
            Streamer streamerExpected = this.streamerService.findById(streamerCreated.getId());
            assertNotNull(streamerExpected);
            assertNotNull(streamerExpected.getUuid());
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
        Streamer streamer = newSTreamer("david", "hedgar", "youtube", Rule.STREAMER);

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
        Streamer streamer = this.newSTreamer("david", "hedgar", "youtube", Rule.STREAMER);
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
                this.newSTreamer("anne-marie", "thiam", "twitch", Rule.USER),
                this.newSTreamer("sarah", "hedgar", "drama", Rule.STREAMER),
                this.newSTreamer("david", "hedgar", "youtube", Rule.ADMIN)
        );

        streamers.forEach(streamer -> this.streamerService.save(streamer));

        assertEquals(streamerCount + streamers.size(), this.streamerService.listAll().size());


    }

    @Test
    public void testPseudoList() {
        int streamerCount = this.streamerService.listAll().size();

        List<Streamer> streamers = List.of(
                this.newSTreamer("manu", "chao", "twitch", Rule.USER),
                this.newSTreamer("lucie", "herman", "drama", Rule.STREAMER),
                this.newSTreamer("rodrigue", "rodriguer", "youtube", Rule.ADMIN)
        );

        streamers.forEach(streamer -> this.streamerService.save(streamer));

        List<String> pseudos = this.streamerService.getPseudoList();
        assertNotNull(pseudos);
        assertEquals(streamerCount + streamers.size(), pseudos.size());

        streamers.forEach(streamer -> assertTrue(pseudos.contains(streamer.getPseudo())));
    }

    @Test
    public void testFindStreamerByPseudo() {
        Streamer streamer = this.newSTreamer("zar", "toch", "youtube", Rule.STREAMER);
        this.streamerService.save(streamer);
        Streamer result = this.streamerService.findByPseudo(streamer.getPseudo());
        assertNotNull(result);
        assertEquals(streamer, result);
    }




}
