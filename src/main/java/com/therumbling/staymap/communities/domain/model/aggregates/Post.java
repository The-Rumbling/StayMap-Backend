package com.therumbling.staymap.communities.domain.model.aggregates;

import java.util.List;

import com.therumbling.staymap.communities.domain.model.commands.CreatePostCommand;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Post extends AuditableAbstractAggregateRoot<Post> {
    private String content;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "likes")
    private List<User> likedBy;

    public Post() {likedBy = List.of();}

    public Post(CreatePostCommand command, Community community, User user) {
        content = command.content();
        imageUrl = command.imageUrl();
        this.community = community;
        this.user = user;
    }
}
