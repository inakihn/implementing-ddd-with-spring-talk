package library.eventsourcing;

import library.lending.domain.CopyId;

import java.util.UUID;

public record LoanClosed(UUID copyId) {
}