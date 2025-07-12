package com.therumbling.staymap.iam.interfaces.rest.resources;

import java.util.List;

import com.therumbling.staymap.concerts.interfaces.rest.resources.ConcertResource;

public record UserDetailsResource(Long id, String username, String type, String profileImage, List<Long> communitiesJoined, List<ConcertResource> upcomingConcerts, List<Long> postsDone, List<Long> createdConcerts) {
    
}
