package com.ecf.zevent.service;

import com.ecf.zevent.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class LiveServiceTest {
    @Autowired
    private StreamerService streamerService;
    @Autowired
    private LiveService liveService;

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

    private Live newLive(String label, ThematiqueType theme, LocalDateTime dateStart, Duration duration, Pegi pegi,
                         Streamer streamer) {
        Live live = new Live();
        live.setLabel(label);
        live.setTheme(theme);
        live.setDateStart(dateStart);
        live.setDateEnd(dateStart.plus(duration));
        live.setPegi(pegi);
        live.setStreamer(streamer);
        return live;
    }

    @Test
    public void testCreateAndSaveNewLive() {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube", Rule.STREAMER);
        streamer = this.streamerService.save(streamer);
        assertNotNull(streamer.getId());

        Live live = this.newLive("let'play", ThematiqueType.FPS,
                LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_16,
                streamer);
        live = this.liveService.save(live);
        assertNotNull(live.getId());

        try {
            Live liveExpected = this.liveService.findById(live.getId());
            assertNotNull(liveExpected);
            assertEquals(liveExpected.getTheme(), live.getTheme());
            assertEquals(Pegi.PEGI_16, live.getPegi());
            assertNotNull(liveExpected.getCreatedAt());
            assertNull(liveExpected.getUpdatedAt());

        } catch (Throwable ex) {
            fail(ex.toString());
        }
    }

    @Test
    public void testLiveUpdated() {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube", Rule.STREAMER);
        streamer = this.streamerService.save(streamer);
        assertNotNull(streamer.getId());

        Live live = this.newLive("let'play", ThematiqueType.MOBA,
                LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_16,
                streamer);
        live = this.liveService.save(live);
        assertNotNull(live.getId());

        try {
            LocalDateTime newDateStart = LocalDateTime.of(2024, Month.JUNE, 20, 20, 30);
            Duration duration = Duration.ofHours(2).plus(Duration.ofMinutes(30));
            LocalDateTime newDateEnd = newDateStart.plus(duration);
            live.setDateStart(newDateStart);
            live.setDateEnd(newDateEnd);
            this.liveService.save(live);

            Live liveUpdated = this.liveService.findById(live.getId());

            assertNotNull(liveUpdated);
            assertNotNull(liveUpdated.getCreatedAt());
            assertNotNull(liveUpdated.getUpdatedAt());
            assertEquals(newDateStart, liveUpdated.getDateStart());
            assertEquals(newDateStart.plus(duration), liveUpdated.getDateEnd());

        } catch (Throwable ex) {
            fail(ex.toString());
        }

    }

    @Test
    public void testDeleteLive() {
        Streamer streamer = this.newSTreamer("david", "hedgar", 45, "youtube", Rule.STREAMER);
        streamer = this.streamerService.save(streamer);
        assertNotNull(streamer.getId());

        Live live = this.newLive("let'play", ThematiqueType.RTS,
                LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_16,
                streamer);


        assertThrows(ResourceNotFoundException.class, () -> {
            Live liveSaved = this.liveService.save(live);
            assertNotNull(live.getId());
            this.liveService.delete(liveSaved.getId());
            this.liveService.findById(liveSaved.getId());
        });
    }

    @Test
    public void testLiveAll() {
        int  liveCount = this.liveService.listAll().size();

        Streamer s1 = this.streamerService.save(this.newSTreamer("anne-marie", "thiam", 67, "twitch", Rule.STREAMER));
        Streamer s2 = this.streamerService.save(this.newSTreamer("sarah", "hedgar", 39, "drama", Rule.STREAMER));
        Streamer s3 = this.streamerService.save(this.newSTreamer("david", "hedgar", 45, "youtube", Rule.ADMIN));

        List<Live> lives = List.of(
                this.newLive("let'play", ThematiqueType.JEUX_D_AVENTURE_ET_DE_ROLE,
                    LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                    Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                    Pegi.PEGI_3,
                    s1),
                this.newLive("let'play", ThematiqueType.JEUX_DE_COURSE,
                    LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                    Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                    Pegi.PEGI_16,
                    s2),
                 this.newLive("let'play", ThematiqueType.SURVIVAL_HORROR,
                    LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                    Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                    Pegi.PEGI_18,
                    s3)
        );

        lives.forEach(live -> this.liveService.save(live));

        assertEquals(liveCount + lives.size(), this.liveService.listAll().size());

    }

    @Test
    public void testFindLivesByDate() {
        Streamer streamer = this.newSTreamer("alexandre", "hedgar", 45, "youtube", Rule.STREAMER);
        this.streamerService.save(streamer);

        Live live = this.newLive("let'play", ThematiqueType.JEUX_DE_CARTES_ET_DE_STRATEGIE,
                LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_3,
                streamer);
        this.liveService.save(live);

        List<Live> results = this.liveService.findLivesByDate(live.getDateStart().toLocalDate().plusDays(1));
        assertNotNull(results);
        assertTrue(results.isEmpty());

        results = this.liveService.findLivesByDate(live.getDateStart().toLocalDate());
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.contains(live));

        System.out.println(this.liveService.findLivesByStreamer(streamer));
        System.out.println(results);
    }

    @Test
    public void testFindLivesByStreamer() {
        Streamer streamer = this.newSTreamer("alexandre", "hedgar", 45, "youtube", Rule.STREAMER);
        this.streamerService.save(streamer);

        Live live = this.newLive("let'play", ThematiqueType.MMORPG,
                LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_3,
                streamer);
        this.liveService.save(live);

        List<Live> results = this.liveService.findLivesByStreamer(streamer);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.contains(live));
    }

    @Test
    public void testFindLivesByTheme() {
        Streamer streamer = this.newSTreamer("alexandre", "hedgar", 45, "youtube", Rule.STREAMER);
        this.streamerService.save(streamer);

        List<Live> livesFPS = List.of(
                this.newLive("Call of duty", ThematiqueType.RTS,
                        LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                        Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                        Pegi.PEGI_16,
                        streamer),
                this.newLive("Cyber punk 2077", ThematiqueType.RTS,
                        LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                        Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                        Pegi.PEGI_16,
                        streamer),
                this.newLive("Doom", ThematiqueType.RTS,
                        LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                        Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                        Pegi.PEGI_12,
                        streamer)
        );
        livesFPS.forEach(live -> this.liveService.save(live));

        List<Live> results = this.liveService.findLivesByTheme(ThematiqueType.RTS);
        assertNotNull(results);
        results.forEach(live -> {
            assertTrue(livesFPS.contains(live));
        });
        
    }


}
