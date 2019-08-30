package com.github.metalloid.email;

import javax.mail.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static javax.mail.Flags.Flag.DELETED;

public class Inbox {
    private Store store;
    private Folder folder;

    public Inbox(Store store, Folder folder) {
        this.store = store;
        try {
            folder.open(Folder.READ_WRITE);
            this.folder = folder;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Email> getEmails() throws MessagingException {
        return Arrays.stream(folder.getMessages()).map(Email::new).collect(Collectors.toList());
    }

    public void delete(Email email) {
        try {
            Message[] messages = folder.getMessages();
            for (Message message : messages) {
                if (email.equals(message)) {
                    message.setFlag(DELETED, true);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void closeInbox() {
        try {
            folder.close(true);
            store.close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
