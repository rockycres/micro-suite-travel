package com.learn.ordersvc.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {

    protected String title;
    protected int status;
    protected LocalDateTime timestamp;
    protected String developerMessage;
    protected String detail;


}
