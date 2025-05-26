package com.gcode.personalFinance.service;

import com.gcode.personalFinance.model.Business;
import com.gcode.personalFinance.model.Category;
import com.gcode.personalFinance.model.Transaction;
import com.gcode.personalFinance.repository.BusinessRepository;
import com.gcode.personalFinance.repository.CategoryRepository;
import com.gcode.personalFinance.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WhatsappService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void processMessage(String message) {
        try {
            String[] parts = message.split("-");

            if (parts.length != 4) {
                throw new IllegalArgumentException("Formato inválido. Usa: tipo-negocio-categoria-monto");
            }

            String typeText = parts[0].trim().toLowerCase(); // "ingreso" o "egreso"
            String businessName = parts[1].trim().toLowerCase();
            String categoryName = parts[2].trim().toLowerCase();
            double amount = Double.parseDouble(parts[3]);

            int type = switch (typeText) {
                case "ingreso" -> 1;
                case "egreso" -> 0;
                default -> throw new IllegalArgumentException("Tipo inválido. Usa ingreso o egreso.");
            };

            // buscar o crear negocio
            Business business = businessRepository.findByName(businessName)
                    .orElseGet(() -> {
                        Business b = new Business();
                        b.setName(businessName);
                        return businessRepository.save(b);
                    });

            // buscar o crear categoría
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        Category c = new Category();
                        c.setName(categoryName);
                        return categoryRepository.save(c);
                    });

            // guardar la transacción
            Transaction tx = new Transaction();
            tx.setType(type);
            tx.setAmount(amount);
            tx.setBusiness(business);
            tx.setCategory(category);
            transactionRepository.save(tx);

            System.out.println("✔️ Transacción guardada: " + message);

        } catch (Exception e) {
            System.err.println("❌ Error procesando mensaje: " + message);
            e.printStackTrace();
        }
    }
}
