package com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.aggregates.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCommunity(Community community);
}
