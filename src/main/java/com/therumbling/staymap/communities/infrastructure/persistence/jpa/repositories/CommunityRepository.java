package com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.iam.domain.model.aggregates.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    boolean existsByName(String name);

    @Query("SELECT c.members FROM Community c WHERE c.id = :id")
    List<User> findMembersByCommunityId(@Param("id") Long communityId);
}
