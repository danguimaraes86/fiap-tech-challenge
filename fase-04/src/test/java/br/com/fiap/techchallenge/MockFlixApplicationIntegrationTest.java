package br.com.fiap.techchallenge;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Tag("integration")
class MockFlixApplicationIntegrationTest {

    @Test
    void main() {
        MockFlixApplication.main(new String[]{});
    }

}
