package com.therumbling.staymap.concerts.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Artist {
  private String name;
  private String genre;

  public Artist() {}

  public Artist(String name, String genre) {
    this.name = name;
    this.genre = genre;
  }
}