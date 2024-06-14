package com.ecf.zevent.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Live implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 100)
    private String label;
    @Column(nullable = false, length = 50)
    private String theme;
    @Column(nullable = false)
    private LocalDateTime dateStart;
    @Column(nullable = false)
    private LocalDateTime dateEnd;
    @Column(nullable = false)
    private Pegi pegi;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="streamer_id", nullable = false)
    private Streamer streamer;

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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

    public Streamer getStreamer() { return this.streamer;}

    public void setStreamer(Streamer streamer) {
        this.streamer = streamer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Live{");
        sb.append("id=").append(id);
        sb.append(", label='").append(label).append('\'');
        sb.append(", theme='").append(theme).append('\'');
        sb.append(", dateStart=").append(dateStart);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append(", pegi=").append(pegi);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }
}
