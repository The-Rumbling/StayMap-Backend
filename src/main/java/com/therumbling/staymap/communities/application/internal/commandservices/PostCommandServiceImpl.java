package com.therumbling.staymap.communities.application.internal.commandservices;

import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.domain.model.commands.CreatePostCommand;
import com.therumbling.staymap.communities.domain.model.commands.LikePostCommand;
import com.therumbling.staymap.communities.domain.model.commands.UndoLikePostCommand;
import com.therumbling.staymap.communities.domain.services.PostCommandService;
import com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories.CommunityRepository;
import com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories.PostRepository;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class PostCommandServiceImpl implements PostCommandService {
    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostCommandServiceImpl(CommunityRepository communityRepository, PostRepository postRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post handle(CreatePostCommand command) {
        var community = communityRepository.findById(command.communityId())
        .orElseThrow(() -> new IllegalArgumentException("Community with id " + command.communityId() + " does not exist"));

        var user = userRepository.findById(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("User with id " + command.userId() + " does not exist"));

        var post = new Post(command, community, user);

        try {
            return postRepository.save(post);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create post: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(LikePostCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(command.postId())
                .orElseThrow(() -> new RuntimeException("Community not found"));

        post.getLikedBy().add(user);
        user.getLikes().add(post);

        try {
            postRepository.save(post);
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to leave community " + e.getMessage());
        }
    }

    @Override
    public void handle(UndoLikePostCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(command.postId())
                .orElseThrow(() -> new RuntimeException("Community not found"));

        post.getLikedBy().remove(user);
        user.getLikes().remove(post);

        try {
            postRepository.save(post);
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to leave community " + e.getMessage());
        }
    }
}
