package com.therumbling.staymap.iam.domain.model.aggregates;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.concerts.domain.model.aggregates.Concert;
import com.therumbling.staymap.iam.domain.model.valueobjects.Role;
import com.therumbling.staymap.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String profileImage;

    private Role role;

    @ManyToMany(mappedBy = "members")
    private Set<Community> communitiesJoined;

    @ManyToMany(mappedBy = "attendees")
    private Set<Concert> upcomingConcerts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postsDone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Concert> createdConcerts;

    @ManyToMany
    @JoinTable(
        name = "user_likes",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> likes;

    public User() {
        this.communitiesJoined = new HashSet<Community>();
        this.upcomingConcerts = new HashSet<Concert>();
        this.postsDone = List.of();
        this.createdConcerts = List.of();
        this.likes = List.of();
    }

    public User(String username, String password, Role role, String profileImage) {
        this();
        this.username = username;
        this.password = password;
        this.role = role;
        this.profileImage = profileImage;
    }

    public User updateInformation(String username, String profileImage){
        this.username = username;
        this.profileImage = profileImage;
        return this;
    }
}
