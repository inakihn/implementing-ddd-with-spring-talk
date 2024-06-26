package library.catalog.infrastructure.web;

import library.catalog.application.RegisterBookCopyUseCase;
import library.catalog.domain.BarCode;
import library.catalog.domain.BookId;
import library.catalog.domain.Copy;
import library.catalog.domain.CopyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
public class CopyController {

    private final CopyRepository copyRepository;
    private final RegisterBookCopyUseCase registerBookCopyUseCase;

    public CopyController(CopyRepository copyRepository, RegisterBookCopyUseCase registerBookCopyUseCase) {
        this.copyRepository = copyRepository;
        this.registerBookCopyUseCase = registerBookCopyUseCase;
    }

    @GetMapping("/copy")
    public Collection<Copy> getCopy() {
        return (Collection<Copy>) copyRepository.findAll();
    }

    @PostMapping("/copy")
    public ResponseEntity<Object> registerBookCopy(@RequestBody RegisterBookCopyDto registerBookCopyDto) {
        registerBookCopyUseCase.execute(new BookId(UUID.fromString(registerBookCopyDto.bookId())),
                new BarCode(registerBookCopyDto.barCode()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}