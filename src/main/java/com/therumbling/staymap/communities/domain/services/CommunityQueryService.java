package com.therumbling.staymap.communities.domain.services;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.queries.GetAllCommunitiesQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetCommunityByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CommunityQueryService {
    List<Community> handle(GetAllCommunitiesQuery query);

    Optional<Community> handle(GetCommunityByIdQuery query);
}
