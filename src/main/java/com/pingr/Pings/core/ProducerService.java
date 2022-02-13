package com.pingr.Pings.core;

import com.pingr.Pings.core.events.PingCreatedEvent;
import com.pingr.Pings.core.events.PingDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Value(value = "${topics.pg_created}")
    private String pingCreatedTopic;

    @Value(value = "${topics.pg_deleted}")
    private String pingDeletedTopic;

    @Autowired // injeção de dependências
    private KafkaTemplate<String, Object> template;

    public void emitPingCreatedEvent(Ping ping) {
        this.template.send(
                this.pingCreatedTopic,
                PingCreatedEvent.of(ping));
    }

    public void emitPingDeletedEvent(Ping ping) {
        this.template.send(
                this.pingDeletedTopic,
                PingDeletedEvent.of(ping));
    }


}
