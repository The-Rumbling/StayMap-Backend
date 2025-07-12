package com.therumbling.staymap.iam.application.internal.queryservices;

import com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories.CommunityRepository;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.domain.model.queries.GetAllUsersQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUserByIdQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUserByUsernameQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUserDetailsByIdQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUsersByCommunityIdQuery;
import com.therumbling.staymap.iam.domain.services.UserQueryService;
import com.therumbling.staymap.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link UserQueryService} interface.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    /**
     * Constructor.
     *
     * @param userRepository {@link UserRepository} instance.
     */
    public UserQueryServiceImpl(UserRepository userRepository, CommunityRepository communityRepository) {
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
    }

    /**
     * This method is used to handle {@link GetAllUsersQuery} query.
     * @param query {@link GetAllUsersQuery} instance.
     * @return {@link List} of {@link User} instances.
     * @see GetAllUsersQuery
     */
    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    /**
     * This method is used to handle {@link GetUserByIdQuery} query.
     * @param query {@link GetUserByIdQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByIdQuery
     */
    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    /**
     * This method is used to handle {@link GetUserByUsernameQuery} query.
     * @param query {@link GetUserByUsernameQuery} instance.
     * @return {@link Optional} of {@link User} instance.
     * @see GetUserByUsernameQuery
     */
    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }

    @Override
    public List<User> handle(GetUsersByCommunityIdQuery query) {
        if (!communityRepository.existsById(query.communityId()))
            throw new EntityNotFoundException("Community not found with ID: " + query.communityId());
    
        return communityRepository.findMembersByCommunityId(query.communityId());
    }

    @Override
    public Optional<User> handle(GetUserDetailsByIdQuery query) {
        return userRepository.findById(query.userId());
    }
}
