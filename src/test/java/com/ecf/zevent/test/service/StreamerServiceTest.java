package com.ecf.zevent.test.service;

import com.ecf.zevent.dto.StreamerDTO;
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
    public void testCreateNewStreamer () {
        Streamer streamerCreated = this.streamerService.create(StreamerDataGenerator.newStreamerDTO());
        this.assertNewStreamer(streamerCreated);
        try{
            Streamer other = this.streamerService.findById(streamerCreated.getId());
            assertTrue(streamerCreated.equals(other));

            assertNull(this.streamerService.create(null));
            assertNull(this.streamerService.create(StreamerDTO.toStreamerDTO(other)));
        } catch (Throwable ex) {
            fail(ex.toString());
        }


    }

    @Test
    public void testUpdateStreamer() {
        Streamer streamerCreated = this.streamerService.create(StreamerDataGenerator.newStreamerDTO());
        this.assertNewStreamer(streamerCreated);
        try {
            final String firstName = "alexandre";
            streamerCreated.getPrivateData().setFirstName(firstName);
            this.streamerService.update(StreamerDTO.toStreamerDTO(streamerCreated));
            Streamer streamerUpdated = this.streamerService.findById(streamerCreated.getId());
            assertNotNull(streamerUpdated.getUpdatedAt());
            assertEquals(firstName, streamerUpdated.getPrivateData().getFirstName());

            assertNull(this.streamerService.update(null));
            streamerUpdated.setUuid(null);
            assertNull(this.streamerService.update(StreamerDTO.toStreamerDTO(streamerUpdated)));
            streamerUpdated.setUuid("");
            assertNull(this.streamerService.update(StreamerDTO.toStreamerDTO(streamerUpdated)));
        } catch (Throwable ex) {
            fail(ex.toString());
        }

    }

    @Test
    public void testDeleteStreamer() {
        final Streamer streamer = this.streamerService.create(StreamerDataGenerator.newStreamerDTO());
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

        List<StreamerDTO> streamersDTOs = StreamerDataGenerator.newStreamerDTOs(3);

        streamersDTOs.forEach(streamerDTO -> this.streamerService.create(streamerDTO));

        List<Streamer> list = this.streamerService.listAll();

        assertEquals(streamerCount + streamersDTOs.size(), list.size());
        assertTrue(list.containsAll(streamersDTOs));
    }

    @Test
    public void testPseudoList() {
        int streamerCount = this.streamerService.listAll().size();

        List<StreamerDTO> streamersDTOs = StreamerDataGenerator.newStreamerDTOs(3);
        List<String> pseudoList = streamersDTOs
                .stream()
                .map(streamerDTO -> streamerDTO.getPseudo())
                .toList();
        streamersDTOs.forEach(dto -> this.streamerService.create(dto));

        List<String> pseudos = this.streamerService.getPseudoList();
        LOG.info(pseudoList.toString());
        LOG.info(pseudos.toString());
        assertNotNull(pseudos);
        assertFalse(pseudos.isEmpty());
        assertEquals(streamerCount + streamersDTOs.size(), pseudos.size());
        assertTrue(pseudos.containsAll(pseudoList));

    }

    @Test
    public void testFindStreamerByPseudo() {
        final Streamer streamer = this.streamerService.create(StreamerDataGenerator.newStreamerDTO());
        this.assertNewStreamer(streamer);

        Streamer result = this.streamerService.findByPseudo(streamer.getPublicData().getPseudo());
        assertNotNull(result);
        assertEquals(streamer, result);
    }

    @Test
    public void testFindStreamerByUuid() {
        final Streamer streamer = this.streamerService.create(StreamerDataGenerator.newStreamerDTO());
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
