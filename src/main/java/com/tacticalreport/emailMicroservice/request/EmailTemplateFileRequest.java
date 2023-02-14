package com.tacticalreport.emailMicroservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplateFileRequest {
   private String to;
   private String subject;
   private MultipartFile file;

}