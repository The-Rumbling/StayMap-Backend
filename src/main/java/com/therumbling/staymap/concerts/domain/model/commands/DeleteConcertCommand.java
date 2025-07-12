package com.therumbling.staymap.concerts.domain.model.commands;

public record DeleteConcertCommand(Long id) {

  public DeleteConcertCommand {
    if (id == null || id <= 0) throw new IllegalArgumentException("ConcertId cannot be null or blank");
  }
}
