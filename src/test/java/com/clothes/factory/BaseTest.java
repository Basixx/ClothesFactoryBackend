package com.clothes.factory;

import com.clothes.factory.client.bad_words_api.BadWordsApiClient;
import com.clothes.factory.client.email_verification_api.EmailVerificationApiClient;
import com.clothes.factory.client.exchange_rates_api.ExchangeRatesApiClient;
import com.clothes.factory.client.quotes_api.QuoteApiClient;
import com.clothes.factory.mail.AdminMailCreator;
import com.clothes.factory.service.EmailService;
import com.clothes.factory.service.UserService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public abstract class BaseTest {

    @MockitoBean
    JavaMailSender javaMailSender;

    @MockitoBean
    BadWordsApiClient badWordsApiClient;

    @MockitoBean
    EmailVerificationApiClient emailVerificationApiClient;

    @MockitoBean
    ExchangeRatesApiClient exchangeRatesApiClient;

    @MockitoBean
    QuoteApiClient quoteApiClient;

    @MockitoBean
    AdminMailCreator adminMailCreator;

    @MockitoBean
    EmailService emailService;

    @MockitoBean
    UserService userService;

}
