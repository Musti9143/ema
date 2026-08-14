package com.mdzdmr.ema.exception;

public class EmptyRequestBodyException extends RuntimeException {

    public EmptyRequestBodyException() {
        super("Nothing to update with no Parameter given!");
    }
}
