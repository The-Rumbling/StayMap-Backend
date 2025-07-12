package com.therumbling.staymap.concerts.infrastructure.persistence.jpa.respositories;

import com.therumbling.staymap.concerts.domain.model.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    boolean existsByName(String name);
    Optional<Venue> findByName(String name);
}
