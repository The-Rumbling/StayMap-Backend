package com.therumbling.staymap.concerts.interfaces.rest.transform;

import com.therumbling.staymap.concerts.domain.model.entities.Venue;
import com.therumbling.staymap.concerts.interfaces.rest.resources.VenueResource;

public class VenueAssembler {
    public static VenueResource toResourceFromEntity(Venue entity) {
        return new VenueResource(entity.getName(), entity.getAddress(), entity.getLocation());
    }

    public static Venue toEntityFromResource(VenueResource resource) {
        return new Venue(resource.name(), resource.address(), resource.location().getLng(), resource.location().getLat());
    }
}
