package com.ecf.zevent.dto;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.model.Pegi;
import com.ecf.zevent.model.ThematiqueType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LiveDTO {

    private UUID uuid;
    private String title;
    private String description;
    private List<ThematiqueType> themes;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Pegi pegi;
    private String streamerPseudo;

    private LiveDTO(){}
    private LiveDTO(Live live) {
        this.uuid = live.getUuid();
        this.title = live.getTitle();
        this.description = live.getDescription();
        this.themes = live.getThemes();
        this.pegi = live.getPegi();
        this.dateStart = live.getDateStart();
        this.dateEnd = live.getDateEnd();
        this.streamerPseudo = live.getStreamer().getPseudo();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ThematiqueType> getThemes() {
        return themes;
    }

    public void setTheme(List<ThematiqueType> themes) {
        this.themes = themes;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Pegi getPegi() {
        return pegi;
    }

    public void setPegi(Pegi pegi) {
        this.pegi = pegi;
    }

    public String getStreamerPseudo() {
        return streamerPseudo;
    }

    public void setStreamerPseudo(String streamerPseudo) {
        this.streamerPseudo = streamerPseudo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LiveDTO{");
        sb.append("uuid=").append(uuid);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", themes=").append(themes);
        sb.append(", dateStart=").append(dateStart);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append(", pegi=").append(pegi);
        sb.append(", streamerPseudo='").append(streamerPseudo).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static LiveDTO parse(Live live) {
        if(live == null) return null;
        return new LiveDTO(live);
    }

    public static List<LiveDTO> parse(List<Live> lives) {
        return lives.stream()
                .filter(Objects::nonNull)
                .map(LiveDTO::parse)
                .toList();
    }

    public static LiveDTO getEmpty(){
        return new LiveDTO();
    }
}
