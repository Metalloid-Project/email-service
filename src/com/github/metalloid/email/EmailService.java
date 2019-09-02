package com.github.metalloid.email;

import javax.mail.*;
import java.util.Properties;

public class EmailService {
    private Session session;
    private String username;
    private String password;
    private String host;
    private int port = -1;

    protected EmailService(String host, Protocol protocol) {
        this.host = host;
        username = System.getProperty("email.username");
        password = System.getProperty("email.password");

        if (username == null) {
            throw new IllegalArgumentException("Use System.setProperty(\"email.username\") to provide username for email service of your choice");
        }

        if (password == null) {
            throw new IllegalArgumentException("Use System.setProperty(\"email.password\") to provide password for email service of your choice");
        }

        setProtocol(protocol);
    }

    protected EmailService(String host, int port, Protocol protocol) {
        this(host, protocol);
        this.port = port;
    }

    private void setProtocol(Protocol protocol) {
        Properties props = new Properties();
        props.put("mail.store.protocol", protocol.toString());
        this.session = Session.getDefaultInstance(props);
    }

    public Inbox getInbox() throws MessagingException {
        Store store = session.getStore();
        if (port == -1) {
            store.connect(host, username, password);
        } else {
            store.connect(host, port, username, password);
        }

        return new Inbox(store, store.getFolder("INBOX"));
    }
}
