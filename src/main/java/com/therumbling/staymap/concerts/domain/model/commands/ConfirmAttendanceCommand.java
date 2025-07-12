package com.therumbling.staymap.concerts.domain.model.commands;

public record ConfirmAttendanceCommand(Long concertId, Long userId) {
    
}
