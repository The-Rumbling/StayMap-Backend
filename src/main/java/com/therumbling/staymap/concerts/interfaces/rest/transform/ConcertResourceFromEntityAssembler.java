package com.therumbling.staymap.concerts.interfaces.rest.transform;

import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import com.therumbling.staymap.concerts.interfaces.rest.resources.ConcertResource;
import com.therumbling.staymap.iam.domain.model.aggregates.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConcertResourceFromEntityAssembler {

    private static List<Long> toListFromHashSet(Set<User> attendees) {
        if (attendees == null) return List.of();
        return attendees.stream().map(User::getId).collect(Collectors.toList());
    }

    public static ConcertResource toResourceFromEntity(Concert entity) {
        return new ConcertResource(
                entity.getId(),
                entity.getUser().getId(),
                entity.getDescription(),
                entity.getImageUrl(),
                entity.getDate(),
                VenueAssembler.toResourceFromEntity(entity.getVenue()),
                entity.getArtist(),
                entity.getStatus(),
                toListFromHashSet(entity.getAttendees())
        );
    }

    public static List<ConcertResource> toResourcesFromEntities(List<Concert> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(ConcertResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
    }
}
