package com.gcode.personalFinance.controller;

import com.gcode.personalFinance.service.WhatsappService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private WhatsappService whatsappService;

    // Verificación inicial del webhook (Meta lo requiere)
    @GetMapping
    public String verifyWebhook(@RequestParam("hub.mode") String mode,
                                @RequestParam("hub.challenge") String challenge,
                                @RequestParam("hub.verify_token") String verifyToken) {
        if ("mi-token-secreto".equals(verifyToken)) {
            return challenge;
        } else {
            return "Error de verificación";
        }
    }

    // Recibir mensajes POST desde WhatsApp
    @PostMapping
    public void receiveMessage(@RequestBody Map<String, Object> payload) {
        try {
            // Extraer texto del mensaje recibido
            Map entry = ((java.util.List<Map>) payload.get("entry")).get(0);
            Map changes = ((java.util.List<Map>) entry.get("changes")).get(0);
            Map value = (Map) changes.get("value");
            if (value.containsKey("messages")) {
                Map message = ((java.util.List<Map>) value.get("messages")).get(0);
                String text = (String) ((Map) message.get("text")).get("body");

                // Procesar mensaje
                whatsappService.processMessage(text);
            }
        } catch (Exception e) {
            System.err.println("❌ Error procesando payload entrante");
            e.printStackTrace();
        }
    }
}
