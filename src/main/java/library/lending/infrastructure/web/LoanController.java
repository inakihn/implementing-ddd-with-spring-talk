package library.lending.infrastructure.web;

import library.lending.application.RentBookUseCase;
import library.lending.application.ReturnBookUseCase;
import library.lending.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
public class LoanController {

    private final RentBookUseCase rentBookUseCase;
    private final ReturnBookUseCase returnBookUseCase;
    private final LoanRepository loanRepository;

    public LoanController(RentBookUseCase rentBookUseCase,
                          ReturnBookUseCase returnBookUseCase, LoanRepository loanRepository) {
        this.rentBookUseCase = rentBookUseCase;
        this.returnBookUseCase = returnBookUseCase;
        this.loanRepository = loanRepository;
    }

    @PostMapping("/loan/rent")
    public ResponseEntity<Void> rentBook(@RequestBody RentBookDto rentBookDto) {
        // rent book
        rentBookUseCase.execute(new CopyId(UUID.fromString(rentBookDto.copyId())),
                new UserId(UUID.fromString(rentBookDto.userId())));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/loan/return")
    public ResponseEntity<Void> returnBook(@RequestBody LoanId loanId) {
        // return book
        returnBookUseCase.execute(loanId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/loan")
    public Collection<Loan> getLoans(@RequestParam(required = false) Boolean active) {
        // if active, query only for active loans
        if (Boolean.TRUE.equals(active)) {
            return loanRepository.findAllByReturnedAtIsNull();
        }

        // get all loans
        return (Collection<Loan>) loanRepository.findAll();
    }
}