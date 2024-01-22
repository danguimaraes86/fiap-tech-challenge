package br.com.fiap.techchallenge.integrationTest;

import br.com.fiap.techchallenge.MockFlixApplication;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Tag("integration")
class MockFlixApplicationIT {

    @Test
    void main() {
        MockFlixApplication.main(new String[]{});
    }

}
