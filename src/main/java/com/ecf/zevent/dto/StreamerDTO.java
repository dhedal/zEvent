package com.ecf.zevent.dto;

import com.ecf.zevent.model.Rule;
import com.ecf.zevent.model.Streamer;

import java.util.List;
import java.util.Objects;

public class StreamerDTO {

    private String pseudo;
    private int age;
    private String chaine;
    private Rule rule;

    private StreamerDTO(){}
    private StreamerDTO(Streamer streamer) {
        this.pseudo = streamer.getPseudo();
        this.age = streamer.getAge();
        this.chaine = streamer.getChaine();
        this.rule = streamer.getRule();
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getChaine() {
        return chaine;
    }

    public void setChaine(String chaine) {
        this.chaine = chaine;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamerDTO{");
        sb.append("pseudo='").append(pseudo).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append(", chaine='").append(chaine).append('\'');
        sb.append(", rule=").append(rule);
        sb.append('}');
        return sb.toString();
    }

    public static StreamerDTO parse(Streamer streamer) {
        if(streamer == null) return null;
        return new StreamerDTO(streamer);
    }

    public static List<StreamerDTO> parse(List<Streamer> streamers) {
        return streamers.stream()
                .filter(Objects::nonNull)
                .map(StreamerDTO::parse)
                .toList();
    }

    public static StreamerDTO getEmpty() { return new StreamerDTO();}
}
