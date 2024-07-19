package com.ecf.zevent.model.interfaces;

import java.time.LocalDateTime;

public interface IEntity {

    public Object getId();
    public void setUuid(String uuid);
    public void setCreatedAt(LocalDateTime createdAt);
    public void setUpdatedAt(LocalDateTime updatedAt);
}
