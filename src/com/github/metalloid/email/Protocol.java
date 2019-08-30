package com.github.metalloid.email;

public enum Protocol {
    POP3,
    POP3S,
    IMAPS;

    public String toString() {
        switch (this) {
            case POP3: return "pop3";
            case POP3S: return "pop3s";
            case IMAPS: return "imaps";
        }

        throw new RuntimeException("not implemented");
    }
}
