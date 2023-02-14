package com.tacticalreport.emailMicroservice;

import com.tacticalreport.emailMicroservice.request.EmailRequest;
import com.tacticalreport.emailMicroservice.request.EmailTemplateFileRequest;
import com.tacticalreport.emailMicroservice.request.MessageRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService mockService;
    String messageRequestBody = "{\"to\":\"jessicab.6146@gmail.com\",\"subject\":\"Test1\",\"message\":\"Dear Sir...\"}";
    String templateRequestBody = "{\"to\":\"jessicab.6146@gmail.com\",\"subject\":\"Test\"}";

    @Test
    public void testSendEmail() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/email/simpleMessage")
                        .content(messageRequestBody).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"message\":\"Email has been sent successfully to: jessicab.6146@gmail.com\"}", response.getContentAsString());
        verify(mockService).sendEmailMsg(new MessageRequest("jessicab.6146@gmail.com", "Test1", "Dear Sir..."));
    }

    @Test
    public void testSendEmailTemplate() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/email/template")
                        .content(templateRequestBody).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"message\":\"Email has been sent successfully to: jessicab.6146@gmail.com\"}", response.getContentAsString());
        verify(mockService).sendEmailWithTemplate(new EmailRequest("jessicab.6146@gmail.com", "Test"));
    }


    @Test
    public void testSendEmailFileTemp() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/email/temp")
                        .content(templateRequestBody).contentType(MediaType.APPLICATION_JSON)
                        .param("fileName", "email.html")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"message\":\"Email has been sent successfully to: jessicab.6146@gmail.com\"}", response.getContentAsString());
        verify(mockService).sendEmailFileTemplate(new EmailRequest("jessicab.6146@gmail.com", "Test"), "email.html");
    }


//    @Test
//    public void testSendEmailTempUsingAttachment() throws Exception {
//
//        InputStream uploadStream = EmailController.class.getClassLoader().getResourceAsStream("email.html");
//        // Setup
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(multipart("/email/attachment")
//                        .file(new MockMultipartFile("file", "email.html", "multipart/form-data",
//                                "content".getBytes()))
//                        .param("to", "jessicab.6146@gmail.com")
//                        .param("subject", "test")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("{\"message\":\"Email has been sent successfully to: jessicab.6146@gmail.com\"}", response.getContentAsString());
//        verify(mockService).sendEmailTempAttachment(new EmailTemplateFileRequest("jessicab.6146@gmail.com", "test", null));
//    }
//
//    @Test
//    public void testReturnTemp() throws Exception {
//        // Setup
//        when(mockService.returnFileTemp("fileName")).thenReturn(null);
//
//        // Run the test
//        final MockHttpServletResponse response = mockMvc.perform(get("/email/downloadFile/{fileName:.+}", "fileName")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//
//        // Verify the results
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//        assertEquals("expectedResponse", response.getContentAsString());
//    }
}
