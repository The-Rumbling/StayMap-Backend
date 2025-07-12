package com.therumbling.staymap.concerts.infrastructure.persistence.jpa.respositories;

import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " + "FROM Concert c JOIN c.attendees a " +"WHERE c.id = :concertId AND a.id = :userId")
    boolean existsUserInConcert(@Param("concertId") Long concertId, @Param("userId") Long userId);
}
