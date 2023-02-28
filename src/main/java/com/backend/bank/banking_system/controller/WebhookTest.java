package com.backend.bank.banking_system.controller;

import com.backend.bank.banking_system.dtos.request.AccountRequestDto;
import com.backend.bank.banking_system.dtos.response.AccountResponseDto;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@Data
public class WebhookTest {
    @RequestMapping(
            path = "product/payments/juspay/webhook",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    public ResponseEntity<String> juspayWebhook(@RequestBody String webhookRequest,
                                        @RequestHeader("secret") String secret) throws AuthenticationException {
        System.out.println("got webhook as :" + webhookRequest);
        System.out.println("Secret_juspay {} "+ secret);
        //log.info("juspay webhook Called  with event name " +webhookRequest.getEventName());
        return ResponseEntity.ok("Webhook received");

    }
}
