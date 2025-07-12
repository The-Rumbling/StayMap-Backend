package com.therumbling.staymap.concerts.interfaces.rest.resources;

import com.therumbling.staymap.concerts.domain.model.valueobjects.Artist;
import com.therumbling.staymap.concerts.domain.model.valueobjects.ConcertStatus;

import java.sql.Date;
import java.util.List;

public record ConcertResource(Long id, Long userId, String description, String image, Date date, VenueResource venue, Artist artist, ConcertStatus status, List<Long> attendees) {}
