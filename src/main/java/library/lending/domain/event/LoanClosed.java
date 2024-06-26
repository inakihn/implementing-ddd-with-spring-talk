package library.lending.domain.event;

import java.util.UUID;

public record LoanClosed(UUID copyId) {
}