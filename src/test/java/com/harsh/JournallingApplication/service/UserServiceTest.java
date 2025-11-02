package com.harsh.JournallingApplication.service;

import com.harsh.JournallingApplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void testFindByUserName(){
        assertNotNull(userRepository.findByUsername("user3"));
    }

    @ParameterizedTest
    @CsvSource({
        "1,2,3",
        "1,1,2"

    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }
}
