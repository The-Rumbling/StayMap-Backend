package com.therumbling.staymap.iam.interfaces.rest.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.therumbling.staymap.concerts.interfaces.rest.transform.ConcertResourceFromEntityAssembler;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.domain.model.valueobjects.Role;
import com.therumbling.staymap.iam.interfaces.rest.resources.UserDetailsResource;

public class UserDetailsResourceFromEntityAssembler {
    private static <T> List<Long> toIdListFromSet(Set<T> set, Function<T, Long> idExtractor) {
        if (set == null || set.isEmpty()) {
            return List.of();
        }

        return set.stream()
                .map(idExtractor)
                .toList();
    }

    private static <T> List<Long> toIdListFromList(List<T> list, Function<T, Long> idExtractor) {
        if (list == null || list.isEmpty()) {
            return List.of();
        }

        return list.stream()
                .map(idExtractor)
                .toList();
    }

    public static UserDetailsResource toResourceFromEntity(User entity) {
        var role = entity.getRole().equals(Role.FAN) ? "Fan" : "Artist";

        return new UserDetailsResource(
            entity.getId(),
            entity.getUsername(),
            role,
            entity.getProfileImage(),
            toIdListFromSet(entity.getCommunitiesJoined(), c -> c.getId()),
            ConcertResourceFromEntityAssembler.toResourcesFromEntities(new ArrayList<>(entity.getUpcomingConcerts())),
            toIdListFromList(entity.getPostsDone(), p -> p.getId()),
            toIdListFromList(entity.getCreatedConcerts(), c -> c.getId())
        );
    }
}
