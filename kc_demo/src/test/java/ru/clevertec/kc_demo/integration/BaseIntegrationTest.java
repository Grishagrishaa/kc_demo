package ru.clevertec.kc_demo.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.kc_demo.integration.annotation.IT;

import java.util.UUID;

@IT
public abstract class BaseIntegrationTest {

    public static final UUID EMPLOYEE_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public static final int COUNT_OF_EMPLOYEES = 2;



    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.7");

    @BeforeAll
    static void runContainer(){
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }

    protected static String camelToSnake(String str){
        return str.replaceAll("([A-Z][a-z])", "_$1").toLowerCase();
    }
}
