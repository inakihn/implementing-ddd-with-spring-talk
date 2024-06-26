package library.lending.domain.event;

import java.util.UUID;

public record LoanCreated(UUID copyId) {
}