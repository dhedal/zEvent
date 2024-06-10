package com.ecf.zevent.service;

import com.ecf.zevent.model.Live;
import com.ecf.zevent.repository.LiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveService extends AbstractService<LiveRepository, Live> {

    @Autowired
    public LiveService(LiveRepository repository) {
        super(repository);
    }
}
