package library;

import library.catalog.application.AddBookToCatalogUseCase;
import library.catalog.domain.BookRepository;
import library.catalog.domain.Isbn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
class LibraryApplicationTests {

    private final AddBookToCatalogUseCase addBookToCatalogUseCase;
    private final BookRepository bookRepository;

    static final PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
        POSTGRES.withReuse(true)
                .withLabel("reuse.UUID", "41c3ccbc-34b6-46f4-892d-ed05379348c2");
        POSTGRES.start();
    }

    @Autowired
    LibraryApplicationTests(AddBookToCatalogUseCase addBookToCatalogUseCase,
                            BookRepository bookRepository) {
        this.addBookToCatalogUseCase = addBookToCatalogUseCase;
        this.bookRepository = bookRepository;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void exampleIntegrationTest() {
        // normal user interaction
        addBookToCatalogUseCase.execute(new Isbn("9781617294945"));

        // we verify for the test that the book was added
        bookRepository.findAll().forEach(book -> {
            System.out.println(book.getId() + " - " + book.getTitle() + " - " + book.getIsbn().value());
        });
    }

}