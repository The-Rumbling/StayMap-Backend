package com.therumbling.staymap.concerts.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Location {
  private double lat;
  private double lng;

  public Location() {}

  public Location(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }
}