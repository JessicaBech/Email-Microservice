package com.tacticalreport.emailMicroservice.handler;

import com.tacticalreport.emailMicroservice.BaseResponse;
import com.tacticalreport.emailMicroservice.exception.FailedToFindFileException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
@AllArgsConstructor
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class FileTemplateExceptionHandler {

  @ExceptionHandler(FailedToFindFileException.class)
  public ResponseEntity<BaseResponse> handleFileTemplateNotFound(
      FailedToFindFileException ex) {
    log.warn("Handle FailedToFindFileException. Going to return NOT_FOUND", ex);
    /*
    Return Expectation failed response
     */
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.parseMediaType("application/json"))
            .body(new BaseResponse("File Not Found", 404));
  }

}
