package com.ecf.zevent.service;

import com.ecf.zevent.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalDate;
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
        streamer.setPseudo(firstName+"-"+lastName);
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

    @Test
    public void testFindLiveByDateAndThematiqueAndStreamer() {
        Streamer streamer = this.newSTreamer("Julien", "Chièze", 45, "youtube", Rule.STREAMER);
        Streamer streamer2 = this.newSTreamer("X", "Serve", 45, "youtube", Rule.STREAMER);
        this.streamerService.save(streamer);
        this.streamerService.save(streamer2);

        Live live1 = this.newLive(
                "Elden ring",
                ThematiqueType.JEUX_D_AVENTURE_ET_DE_ROLE,
                LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_16,
                streamer);
        Live live1b = this.newLive(
                "Doom 4",
                ThematiqueType.FPS,
                LocalDateTime.of(2024, Month.JUNE, 15, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_16,
                streamer2);
        this.liveService.save(live1);
        this.liveService.save(live1b);


        Live live2 =  this.newLive(
                "Cyber punk 2077",
                ThematiqueType.FPS,
                LocalDateTime.of(2024, Month.JUNE, 16, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_16,
                streamer);
        this.liveService.save(live2);

        Live live3 = this.newLive(
                "Tekken 8",
                ThematiqueType.JEUX_DE_COMBAT,
                LocalDateTime.of(2024, Month.JUNE, 17, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_12,
                streamer);
        Live live3b = this.newLive(
                "Zelda",
                ThematiqueType.JEUX_D_AVENTURE_ET_DE_ROLE,
                LocalDateTime.of(2024, Month.JUNE, 17, 20, 30),
                Duration.ofHours(2).plus(Duration.ofMinutes(30)),
                Pegi.PEGI_12,
                streamer);
        this.liveService.save(live3);
        this.liveService.save(live3b);

        // TEST LA DATE
        LocalDate localDate = LocalDate.of(2024, Month.JUNE, 15);
        List<Live> result = this.liveService.findLivesBy(localDate, null, null);
        assertNotNull(result);
        assertTrue(result.contains(live1));
        assertTrue(result.contains(live1b));
        assertFalse(result.contains(live2));
        assertFalse(result.contains(live3));
        assertFalse(result.contains(live3b));

        // TEST LA THEMATIQUE
        result = this.liveService.findLivesBy(null, ThematiqueType.JEUX_DE_COMBAT, null);
        assertNotNull(result);
        assertTrue(result.contains(live3));
        assertFalse(result.contains(live1));
        assertFalse(result.contains(live1b));
        assertFalse(result.contains(live2));
        assertFalse(result.contains(live3b));

        // TEST LE PSEUDO
        result = this.liveService.findLivesBy(null, null, streamer.getPseudo());
        assertNotNull(result);
        assertTrue(result.contains(live3));
        assertTrue(result.contains(live1));
        assertTrue(result.contains(live2));
        assertTrue(result.contains(live3b));
        assertFalse(result.contains(live1b));


        // TEST LA DATE ET LA THEMATIQUE
        result = this.liveService.findLivesBy(localDate, ThematiqueType.FPS, null);
        assertNotNull(result);
        assertTrue(result.contains(live1b));
        assertFalse(result.contains(live1));
        assertFalse(result.contains(live2));
        assertFalse(result.contains(live3));
        assertFalse(result.contains(live3b));

        // TEST LA DATE ET LE PSEUDO
        result = this.liveService.findLivesBy(localDate, null, streamer2.getPseudo());
        assertNotNull(result);
        assertTrue(result.contains(live1b));
        assertFalse(result.contains(live1));
        assertFalse(result.contains(live2));
        assertFalse(result.contains(live3));
        assertFalse(result.contains(live3b));

        // TEST LA THEMATIQUE ET LE PSEUDO
        result = this.liveService.findLivesBy(null, ThematiqueType.JEUX_D_AVENTURE_ET_DE_ROLE, streamer.getPseudo());
        assertNotNull(result);
        assertTrue(result.contains(live1));
        assertTrue(result.contains(live3b));
        assertFalse(result.contains(live1b));
        assertFalse(result.contains(live2));
        assertFalse(result.contains(live3));

        // TEST LA DATE, LA THEMATIQUE ET LE PSEUDO 1
        result = this.liveService.findLivesBy(localDate, ThematiqueType.FPS, streamer2.getPseudo());
        assertNotNull(result);
        assertTrue(result.contains(live1b));
        assertFalse(result.contains(live1));
        assertFalse(result.contains(live3b));
        assertFalse(result.contains(live2));
        assertFalse(result.contains(live3));

        // TEST LA DATE, LA THEMATIQUE ET LE PSEUDO 2
        result = this.liveService.findLivesBy(localDate, ThematiqueType.JEUX_D_AVENTURE_ET_DE_ROLE, streamer2.getPseudo());
        assertNotNull(result);
        assertFalse(result.contains(live1b));
        assertFalse(result.contains(live1));
        assertFalse(result.contains(live3b));
        assertFalse(result.contains(live2));
        assertFalse(result.contains(live3));
    }

    @Test
    public void testFindLivesByDateStartGreaterThanEqual() {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Live> result = this.liveService.findByDateStartGreaterThanEqual(localDateTime);
        result.forEach(System.out::println);
        assertNotNull(result);
        assertTrue(result.size() >= 1);
        result.forEach(live -> assertTrue(live.getDateStart().isAfter(localDateTime)));

        List<Live> lives = this.liveService.listAll();
        assertNotNull(lives);
        assertTrue(lives.size() >= 1);
        int  nbLivesSupLocalDateTime = (int) lives.stream()
                .filter(live -> live.getDateStart().isAfter(localDateTime))
                .count();
        assertTrue(nbLivesSupLocalDateTime == result.size());
    }

}
