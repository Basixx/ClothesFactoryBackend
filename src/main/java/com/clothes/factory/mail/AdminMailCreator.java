package com.clothes.factory.mail;

import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMailCreator {

    @Value("${admin.mail}")
    private String adminMail;

    public Mail createMailForAdminOrderCreated(Order order) {
        String subject = "New order created";
        StringBuilder message = new StringBuilder("""
                New order has been created by %s %s - %s.
                
                Order contains:
                """.formatted(
                order.getUser().getName(),
                order.getUser().getSurname(),
                order.getUser().getEmailAddress()
        ));
        int i = 1;
        for (Cloth cloth : order.getClothesList()) {
            message.append("%d. %s%n".formatted(i++, cloth.toString()));
        }
        message.append("""
                
                For total price of: %s PLN,
                Address: %s
                Shipment: %s
                """
                .formatted(
                        order.getTotalOrderPrice(),
                        order.getAddress(),
                        order.getShipmentMethod()
                )
        );
        return new Mail(adminMail, subject, message.toString());
    }

    public Mail createTokenMail(String token) {
        String subject = "New Admin Token generated.";
        String message = """
                Admin token has been generated, enter below code to log in as admin:
                %s
                """.formatted(token);
        return new Mail(adminMail, subject, message);
    }

}
