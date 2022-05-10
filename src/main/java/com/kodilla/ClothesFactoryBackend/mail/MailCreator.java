package com.kodilla.ClothesFactoryBackend.mail;

import com.kodilla.ClothesFactoryBackend.client.config.AdminConfig;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailCreator {
    private final AdminConfig adminConfig;

    public Mail createMailForAdmin(Order order) {
        String adminMail = adminConfig.getAdminMail();
        String subject = "New order created";
        String message = "New order has been created by " + order.getUser().getName() + " " + order.getUser().getSurname() + ".";
        String messageClothes = "\nOrder contains: ";
        int i = 1;
        for(Cloth cloth : order.getClothesList()) {
            messageClothes += "\n" + i + ". "+ cloth.toString();
            i++;
        }
        String messagePrice = "\n" + "For total price of: " + order.getTotalOrderPrice();
        String messageAddress = "\n Address: \n" + order.getAddress();
        return new Mail(adminMail, subject, message + messageClothes + messagePrice + messageAddress);
    }

    public Mail createMailForUser(Order order) {
        String userEmail = order.getUser().getEmailAddress();
        String subject = "New order in C L O H T E S   F A C T O R Y";
        String message = "\nYou have purchased new clothes: ";
        int i = 1;
        for(Cloth cloth : order.getClothesList()) {
            message += "\n" + i + ". "+ cloth.toString();
            i++;
        }
        String messagePrice = "\n" + "For total price of: " + order.getTotalOrderPrice() + "\n" + "Your order number: " + order.getId();
        String messageAddress = "\n Address: \n" + order.getAddress();
        String messagePayment = "\nPlease send payment for account number 00 1111 2222 33333 4444 5555 6666.";
        return new Mail(userEmail, subject, message + messagePrice + messageAddress +  messagePayment);
    }

    public Mail createMailOrderPaid(Order order) {
        String userEmail = order.getUser().getEmailAddress();
        String subject = "Payment confirmed";
        String message = "Your order " + order.getId() + " has been paid, we will send it as soon as possible.";
        return new Mail(userEmail, subject, message);
    }

    public Mail createMailOrderSent(Order order) {
        String userEmail = order.getUser().getEmailAddress();
        String subject = "Shipment confirmed";
        String message = "Your order " + order.getId() + " has been sent, you will receive it in a few days.";
        return new Mail(userEmail, subject, message);
    }

    public Mail createTokenMail(String token) {
        String adminMail = adminConfig.getAdminMail();
        String subject = "New Admin Token generated.";
        String message = "Admin token has been generated, enter below code to log in as admin: \n" + token;
        return new Mail(adminMail, subject, message);
    }
}
