package com.tacticalreport.emailMicroservice.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmailTemplateFileRequest {
   private String to;
   private String subject;
   private MultipartFile file;

}