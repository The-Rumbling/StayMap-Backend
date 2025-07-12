package com.therumbling.staymap.concerts.domain.model.entities;

import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import com.therumbling.staymap.concerts.domain.model.valueobjects.Location;
import com.therumbling.staymap.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Venue extends AuditableModel {
  private String name;

  private String address;

  @Embedded
  private Location location;

  @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Concert> concerts;

  public Venue() { concerts = List.of(); }

  public Venue(String name, String address, double lng, double lat) {
    this();
    this.name = name;
    this.address = address;
    this.location = new Location(lat, lng);
  }
}
