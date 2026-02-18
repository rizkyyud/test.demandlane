package com.example.test.demandlane.security;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class LoanSecurityTest {

    private final MockMvc mockMvc;

    @Test
    void createLoan_withoutAuth_shouldReturn401() throws Exception {
        mockMvc.perform(post("/demandlane/loan/createLoan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"bookId":1,"memberId":1}
                        """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createLoan_userRole_shouldReturn403() throws Exception {
        mockMvc.perform(post("/demandlane/loan/createLoan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"bookId":1,"memberId":1}
                        """))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createLoan_adminRole_shouldReturn201() throws Exception {
        mockMvc.perform(post("/demandlane/loan/createLoan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"bookId":1,"memberId":1}
                        """))
                .andExpect(status().isCreated());
    }

}
