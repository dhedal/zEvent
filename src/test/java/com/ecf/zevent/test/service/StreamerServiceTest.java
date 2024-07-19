package com.ecf.zevent.test.service;

import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.service.StreamerService;
import com.ecf.zevent.test.util.StreamerDataGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class StreamerServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(StreamerServiceTest.class);
    @Autowired
    private StreamerService streamerService;


    /**
     *
     */
    @Test
    public void testCreateAndSaveNewStreamer () {
        Streamer streamerCreated = this.streamerService.save(StreamerDataGenerator.newStreamer());
        this.assertNewStreamer(streamerCreated);
        try{
            Streamer other = this.streamerService.findById(streamerCreated.getId());
            assertTrue(streamerCreated.equals(other));
        } catch (Throwable ex) {
            fail(ex.toString());
        }
    }

    @Test
    public void testStreamerUpdated() {
        Streamer streamerCreated = this.streamerService.save(StreamerDataGenerator.newStreamer());
        this.assertNewStreamer(streamerCreated);

        final String firstName = "alexandre";
        streamerCreated.getPrivateData().setFirstName(firstName);
        this.streamerService.save(streamerCreated);

        try {
            Streamer streamerUpdated = this.streamerService.findById(streamerCreated.getId());
            assertNotNull(streamerUpdated.getUpdatedAt());
            assertEquals(firstName, streamerUpdated.getPrivateData().getFirstName());
        } catch (Throwable ex) {
            fail(ex.toString());
        }

    }

    @Test
    public void testDeleteStreamer() {
        final Streamer streamer = this.streamerService.save(StreamerDataGenerator.newStreamer());
        this.assertNewStreamer(streamer);

        assertThrows(ResourceNotFoundException.class, () -> {
            this.streamerService.delete(streamer.getId());
            Streamer streamerDeleted = this.streamerService.findById(streamer.getId());
            assertNull(streamerDeleted);
        });

    }

    @Test
    public void testListAll() {
        int streamerCount = this.streamerService.listAll().size();

        List<Streamer> streamers = StreamerDataGenerator.newStreamers(3);

        streamers.forEach(streamer -> this.streamerService.save(streamer));

        List<Streamer> list = this.streamerService.listAll();

        assertEquals(streamerCount + streamers.size(), list.size());
        assertTrue(list.containsAll(streamers));
    }

    @Test
    public void testPseudoList() {
        int streamerCount = this.streamerService.listAll().size();

        List<Streamer> streamers = StreamerDataGenerator.newStreamers(3);
        List<String> pseudoList = streamers.stream()
                .map(Streamer::getPublicData)
                .map(StreamerPublicData::getPseudo)
                .toList();
        streamers.forEach(streamer -> this.streamerService.save(streamer));

        List<String> pseudos = this.streamerService.getPseudoList();
        LOG.info(pseudoList.toString());
        LOG.info(pseudos.toString());
        assertNotNull(pseudos);
        assertFalse(pseudos.isEmpty());
        assertEquals(streamerCount + streamers.size(), pseudos.size());
        assertTrue(pseudos.containsAll(pseudoList));

    }

    @Test
    public void testFindStreamerByPseudo() {
        final Streamer streamer = this.streamerService.save(StreamerDataGenerator.newStreamer());
        this.assertNewStreamer(streamer);

        Streamer result = this.streamerService.findByPseudo(streamer.getPublicData().getPseudo());
        assertNotNull(result);
        assertEquals(streamer, result);
    }

    @Test
    public void testFindStreamerByUuid() {
        final Streamer streamer = this.streamerService.save(StreamerDataGenerator.newStreamer());
        this.assertNewStreamer(streamer);

        Streamer result = this.streamerService.findByUuid(streamer.getUuid());
        assertEquals(result, streamer);
    }

    public static void assertNewStreamer(Streamer streamer) {
        assertNotNull(streamer);
        assertNotNull(streamer.getId());
        assertNotNull(streamer.getUuid());
        assertNotNull(streamer.getCreatedAt());
    }
}
