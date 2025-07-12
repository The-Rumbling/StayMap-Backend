package com.therumbling.staymap.concerts.interfaces.rest.resources;

import com.therumbling.staymap.concerts.domain.model.valueobjects.Artist;

import java.sql.Date;

public record CreateConcertResource(String description, String image, Date date, VenueResource venue, Artist artist, Long userId) {
    public CreateConcertResource {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (image == null || image.isBlank()) {
            throw new IllegalArgumentException("imageUrl cannot be null or blank");
        }
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        if (venue == null || venue.name().isBlank() || venue.address().isBlank()) {
            throw new IllegalArgumentException("venue cannot be null or blank");
        }
        if (artist == null || artist.getName().isBlank() || artist.getGenre().isBlank()) {
            throw new IllegalArgumentException("artist cannot be null or blank");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId must be greater than 0");
        }
    }
}
