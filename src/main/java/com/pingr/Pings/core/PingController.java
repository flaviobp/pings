package com.pingr.Pings.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pings")
public class PingController {

    private final PingService service;

    @Autowired
    public PingController(PingService service) {
        this.service = service;
    }

    @PostMapping(path = {"/{userid}"})
    @ResponseStatus(HttpStatus.CREATED)
    public Ping createOnePing(@PathVariable("userid") Long userid, @RequestBody Ping ping) {
        return this.service.createPing(userid, ping);
    }

    @PostMapping(path = {"/{id}/replies/{userid}"})
    @ResponseStatus(HttpStatus.CREATED)
    public Ping createReply(@PathVariable("id") Long id, @PathVariable("userid") Long userid, @RequestBody Ping ping) {
        return this.service.replyPing(id, userid, ping);
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneAccount(@PathVariable("id") Long id) {
        this.service.deletePing(id);
    }

    @GetMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public Ping findById(@PathVariable Long id){
        return this.service.getPingById(id);
    }


}
