package com.therumbling.staymap.concerts.domain.model.aggregates;

import com.therumbling.staymap.concerts.domain.model.commands.CreateConcertCommand;
import com.therumbling.staymap.concerts.domain.model.commands.UpdateConcertCommand;
import com.therumbling.staymap.concerts.domain.model.valueobjects.Artist;
import com.therumbling.staymap.concerts.domain.model.valueobjects.ConcertStatus;
import com.therumbling.staymap.concerts.domain.model.entities.Venue;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Concert extends AuditableAbstractAggregateRoot<Concert> {
  @Size(max = 1000)
  private String description;

  private Date date;

  private ConcertStatus status;
  
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "venue_id", nullable = false)
  private Venue venue;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "concert_attendees", joinColumns = @JoinColumn(name = "concert_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> attendees;

  @Embedded
  private Artist artist;

  public Concert() {
    this.attendees = new HashSet<>();
  }

  public Concert(CreateConcertCommand command, User user) {
    this.description = command.description();
    this.date = command.date();
    this.imageUrl = command.image();
    this.status =  ConcertStatus.AVAILABLE;
    this.venue = command.venue();
    this.artist = command.artist();
    this.user = user;
  }

  public Concert updateInformation(UpdateConcertCommand command) {
    this.description = command.description();
    this.date = command.date();
    this.imageUrl = command.imageUrl();
    this.venue = command.venue();
    this.artist = command.artist();
    return this;
  }
}