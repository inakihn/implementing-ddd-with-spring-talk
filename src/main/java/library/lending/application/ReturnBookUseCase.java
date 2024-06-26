package library.lending.application;

import library.common.UseCase;
import library.lending.domain.Loan;
import library.lending.domain.LoanId;
import library.lending.domain.LoanRepository;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class ReturnBookUseCase {

    private final LoanRepository loanRepository;

    public ReturnBookUseCase(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Transactional
    public void execute(LoanId loanId) {
        Loan loan = loanRepository.findByIdOrThrow(loanId);
        loan.returned();
        loanRepository.save(loan);
    }
}