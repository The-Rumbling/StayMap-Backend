package com.therumbling.staymap.communities.interfaces.rest.transform;

import com.therumbling.staymap.communities.domain.model.commands.CreatePostCommand;
import com.therumbling.staymap.communities.interfaces.rest.resources.CreatePostResource;

public class CreatePostCommandFromResourceAssembler {
    public static CreatePostCommand toCommandFromResource(CreatePostResource resource) {
        if(resource.communityId() == null || resource.communityId() <= 0) throw new IllegalArgumentException("CreatePostResource: Invalid communityId");
        if(resource.userId() == null || resource.userId() <= 0) throw new IllegalArgumentException("CreatePostResource: Invalid userId");
        if(resource.content() == null || resource.content().isBlank()) throw new IllegalArgumentException("CreatePostResource: Invalid content");

        return new CreatePostCommand(resource.content(), resource.imageUrl(), resource.communityId(), resource.userId());
    }
}