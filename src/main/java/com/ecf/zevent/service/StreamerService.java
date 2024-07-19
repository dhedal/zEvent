package com.ecf.zevent.service;

import com.ecf.zevent.model.Streamer;
import com.ecf.zevent.model.embeddables.StreamerPublicData;
import com.ecf.zevent.repository.StreamerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamerService extends AbstractService<StreamerRepository, Streamer>{
    private static final Logger LOG = LoggerFactory.getLogger(StreamerService.class);
    @Autowired
    public StreamerService(StreamerRepository repository) {
        super(repository);
    }


//    public Streamer save(Streamer streamer) {
//        if(streamer == null) return null;
//        if(streamer.getUuid() == null || streamer.getUuid().equals("")) {
//            return super.save(streamer);
//        }
//        return this.update(streamer);
//    }

//    public Streamer update(Streamer streamer){
//        LOG.debug(streamer.toString());
//        if(streamer == null) return null;
//        Streamer entity = this.findByUuid(streamer.getUuid());
//        if(entity == null) return null;
//        entity.setFirstName(streamer.getFirstName());
//        entity.setLastName(streamer.getLastName());
//        entity.setPseudo(streamer.getPseudo());
//        entity.setEmail(streamer.getEmail());
//        entity.setBirthDate(streamer.getBirthDate());
//        entity.setRule(streamer.getRule());
//        entity.setStatus(streamer.getStatus());
//        entity.setChannel(streamer.getChannel());
//        entity.setUpdatedAt(LocalDateTime.now());
//
//        return super.save(entity);
//    }

    public List<String> getPseudoList(){
        List<Streamer> streamers = this.listAll();
        return streamers.stream()
                .map(Streamer::getPublicData)
                .map(StreamerPublicData::getPseudo)
                .toList();
    }

    public Streamer findByPseudo( String pseudo) {
        return this.repository.findByPseudo(pseudo);
    }

    public Streamer findByUuid(String uuid) {
        if(uuid == null || uuid.equals("")) return null;
        return this.repository.findByUuid(uuid);
    }

    public Streamer findByAuthenticationDataId(Long authenticationDataId) {
        return this.repository.findByAuthenticationDataId(authenticationDataId);
    }
}
