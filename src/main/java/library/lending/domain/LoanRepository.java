package library.lending.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, LoanId> {

    @Query("""
            select count(*) = 0 
            from Loan 
            where copyId = :id 
                and returnedAt is null
            """)
    boolean isAvailable(CopyId id);

    default Loan findByIdOrThrow(LoanId loanId) {
        return findById(loanId).orElseThrow();
    }

    List<Loan> findAllByReturnedAtIsNull();
}