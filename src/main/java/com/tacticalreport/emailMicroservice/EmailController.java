package com.tacticalreport.emailMicroservice;

 import com.tacticalreport.emailMicroservice.request.EmailRequest;
 import com.tacticalreport.emailMicroservice.request.EmailTemplateFileRequest;
 import com.tacticalreport.emailMicroservice.request.MessageRequest;
 import com.tacticalreport.emailMicroservice.response.EmailResponse;
 import io.swagger.v3.oas.annotations.tags.Tag;
 import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.io.Resource;
 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.MediaType;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import javax.mail.MessagingException;
 import java.io.FileNotFoundException;
 import java.io.IOException;

@RestController
 @RequestMapping(value = "email")
 @Tag(name = "Email Controller")
 public class EmailController {
   @Autowired  
   private EmailService service;

   @PostMapping("/simpleMessage")
   public ResponseEntity<?> sendEmail(@RequestBody MessageRequest request) throws MessagingException {
       service.sendEmailMsg(request);
       EmailResponse response= new EmailResponse();
       response.setMessage("Email has been sent successfully to: " + request.getTo());
       return ResponseEntity.status(HttpStatus.OK).body(response);
   }

   @PostMapping("/template")
   public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailRequest request) throws MessagingException {
       service.sendEmailWithTemplate(request);
       EmailResponse response= new EmailResponse();
       response.setMessage("Email has been sent successfully to: " + request.getTo());
       return ResponseEntity.status(HttpStatus.OK).body(response);
   }

    @PostMapping("/temp")
    public ResponseEntity<?> sendEmailFileTemp(@RequestBody EmailRequest request, @RequestParam String fileName) throws MessagingException {
        service.sendEmailFileTemplate(request, fileName);
        EmailResponse response= new EmailResponse();
        response.setMessage("Email has been sent successfully to: " + request.getTo());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

     @PostMapping(value= "attachment", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
     public ResponseEntity<?> sendEmailTempUsingAttachment(@Required @ModelAttribute EmailTemplateFileRequest emailTemplateFileRequest) throws MessagingException, IOException {
         service.sendEmailTempAttachment(emailTemplateFileRequest);
         EmailResponse response= new EmailResponse();
         response.setMessage("Email has been sent successfully to: " + emailTemplateFileRequest.getTo());
         return ResponseEntity.status(HttpStatus.OK).body(response);
     }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity < ? > returnTemp(@PathVariable String fileName) throws FileNotFoundException {
        Resource resource = service.returnFileTemp(fileName);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
 } 