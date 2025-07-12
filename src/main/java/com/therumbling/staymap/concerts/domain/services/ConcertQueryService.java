package com.therumbling.staymap.concerts.domain.services;

import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import com.therumbling.staymap.concerts.domain.model.queries.GetAllConcertsQuery;
import com.therumbling.staymap.concerts.domain.model.queries.GetConcertByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ConcertQueryService {
  List<Concert> handle(GetAllConcertsQuery query);

  Optional<Concert> handle(GetConcertByIdQuery query);

  Boolean checkAttendance(Long concertId, Long userId);
}
