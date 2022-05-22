package com.kodilla.ClothesFactoryBackend.mail;

import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMailCreator {

    public Mail createMailForUserOrderCreated(Order order) {
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
        String messageShipment = "\n Shipment: " + order.getShipmentCompanyName() + ", should be delivered in " + order.getDeliveryDays() + " days.";
        String end = "\n Thank you for choosing CLOTHES FACTORY!";
        return new Mail(userEmail, subject, message + messagePrice + messageAddress +  messagePayment + messageShipment + end);
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

    public Mail accountCreationMail(User user) {
        String userMail = user.getEmailAddress();
        String subject = "Welcome";
        String message = "Hello, thank you for creating an account in our shop, " +  user.getName() + " " + user.getSurname() + ". We hope you will enjoy your purchase!";
        return new Mail(userMail, subject, message);
    }
}