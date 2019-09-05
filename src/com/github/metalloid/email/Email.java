package com.github.metalloid.email;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public class Email {
    private Message message;

    public Email(Message message) {
        this.message = message;
    }

    public Address getFrom() throws MessagingException {
        return message.getFrom()[0];
    }

    public String getTitle() throws MessagingException {
        return message.getSubject();
    }

    public EmailBody getBody() throws IOException, MessagingException {
        return new EmailBody(message.getContent());
    }

    public boolean equals(Message message) {
        return this.message.equals(message);
    }

    public Address[] getAllRecipients() throws MessagingException{
        return this.message.getAllRecipients();
    }

    public Address[] getRecipients(Message.RecipientType type) throws MessagingException{
        return this.message.getRecipients(type);
    }
}