package com.tacticalreport.emailMicroservice.request;

import lombok.Data;
@Data
public class MessageRequest {
    private String to;
    private String subject;
    private String message;
}