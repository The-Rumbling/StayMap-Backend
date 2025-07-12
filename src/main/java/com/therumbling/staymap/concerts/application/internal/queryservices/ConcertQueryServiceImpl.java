package com.therumbling.staymap.concerts.application.internal.queryservices;

import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import com.therumbling.staymap.concerts.domain.model.queries.GetAllConcertsQuery;
import com.therumbling.staymap.concerts.domain.model.queries.GetConcertByIdQuery;
import com.therumbling.staymap.concerts.domain.services.ConcertQueryService;
import com.therumbling.staymap.concerts.infrastructure.persistence.jpa.respositories.ConcertRepository;
import com.therumbling.staymap.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertQueryServiceImpl implements ConcertQueryService {
    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    public ConcertQueryServiceImpl(ConcertRepository concertRepository, UserRepository userRepository) {
        this.concertRepository = concertRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Concert> handle(GetAllConcertsQuery query) {
        return concertRepository.findAll();
    }

    @Override
    public Optional<Concert> handle(GetConcertByIdQuery query) {
        return concertRepository.findById(query.id());
    }

    @Override
    public Boolean checkAttendance(Long concertId, Long userId) {
        if (!concertRepository.existsById(concertId)) {
            throw new EntityNotFoundException("Concert with ID " + concertId + " does not exist.");
        }

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with ID " + userId + " does not exist.");
        }

        return concertRepository.existsUserInConcert(concertId, userId);
    }
}
