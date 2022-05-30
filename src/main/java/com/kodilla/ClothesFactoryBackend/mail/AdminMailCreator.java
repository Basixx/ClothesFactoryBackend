package com.kodilla.ClothesFactoryBackend.mail;

import com.kodilla.ClothesFactoryBackend.config.AdminConfig;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminMailCreator {
    private final AdminConfig adminConfig;

    public Mail createMailForAdminOrderCreated(Order order) {
        String adminMail = adminConfig.getAdminMail();
        String subject = "New order created";
        String message = "New order has been created by " + order.getUser().getName() + " " + order.getUser().getSurname() + " - " + order.getUser().getEmailAddress() + ".";
        StringBuilder messageClothes = new StringBuilder("\nOrder contains: ");
        int i = 1;
        for(Cloth cloth : order.getClothesList()) {
            messageClothes.append("\n").append(i).append(". ").append(cloth.toString());
            i++;
        }
        String messagePrice = "\n" + "For total price of: " + order.getTotalOrderPrice();
        String messageAddress = "\n Address: \n" + order.getAddress();
        String messageShipment = "\n Shipment: " + order.getShipmentCompanyName();
        return new Mail(adminMail, subject, message + messageClothes + messagePrice + messageAddress + messageShipment);
    }

    public Mail createTokenMail(String token) {
        String adminMail = adminConfig.getAdminMail();
        String subject = "New Admin Token generated.";
        String message = "Admin token has been generated, enter below code to log in as admin: \n" + token;
        return new Mail(adminMail, subject, message);
    }
}