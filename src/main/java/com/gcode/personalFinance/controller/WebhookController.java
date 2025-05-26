package com.gcode.personalFinance.controller;

import com.gcode.personalFinance.service.WhatsappService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final String VERIFY_TOKEN = "gorgedimonca";

    @Autowired
    private WhatsappService whatsappService;


    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") String challenge,
            @RequestParam("hub.verify_token") String token) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(403).body("Verificación fallida");
        }
    }

    // Recepción de mensajes (POST)
    @PostMapping
    public void receiveMessage(@RequestBody Map<String, Object> payload) {
        try {
            Map entry = ((java.util.List<Map>) payload.get("entry")).get(0);
            Map changes = ((java.util.List<Map>) entry.get("changes")).get(0);
            Map value = (Map) changes.get("value");
            if (value.containsKey("messages")) {
                Map message = ((java.util.List<Map>) value.get("messages")).get(0);
                String text = (String) ((Map) message.get("text")).get("body");

                whatsappService.processMessage(text);
            }
        } catch (Exception e) {
            System.err.println("❌ Error procesando el payload entrante");
            e.printStackTrace();
        }
    }
}
