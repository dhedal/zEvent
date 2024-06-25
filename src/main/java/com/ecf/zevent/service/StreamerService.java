package com.ecf.zevent.service;

import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.repository.StreamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamerService extends AbstractService<StreamerRepository, Streamer>{

    @Autowired
    public StreamerService(StreamerRepository repository) {
        super(repository);
    }


    public List<String> getPseudoList(){
        List<Streamer> streamers = this.listAll();
        return streamers.stream().map(Streamer::getPseudo)
                .toList();
    }
}
