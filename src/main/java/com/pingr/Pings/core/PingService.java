package com.pingr.Pings.core;

import com.pingr.Pings.exceptions.EntityNotFoundException;
import com.pingr.Pings.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PingService {
    private final AccountRepository accountrepo;
    private final PingRepository pingrepo;
    private final ProducerService producer;

    @Autowired
    public PingService(AccountRepository accountrepo, PingRepository pingrepo, ProducerService producer) {
        this.accountrepo = accountrepo;
        this.pingrepo = pingrepo;
        this.producer = producer;
    }

    public Ping createPing(Long userid, Ping ping) throws IllegalArgumentException {
        Optional<Account> userById = this.accountrepo.findById(userid);
        if (userById.isEmpty()) {
            throw new EntityNotFoundException("account", userid);
        }

        if(ping.getBody().length() > 144)
            throw new EntityValidationException("body",new Exception("Mais de 144 caracteres."));

        Account user = userById.get();
        ping.setAuthor(user);

        try {
            Ping pg = this.pingrepo.save(ping);
            this.producer.emitPingCreatedEvent(ping);
            return pg;
        } catch(Exception e) {
            throw new IllegalArgumentException("Ping creation violates restrictions" + "[ping: " + ping + "]");
        }
    }

    public Ping replyPing(Long id, Long userid, Ping ping) throws IllegalArgumentException {
        try {
            Ping reply = this.createPing(userid, ping);
            Ping pg = this.getPingById(id);
            pg.getReplies().add(reply);
            this.pingrepo.save(pg);
            return reply;
        } catch(Exception e) {
            throw new IllegalArgumentException("Ping reply creation violates restrictions" + "[´ping: " + ping + "]");
        }
    }

    public void deletePing(Long id) throws IllegalArgumentException {
        try {

            /*
            Optional<Ping> oneById = this.pingrepo.findByReplies(id);
            if (oneById.isPresent()){
                System.out.println("Pai ping:" + oneById.get().getId());
                Ping pai = oneById.get();
                pai.getReplies().removeIf(x -> x.getId().equals(id));
                this.pingrepo.save(pai);
            }*/
            Ping ping = this.getPingById(id);
            this.pingrepo.deleteById(id);
            this.producer.emitPingDeletedEvent(ping);
        } catch(Exception e) {
            throw new IllegalArgumentException("Ping deletion violates restrictions" + "[ping: " + id + "]");
        }
    }

    public Ping getPingById(Long id) {
        Optional<Ping> oneById = this.pingrepo.findById(id);
        if (oneById.isEmpty()) {
            throw new EntityNotFoundException("ping", id);
        }

        return oneById.get();
    }

}
