package com.therumbling.staymap.iam.domain.services;

import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.domain.model.queries.GetAllUsersQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUserByIdQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUserByUsernameQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUserDetailsByIdQuery;
import com.therumbling.staymap.iam.domain.model.queries.GetUsersByCommunityIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * User query service
 * <p>
 *     This interface represents the service to handle user queries.
 * </p>
 */
public interface UserQueryService {
    /**
     * Handle get all users query
     * @param query the {@link GetAllUsersQuery} query
     * @return a list of {@link User} entities
     */
    List<User> handle(GetAllUsersQuery query);

    /**
     * Handle get user by id query
     * @param query the {@link GetUserByIdQuery} query
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handle get user by username query
     * @param query the {@link GetUserByUsernameQuery} query
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(GetUserByUsernameQuery query);

    List<User> handle(GetUsersByCommunityIdQuery query);

    Optional<User> handle(GetUserDetailsByIdQuery query);
}
