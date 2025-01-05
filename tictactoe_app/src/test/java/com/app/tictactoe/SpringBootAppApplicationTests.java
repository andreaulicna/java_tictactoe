package com.app.tictactoe;

import com.app.tictactoe.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {TicTacToeApplication.class, SecurityConfig.class})
class SpringBootAppApplicationTests {

    @Test
    void contextLoads() {
    }
}