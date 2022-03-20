package com.example.strategyzertakehome.error;

public class TinyURLNotFoundException extends Exception {
    public TinyURLNotFoundException() {
        super();
    }

    public TinyURLNotFoundException(String message) {
        super(message);
    }

    public TinyURLNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TinyURLNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TinyURLNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
