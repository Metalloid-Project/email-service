package com.github.metalloid.email;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmailBody {
    private Object content;
    private Function<String, String> TRIM = String::trim;

    EmailBody(Object content) {
        this.content = content;
    }

    private boolean isMultipart() {
        return content instanceof Multipart;
    }

    public String toString() {
        if (isMultipart()) return combineMultipart((Multipart) content);
        else return (String) content;
    }

    public List<String> getLines() {
        return getLinesStream().collect(Collectors.toList());
    }

    public Stream<String> getLinesStream() {
        return Arrays.stream(toString().split("\n")).map(TRIM);
    }

    private String multiPartContent = "";
    private String combineMultipart(Multipart multipart) {
        try {
            int parts = multipart.getCount();
            for (int i = 0; i < parts; i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                Object contentOfBodyPart = bodyPart.getContent();
                if (contentOfBodyPart instanceof Multipart) {
                    multiPartContent = multiPartContent.concat(combineMultipart((Multipart) contentOfBodyPart)).concat(System.lineSeparator());
                } else {
                    multiPartContent = multiPartContent.concat((String) contentOfBodyPart).concat(System.lineSeparator());
                }
            }
            return multiPartContent;
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
