package com.therumbling.staymap.communities.interfaces.rest.transform;

import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.interfaces.rest.resources.PostResource;

public class PostResourceFromEntityAssembler {
    public static PostResource toResourceFromEntity(Post entity) {
        return new PostResource(entity.getId(), entity.getCommunity().getId(), entity.getUser().getId(), entity.getContent(), entity.getCreatedAt(), entity.getImageUrl());
    }
}
