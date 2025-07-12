package com.therumbling.staymap.concerts.interfaces.rest.resources;

import com.therumbling.staymap.concerts.domain.model.valueobjects.Location;

public record VenueResource(String name, String address, Location location) {
}
