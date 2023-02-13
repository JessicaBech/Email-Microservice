package com.tacticalreport.emailMicroservice;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmailTemplateFileEntity {
   private String to;
   private String subject;
   private MultipartFile file;

}