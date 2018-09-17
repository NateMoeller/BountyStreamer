package com.nicknathanjustin.streamercontracts.alerts;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertApiController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public boolean testAlert(@NonNull @RequestBody final String alertKey) {
        final String username = "User123";
        final BigDecimal amount = new BigDecimal(3.0);
        final String bounty = "This is a test bounty message";
        final AlertMessage testMessage = new AlertMessage(username, bounty, amount);
        final String url = "/alert/" + alertKey;

        messagingTemplate.convertAndSend(url, testMessage);
        return true;
    }
}
