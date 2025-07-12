package com.therumbling.staymap.communities.application.internal.queryservices;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.queries.GetAllCommunitiesQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetCommunityByIdQuery;
import com.therumbling.staymap.communities.domain.services.CommunityQueryService;
import com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories.CommunityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityQueryServiceImpl implements CommunityQueryService {
    private final CommunityRepository communityRepository;

    public CommunityQueryServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public List<Community> handle(GetAllCommunitiesQuery query) {
        return communityRepository.findAll();
    }

    @Override
    public Optional<Community> handle(GetCommunityByIdQuery query) {
        return communityRepository.findById(query.id());
    }
}
