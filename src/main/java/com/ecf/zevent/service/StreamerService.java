package com.ecf.zevent.service;

import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.repository.StreamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamerService extends AbstractService<StreamerRepository, Streamer>{

    @Autowired
    public StreamerService(StreamerRepository repository) {
        super(repository);
    }


}
