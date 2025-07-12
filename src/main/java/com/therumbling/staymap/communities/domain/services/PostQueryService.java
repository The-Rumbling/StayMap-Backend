package com.therumbling.staymap.communities.domain.services;

import java.util.List;
import java.util.Optional;

import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.domain.model.queries.GetAllPostsQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetAllPostsByCommunityQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetPostByIdQuery;

public interface PostQueryService {
    List<Post> handle(GetAllPostsQuery query);

    Optional<Post> handle(GetPostByIdQuery query);

    List<Post> handle(GetAllPostsByCommunityQuery query);
}
