package com.therumbling.staymap.concerts.domain.model.commands;

public record CancelAttendanceCommand(Long concertId, Long userId) {
}
