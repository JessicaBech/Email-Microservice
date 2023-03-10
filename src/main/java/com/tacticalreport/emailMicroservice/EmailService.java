package com.tacticalreport.emailMicroservice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.tacticalreport.emailMicroservice.exception.FailedToFindFileException;
import com.tacticalreport.emailMicroservice.request.EmailRequest;
import com.tacticalreport.emailMicroservice.request.EmailTemplateFileRequest;
import com.tacticalreport.emailMicroservice.request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


@Service
public class EmailService {
  @Autowired
  private JavaMailSender emailSender;

  @Autowired
  private SpringTemplateEngine templateEngine;

  private final Path root = Paths.get("./src/main/resources/templates/");

  @Value("{spring.mail.username}")
  private String gmailFrom;
  public void sendEmailMsg(MessageRequest messageRequest) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    try{
      MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
      helper.setTo(messageRequest.getTo());
      helper.setText(messageRequest.getMessage());
      helper.setSubject(messageRequest.getSubject());
      helper.setFrom(gmailFrom);
      emailSender.send(message);
    } catch (MessagingException e) {
      throw e;
    }
  }

  public void sendEmailWithTemplate(EmailRequest emailRequest) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    try{
    MimeMessageHelper helper = new MimeMessageHelper(message,
            MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
            StandardCharsets.UTF_8.name());
    Context context = new Context();

    String html = templateEngine.process("email.html", context);
    html = html.replace("${name}", "Jessica");
    helper.setTo(emailRequest.getTo());
    helper.setText(html, true);
    helper.setSubject(emailRequest.getSubject());
    helper.setFrom(gmailFrom);
    emailSender.send(message);
    } catch (MessagingException e) {
      throw e;
    }
  }

  public void sendEmailFileTemplate(EmailRequest emailRequest, String fileName) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    try{
      MimeMessageHelper helper = new MimeMessageHelper(message,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      Context context = new Context();

      String html = templateEngine.process(fileName, context);
      html = html.replace("${name}", "Jessica");
      helper.setTo(emailRequest.getTo());
      helper.setText(html, true);
      helper.setSubject(emailRequest.getSubject());
      helper.setFrom(gmailFrom);
      emailSender.send(message);
    } catch (MessagingException e) {
      throw e;
    }
  }

  public void sendEmailTempAttachment(EmailTemplateFileRequest emailTemplateFileRequest) throws MessagingException, IOException {
    MimeMessage message = emailSender.createMimeMessage();
    try{
      Files.copy(emailTemplateFileRequest.getFile().getInputStream(), this.root.resolve(emailTemplateFileRequest.getFile().getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
      MimeMessageHelper helper = new MimeMessageHelper(message,
              MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      Context context = new Context();

      String html = templateEngine.process(emailTemplateFileRequest.getFile().getOriginalFilename(), context);
      html = html.replace("${name}", "Jessica");
      helper.setTo(emailTemplateFileRequest.getTo());
      helper.setText(html, true);
      helper.setSubject(emailTemplateFileRequest.getSubject());
      helper.setFrom(gmailFrom);
      emailSender.send(message);
    } catch (MessagingException e) {
      throw e;
    }
  }


  public Resource returnFileTemp(String fileName) throws FileNotFoundException {
    try {
      Path filePath = this.root.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new FailedToFindFileException();
      }

    } catch (MalformedURLException ex) {
      throw new FileNotFoundException();
    }
  }
}