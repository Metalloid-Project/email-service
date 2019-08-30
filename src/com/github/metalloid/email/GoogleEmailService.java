package com.github.metalloid.email;

public class GoogleEmailService extends EmailService {

    protected GoogleEmailService() {
        super("imap.googlemail.com", 993, Protocol.IMAPS);
    }
}
