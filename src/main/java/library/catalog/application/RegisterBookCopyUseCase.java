package library.catalog.application;

import jakarta.validation.constraints.NotNull;
import library.UseCase;
import library.catalog.domain.BarCode;
import library.catalog.domain.BookId;
import library.catalog.domain.Copy;
import library.catalog.domain.CopyRepository;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class RegisterBookCopyUseCase {
    private final CopyRepository copyRepository;

    public RegisterBookCopyUseCase(CopyRepository copyRepository) {
        this.copyRepository = copyRepository;
    }

    @Transactional
    public void execute(@NotNull BookId bookId, @NotNull BarCode barCode) {
        copyRepository.save(new Copy(bookId, barCode));
    }
}