package com.therumbling.staymap.concerts.application.internal.commandservices;

import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import com.therumbling.staymap.concerts.domain.model.commands.CancelAttendanceCommand;
import com.therumbling.staymap.concerts.domain.model.commands.ConfirmAttendanceCommand;
import com.therumbling.staymap.concerts.domain.model.commands.CreateConcertCommand;
import com.therumbling.staymap.concerts.domain.model.commands.DeleteConcertCommand;
import com.therumbling.staymap.concerts.domain.model.entities.Venue;
import com.therumbling.staymap.concerts.domain.services.ConcertCommandService;
import com.therumbling.staymap.concerts.infrastructure.persistence.jpa.respositories.ConcertRepository;
import com.therumbling.staymap.concerts.infrastructure.persistence.jpa.respositories.VenueRepository;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ConcertCommandServiceImpl implements ConcertCommandService {
    private final ConcertRepository concertRepository;
    private final VenueRepository venueRepository;
    private final UserRepository userRepository;

    public ConcertCommandServiceImpl(ConcertRepository concertRepository, VenueRepository venueRepository, UserRepository userRepository) {
        this.concertRepository = concertRepository;
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Concert handle(CreateConcertCommand command) {
        Optional<Venue> existingVenue = venueRepository.findByName(command.venue().getName());
        Optional<User> existingUser = userRepository.findById(command.userId());

        User user = existingUser.orElseThrow(() -> new RuntimeException("User not found with ID: " + command.userId()));
        Venue venue = existingVenue.orElseGet(() -> venueRepository.save(command.venue()));

        Concert concert = new Concert(command, user);
        concert.setVenue(venue);

        try {
            concert = concertRepository.save(concert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create concert: " + e.getMessage(), e);
        }

        return concert;
    }

    @Override
    public void handle(CancelAttendanceCommand command) {
        User user = userRepository.findById(command.userId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Concert concert = concertRepository.findById(command.concertId())
            .orElseThrow(() -> new RuntimeException("Concert not found"));

        user.getUpcomingConcerts().remove(concert);
        concert.getAttendees().remove(user);

        try {
            userRepository.save(user);
            concertRepository.save(concert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to cancel attendance " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(ConfirmAttendanceCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Concert concert = concertRepository.findById(command.concertId())
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        user.getUpcomingConcerts().add(concert);
        concert.getAttendees().add(user);

        try {
            userRepository.save(user);
            concertRepository.save(concert);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to confirm attendance " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteConcertCommand command) {
        if (command.id() == null) {
            throw new IllegalArgumentException("El ID del concierto no puede ser nulo.");
        }

        var concert = concertRepository.findById(command.id())
            .orElseThrow(() -> new NoSuchElementException("Concierto no encontrado con ID: " + command.id()));

        concertRepository.delete(concert);
    }
}
