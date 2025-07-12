package com.therumbling.staymap.communities.interfaces.rest.resources;

public record CreatePostResource(Long communityId, Long userId, String content, String imageUrl) {
}
