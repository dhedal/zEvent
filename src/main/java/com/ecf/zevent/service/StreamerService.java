package com.ecf.zevent.service;

import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.repository.StreamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StreamerService extends AbstractService<StreamerRepository, Streamer>{

    @Autowired
    public StreamerService(StreamerRepository repository) {
        super(repository);
    }


    public Streamer save(Streamer streamer) {
        if(streamer == null) return null;
        if(streamer.getUuid() == null) {
            streamer.setUuid(UUID.randomUUID());
            return super.save(streamer);
        }
        return this.update(streamer);
    }

    public Streamer update(Streamer streamer){
        if(streamer == null) return null;

        Streamer entity = this.findByUuid(streamer.getUuid());
        entity.setFirstName(streamer.getFirstName());
        entity.setLastName(streamer.getLastName());
        entity.setPseudo(streamer.getPseudo());
        entity.setEmail(streamer.getEmail());
        entity.setRule(streamer.getRule());
        entity.setStatus(streamer.getStatus());
        entity.setChaine(streamer.getChaine());

        return super.save(entity);
    }

    public List<String> getPseudoList(){
        List<Streamer> streamers = this.listAll();
        return streamers.stream().map(Streamer::getPseudo)
                .toList();
    }

    public Streamer findByPseudo( String pseudo) {
        return this.repository.findByPseudo(pseudo);
    }

    public Streamer findByUuid(UUID uuid) {
        if(uuid == null) return null;
        return this.repository.findByUuid(uuid);
    }
}
