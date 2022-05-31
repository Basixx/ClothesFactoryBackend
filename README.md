FRONTEND: https://github.com/Basixx/ClothesFactoryFronted.git

*English Below*

1. Należy utworzyć bazę danych MySQL clothes_factory oraz użytkownika z poniższymi danymi oraz utworzyć zmienne środowiskowe:

SQL_USERNAME = clothes_factory_admin
SQL_PASSWORD = adminpassword

2. Zmienne środowiskowe niezbędne do działania aplikacji:

API_LAYER_KEY = *token wygenerowany po utworzeniu konta na stronie https://apilayer.com/, wymagana subskrypcja Exchange Rates Data API oraz Bad Words API*
MAIL_API_KEY = *token wygenerowany po utworzeniu konta na stronie https://main.whoisxmlapi.com/, wymagana subskrypcja Email Verification API*


3. Dane mailowe (zmienne środowiskowe):

Adres e-mail "Sklepu" wysyłający wiadomości do użytkowników oraz do admina (właściciela sklepu):

OUTLOOK_MAIL_USERNAME = *adres email utworzony w domenie @outlook.com, "zezwalający urządzeniom i aplikacjom na używanie protokołu POP"
(Outlook: Ustawienia -> Wyświetl wszystkie ustawienia programu Outlook -> Synchronizacja poczty e-mail -> Zezwalaj urządzeniom i aplikacjom na używanie protokołu POP -> TAK)*
OUTLOOK_MAIL_PASSWORD = *hasło do powyższego adresu*

Adres e-mail admina (właściciela sklepu):

ADMIN_MAIL = *istniejący adres e-mail, na który będą przychodzić maile informujące o zamówieniach utworzonych przez użytkowników lub zawierające token niezbędny do logowania jako admin*

4. SWAGGER:
   http://localhost:8080/swagger-ui.html

W sumie 7 zmiennych środowiskowych.
___________________________________________________

English:

To start using this application a few steps need to be done:

1. Please create a MySQL database clothes_factory and user with below credentials and provide Environment Variables on your local computer:
   SQL_USERNAME = clothes_factory_admin
   SQL_PASSWORD = adminpassword

2. Environment Variables necessary for application to work properly:

API_LAYER_KEY = *token generated after signing in https://apilayer.com/, subscribing Exchange Rates Data API and Bad Words API on this site is necessary*
MAIL_API_KEY = *token generated after signing in https://main.whoisxmlapi.com/, subscribing Email Verification API on this site is necessary*

3. Please provide Environment Variables for "Shop" email address, which sends messages to admin and users.

OUTLOOK_MAIL_USERNAME = *email address created in domain @outlook.com, with POP protocol usage enabled.
(Outlook: Settings -> View all Outlook settings -> Sync email -> Let devices and apps use POP -> YES)*
OUTLOOK_MAIL_PASSWORD = *password for  the account*

Admin's (shop owner's) email address:

ADMIN_MAIL = *existing email address for the shop owner for receiving token and orders information messages*

4. SWAGGER:
   http://localhost:8080/swagger-ui.html

Total amount of 7 Environment Variables