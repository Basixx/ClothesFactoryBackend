package com.clothes.factory.mail;

import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.Order;
import com.clothes.factory.domain.User;
import com.clothes.factory.exception.api.CurrencyExchangeFailedException;
import com.clothes.factory.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.RoundingMode.CEILING;

@Service
@RequiredArgsConstructor
public class UserMailCreator {

    private final ExchangeRatesService exchangeRatesService;

    public Mail createMailForUserOrderCreated(Order order) throws CurrencyExchangeFailedException {
        String userEmail = order.getUser().getEmailAddress();
        String subject = "New order in C L O H T E S   F A C T O R Y";
        BigDecimal totalPrice = order.getTotalOrderPrice();
        StringBuilder message = new StringBuilder("""
                You have purchased new clothes:
                """);
        int i = 1;
        for (Cloth cloth : order.getClothesList()) {
            message.append("%d. %s%n".formatted(i++, cloth.toString()));
        }
        message.append("""
                
                For total price of: %s PLN  //  %s EUR  //  %s USD  //  %s GBP
                Your order number: %d
                
                Address: %s
                
                Please send payment for account number 00 1111 2222 33333 4444 5555 6666.
                Shipment: %s, should be delivered in %d days.
                
                Thank you for choosing CLOTHES FACTORY!
                """.formatted(
                totalPrice,
                calculateCurrency("EUR", totalPrice),
                calculateCurrency("USD", totalPrice),
                calculateCurrency("GBP", totalPrice),
                order.getId(),
                order.getAddress(),
                order.getShipmentMethod(), order.getDeliveryDays()
        ));
        return new Mail(userEmail, subject, message.toString());
    }


    public Mail createMailOrderPaid(Order order) {
        String userEmail = order.getUser().getEmailAddress();
        String subject = "Payment confirmed";
        String message = "Your order %s has been paid, we will send it as soon as possible."
                .formatted(order.getId());
        return new Mail(userEmail, subject, message);
    }

    public Mail createMailOrderSent(Order order) {
        String userEmail = order.getUser().getEmailAddress();
        String subject = "Shipment confirmed";
        String message = "Your order %s has been sent, you will receive it in a few days."
                .formatted(order.getId());
        return new Mail(userEmail, subject, message);
    }

    public Mail accountCreationMail(User user) {
        String userMail = user.getEmailAddress();
        String subject = "Welcome";
        String message = """
                Hello, %s %s!
                thank you for creating an account in our shop.
                We hope you will enjoy your purchase!
                """.formatted(user.getName(), user.getSurname());
        return new Mail(userMail, subject, message);
    }

    private BigDecimal calculateCurrency(String currency, BigDecimal totalPrice) throws CurrencyExchangeFailedException {
        return exchangeRatesService.getExchangeRate(currency, "PLN")
                .getCurrencyRate()
                .multiply(totalPrice)
                .setScale(2, CEILING);
    }

}
