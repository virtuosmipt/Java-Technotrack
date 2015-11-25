package ru.mail.track.net;

/**
 * Created by a.borodin on 20.11.2015.
 */
public class ProtocolException extends Exception {

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolException(String message) {
        super(message);
    }
}