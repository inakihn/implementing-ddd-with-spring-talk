package library.eventsourcing;

import java.util.UUID;

public record LoanClosed(UUID copyId) {
}