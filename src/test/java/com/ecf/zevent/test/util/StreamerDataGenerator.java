package com.ecf.zevent.test.util;

import com.ecf.zevent.model.*;
import com.ecf.zevent.test.utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class StreamerDataGenerator {

    private static StreamerDataGenerator generator;
    private static Random random;
    private  List<String> firstNames;
    private List<String> lastNames;

    private StreamerDataGenerator() {
        this.init();
    }


    private void init() {
        this.random = new Random();
        this.firstNames = List.of(
                "tony","donald", "bruce", "hank", "janet", "steve",
                "clint", "wanda", "pietro", "jacques", "hercules", "dane", "t'challa"
        );

        this.lastNames = List.of(
                "barton","pym", "van dyne", "stark", "blake", "rogers",
                "whitman", "maximoff", "panhellenios", "duquesne", "banner", "hedgar", "diop"
        );
    }

    private static StreamerDataGenerator instance() {
        if(generator == null) generator = new StreamerDataGenerator();
        return generator;
    }

    private LocalDate randomBirthDate() {
        return DateUtils.randomBirthDate();
    }

    private String randomFirstName() {
        return this.firstNames.get(this.random.nextInt(this.firstNames.size()));
    }

    private String randomLastName() {
        return this.lastNames.get(this.random.nextInt(this.lastNames.size()));
    }

    private String randomPseudo(String firstName, String lastName) {
        return firstName + "-" + lastName + "-" + this.random.nextInt(100000);
    }

    private String formatEmail(String pseudo) {
        return pseudo + "@zevent.fr";
    }

    public static Streamer generate() {
        StreamerPrivateData privateData = new StreamerPrivateData();
        privateData.setFirstName(instance().randomFirstName());
        privateData.setLastName(instance().randomLastName());

        StreamerPublicData publicData = new StreamerPublicData();
        publicData.setPseudo(instance().randomPseudo(privateData.getFirstName(), privateData.getLastName()));
        publicData.setBirthDate(instance().randomBirthDate());
        publicData.setChannel("youtube");

        AuthenticationData authenticationData = new AuthenticationData();
        authenticationData.setEmail(instance().formatEmail(publicData.getPseudo()));
        authenticationData.setPassword("PASSWORD_TEMP");

        Streamer streamer = new Streamer();
        streamer.setRule(Rule.STREAMER);
        streamer.setStatus(StreamerStatus.REGISTRATION_REQUEST);
        streamer.setPrivateData(privateData);
        streamer.setPublicData(publicData);
        streamer.setAuthenticationData(authenticationData);

        return streamer;
    }

    public static List<Streamer> generateList(int size) {
        List<Streamer> streamers = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            streamers.add(generate());
        }
        return streamers;
    }

}
