package com.therumbling.staymap.communities.application.internal.queryservices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.domain.model.queries.GetAllPostsByCommunityQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetAllPostsQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetPostByIdQuery;
import com.therumbling.staymap.communities.domain.services.PostQueryService;
import com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories.CommunityRepository;
import com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories.PostRepository;

@Service
public class PostQueryServiceImpl implements PostQueryService {
    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;

    public PostQueryServiceImpl(PostRepository postRepository, CommunityRepository communityRepository) {
        this.postRepository = postRepository;
        this.communityRepository = communityRepository;
    }

    @Override
    public List<Post> handle(GetAllPostsQuery query) {
        return postRepository.findAll(); 
    }

    @Override
    public Optional<Post> handle(GetPostByIdQuery query) {
        return postRepository.findById(query.id());
    }

    @Override
    public List<Post> handle(GetAllPostsByCommunityQuery query) {
        Community community = communityRepository.findById(query.communityId())
            .orElseThrow(() -> new RuntimeException("Community not found"));

        return postRepository.findAllByCommunity(community);
    }
}
