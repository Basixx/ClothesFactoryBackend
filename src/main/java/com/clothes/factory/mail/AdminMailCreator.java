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
        String message = "New order has been created by " + order.getUser().getName() + " " + order.getUser().getSurname() + " - " + order.getUser().getEmailAddress() + ".";
        StringBuilder messageClothes = new StringBuilder("\nOrder contains: ");
        int i = 1;
        for (Cloth cloth : order.getClothesList()) {
            messageClothes.append("\n").append(i).append(". ").append(cloth.toString());
            i++;
        }
        String messagePrice = "\n" + "For total price of: " + order.getTotalOrderPrice();
        String messageAddress = "\n Address: \n" + order.getAddress();
        String messageShipment = "\n Shipment: " + order.getShipmentCompanyName();
        return new Mail(adminMail, subject, message + messageClothes + messagePrice + messageAddress + messageShipment);
    }

    public Mail createTokenMail(String token) {
        String subject = "New Admin Token generated.";
        String message = "Admin token has been generated, enter below code to log in as admin: \n" + token;
        return new Mail(adminMail, subject, message);
    }

}
