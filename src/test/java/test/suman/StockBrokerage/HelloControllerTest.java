package test.suman.StockBrokerage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class HelloControllerTest {
    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    public void testHello() throws Exception {
      
    }

    @Test
    public void testCalc() throws Exception {
      
    }
}