package library.catalog.infrastructure.web;

import library.catalog.application.AddBookToCatalogUseCase;
import library.catalog.domain.Book;
import library.catalog.domain.BookRepository;
import library.catalog.domain.Isbn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class BookController {

    private final AddBookToCatalogUseCase addBookToCatalogUseCase;
    private final BookRepository bookRepository;

    public BookController(AddBookToCatalogUseCase addBookToCatalogUseCase,
                          BookRepository bookRepository) {
        this.addBookToCatalogUseCase = addBookToCatalogUseCase;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/book")
    public ResponseEntity<Void> addBookToCatalog(@RequestBody Isbn isbn) {
        // add book
        addBookToCatalogUseCase.execute(isbn);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/book")
    public Collection<Book> getBooks() {
        return (Collection<Book>) bookRepository.findAll();
    }
}