package com.tacticalreport.emailMicroservice.request;

import lombok.Data;
@Data
public class EmailRequest {
    private String to;
    private String subject;
}