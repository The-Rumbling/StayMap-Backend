package com.therumbling.staymap.concerts.interfaces.rest.resources;

import com.therumbling.staymap.concerts.domain.model.valueobjects.Artist;
import com.therumbling.staymap.concerts.domain.model.entities.Venue;

import java.sql.Date;

public record UpdateConcertResource(Long id, String description, String imageUrl, Date date, Venue venue, Artist artist) {

    public UpdateConcertResource {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("concertId cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("description cannot be null or blank");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("imageUrl cannot be null or blank");
        }
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        if (venue == null || venue.getName().isBlank() || venue.getAddress().isBlank()) {
            throw new IllegalArgumentException("venue cannot be null or blank");
        }
        if (artist == null || artist.getName().isBlank() || artist.getGenre().isBlank()) {
            throw new IllegalArgumentException("artist cannot be null or blank");
        }
    }

}
