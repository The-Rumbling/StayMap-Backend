package com.therumbling.staymap.communities.domain.model.aggregates;

import com.therumbling.staymap.communities.domain.model.commands.CreateCommunityCommand;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
public class Community extends AuditableAbstractAggregateRoot<Community> {
    private String name;

    private String imageUrl;

    private String description;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "community_members",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members;

    public Community() {
        this.posts = List.of();
        this.members = new HashSet<>();
    }

    public Community(CreateCommunityCommand command) {
        this();
        name = command.name();
        imageUrl = command.imageUrl();
        description = command.description();
    }
}
