package library.lending.application;

import library.common.UseCase;
import library.lending.domain.CopyId;
import library.lending.domain.Loan;
import library.lending.domain.LoanRepository;
import library.lending.domain.UserId;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class RentBookUseCase {
    private final LoanRepository loanRepository;

    public RentBookUseCase(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Transactional
    public void execute(CopyId copyId, UserId userId) {
        loanRepository.save(new Loan(copyId, userId, loanRepository));
    }
}