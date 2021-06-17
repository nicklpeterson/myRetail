package com.myRetail.endpoints;

import org.junit.jupiter.api.Test;

import static com.myRetail.TestingConstants.LOGIN_URI;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestPostLogin extends AbstractTest {
    /*
    TEST CASE 1

    /login responds to a valid login with an Authorization header containing a JWT
     */
    @Test
    public void validLoginAttempt() throws Exception {
        this.mockMvc.perform(post(LOGIN_URI)
                .content("{\"username\": \"admin\", \"password\": \"admin\"}"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"));
    }

    /*
    TEST CASE 2

    /login responds to an invalid login with 403 forbidden
   */
    @Test
    public void invalidLoginAttempt() throws Exception {
        this.mockMvc.perform(post(LOGIN_URI)
                .content("{\"username\": \"nick\", \"password\": \"dev\"}"))
                .andExpect(status().isUnauthorized());
    }

    /*
    TEST CASE 3

    /login Authentication filter throws a runtime exception when it is unable to parse the JSON Body
    */
    @Test
    public void malformedLoginAttempt() throws Exception {
        try {
            this.mockMvc.perform(post(LOGIN_URI)
                    .content("{\"blahblahblah\": \"nick\", \"notapassword\": \"dev\", \"extrafield\":\"somestuff\"}"));

            fail("The Authentication filter should throw a runtime exception if it is unable to parse the request.");
        } catch (RuntimeException e) {
            // This is expected
        }
    }

    /*
    TEST CASE 4

    /login Authentication filter throws a runtime exception when the request body contains improper json.
    */
    @Test
    public void badJsonLoginAttempt() throws Exception {
        try {
            this.mockMvc.perform(post(LOGIN_URI)
                    .content("{\"blahblahblah\": \"nick\", \"notapassword\":"));

            fail("The Authentication filter should throw a runtime exception if it is unable to parse the request.");
        } catch (RuntimeException e) {
            // This is expected
        }
    }
}
