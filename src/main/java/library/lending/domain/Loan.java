package library.lending.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import library.lending.domain.event.LoanClosed;
import library.lending.domain.event.LoanCreated;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Loan extends AbstractAggregateRoot<Loan> {
    @EmbeddedId
    private LoanId loanId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "copy_id"))
    private CopyId copyId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private UserId userId;
    private LocalDateTime createdAt;
    private LocalDate expectedReturnDate;
    private LocalDateTime returnedAt;

    @Version
    private Long version;

    Loan() {
    }

    public Loan(CopyId copyId, UserId userId, LoanRepository loanRepository) {
        Assert.notNull(copyId, "copyId must not be null");
        Assert.notNull(userId, "userId must not be null");

        // cool stuff, we inject the repository here, and therefore we can ensure that the copy is available on domain
        // instead of doing it on the application layer.
        Assert.isTrue(loanRepository.isAvailable(copyId), "copy with id = " + copyId + " is not available");
        this.loanId = new LoanId();
        this.copyId = copyId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.expectedReturnDate = LocalDate.now().plusDays(30);

        // This is because we extend from AbstractAggregateRoot, which has those methods to register events.
        // And the event is registered, but it will only be fired when we hit save on the repository.
        this.registerEvent(new LoanCreated(this.copyId.id()));
    }

    public void returned() {
        this.returnedAt = LocalDateTime.now();
        if (this.returnedAt.isAfter(expectedReturnDate.atStartOfDay())) {
            // TODO calculate fee
        }
        this.registerEvent(new LoanClosed(this.copyId.id()));
    }

    public LoanId getLoanId() {
        return loanId;
    }

    public CopyId getCopyId() {
        return copyId;
    }

    public UserId getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public Long getVersion() {
        return version;
    }
}