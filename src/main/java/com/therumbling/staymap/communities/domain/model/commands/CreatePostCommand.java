package com.therumbling.staymap.communities.domain.model.commands;

public record CreatePostCommand(String content, String imageUrl, Long communityId, Long userId) {
}
