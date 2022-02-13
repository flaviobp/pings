package com.pingr.Pings.core.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pingr.Pings.core.Ping;

import java.util.HashMap;
import java.util.Map;

@JsonSerialize
public class PingDeletedEvent {

    @JsonProperty
    private String eventType;

    @JsonProperty
    private Long pingId;

    @JsonProperty
    private Map<String, Object> payload;

    public PingDeletedEvent() {
    }

    public PingDeletedEvent(String eventType, Long pingId, Map<String, Object> payload) {
        this.eventType = eventType;
        this.pingId = pingId;
        this.payload = payload;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getPingId() {
        return pingId;
    }

    public void setPingId(Long pingId) {
        this.pingId = pingId;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public static PingDeletedEvent of(Ping ping) {
        Map<String, Object> pingMapView = new HashMap<>();

        pingMapView.put("pingId", ping.getId());
        pingMapView.put("authorId", ping.getAuthor().getId());

        return new PingDeletedEvent(
                "PingDeletedEvent",
                ping.getId(),
                pingMapView
        );
    }
}
