package com.therumbling.staymap.concerts.domain.model.commands;

import com.therumbling.staymap.concerts.domain.model.valueobjects.Artist;
import com.therumbling.staymap.concerts.domain.model.entities.Venue;

import java.sql.Date;

public record CreateConcertCommand(String description, String image, Date date, Venue venue, Artist artist, Long userId) {
  public CreateConcertCommand {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("description cannot be null or blank");
    }
    if (image == null || image.isBlank()) {
      throw new IllegalArgumentException("imageUrl cannot be null or blank");
    }
    if (date == null) {
      throw new IllegalArgumentException("date cannot be null");
    }
    if (venue == null || venue.getName().isBlank() || venue.getAddress().isBlank()) {
      throw new IllegalArgumentException("venueName cannot be null or blank");
    }
    if (artist == null || artist.getName().isBlank() || artist.getGenre().isBlank()) {
      throw new IllegalArgumentException("Artist cannot be null or blank");
    }
    if (userId == null || userId <= 0) {
      throw new IllegalArgumentException("User cannot be null or less than 0");
    }
  }
}
