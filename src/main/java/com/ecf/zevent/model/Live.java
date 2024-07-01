package com.ecf.zevent.model;

import com.ecf.zevent.converter.ListThematiqueTypeAttributeConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Live implements IEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private UUID uuid;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 255)
    private String description;
    @Column(nullable = false)
    private List<ThematiqueType> themes;
    @Column(nullable = false)
    private LocalDateTime dateStart;
    @Column(nullable = false)
    private LocalDateTime dateEnd;
    @Column(nullable = false)
    private Pegi pegi;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="streamer_id", nullable = false)
    private Streamer streamer;

    public Long getId() {
        return id;
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

    public void setThemes(List<ThematiqueType> themes) {
        this.themes = themes;
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
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", themes='").append(themes).append('\'');
        sb.append(", dateStart=").append(dateStart);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append(", pegi=").append(pegi);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(31);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(null == obj || !(obj instanceof Live)) return false;
        Live that = (Live) obj;
        if(this.id == null && that.id == null) {
            return Objects.equals(this.uuid, that.uuid) &&
                    Objects.equals(this.title, that.title) &&
                    Objects.equals(this.description, that.description) &&
                    Objects.equals(this.dateStart, that.dateStart) &&
                    Objects.equals(this.dateEnd, that.dateEnd) &&
                    Objects.equals(this.createdAt, that.createdAt) &&
                    Objects.equals(this.updatedAt, that.updatedAt);
        }

        return Objects.equals(this.id, that.id);

    }
}
