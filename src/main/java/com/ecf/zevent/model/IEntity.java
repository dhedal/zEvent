package com.ecf.zevent.model;

import java.time.LocalDateTime;

public interface IEntity {

    public Object getId();
    public String getUuid();
    public void setUuid(String uuid);
    public void setCreatedAt(LocalDateTime updatedAt);
    public void setUpdatedAt(LocalDateTime updatedAt);
}
