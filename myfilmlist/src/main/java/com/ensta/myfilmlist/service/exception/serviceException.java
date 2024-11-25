package com.ensta.myfilmlist.service.exception;

import net.bytebuddy.asm.Advice;


public class serviceException extends Exception {
    public serviceException(String message) {
        super(message);
    }

    public serviceException(String message, Throwable cause) {
        super(message, cause);
    }

}
