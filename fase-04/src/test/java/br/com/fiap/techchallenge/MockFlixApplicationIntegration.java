package br.com.fiap.techchallenge;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Tag("integration")
class MockFlixApplicationIntegration {

    @Test
    void main() {
        MockFlixApplication.main(new String[]{});
    }

}
