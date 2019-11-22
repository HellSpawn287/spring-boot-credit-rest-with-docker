package hellspawn287.springbootcreditrestwithdocker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractRestControllerTest {
    static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
