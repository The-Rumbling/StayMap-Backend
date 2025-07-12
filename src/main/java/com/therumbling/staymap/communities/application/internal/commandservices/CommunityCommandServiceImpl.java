package com.therumbling.staymap.communities.application.internal.commandservices;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.commands.CreateCommunityCommand;
import com.therumbling.staymap.communities.domain.model.commands.JoinCommunityCommand;
import com.therumbling.staymap.communities.domain.model.commands.LeaveCommunityCommand;
import com.therumbling.staymap.communities.domain.services.CommunityCommandService;
import com.therumbling.staymap.communities.infrastructure.persistence.jpa.repositories.CommunityRepository;
import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;
 
@Service
public class CommunityCommandServiceImpl implements CommunityCommandService {
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityCommandServiceImpl(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Community handle(CreateCommunityCommand command) {
        if (communityRepository.existsByName(command.name())) throw new IllegalArgumentException("Community with name " + command.name() + " already exists");

        var community = new Community(command);

        try {
            communityRepository.save(community);
        } catch (Exception e){
            throw new IllegalArgumentException("Failed to create community " + e.getMessage());
        }

        return community;
    }

    @Override
    public void handle(JoinCommunityCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Community community = communityRepository.findById(command.communityId())
                .orElseThrow(() -> new RuntimeException("Community not found"));

        community.getMembers().add(user);
        user.getCommunitiesJoined().add(community);

        try {
            communityRepository.save(community);
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to join community " + e.getMessage());
        }
    }
 
    @Override
    public void handle(LeaveCommunityCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Community community = communityRepository.findById(command.communityId())
                .orElseThrow(() -> new RuntimeException("Community not found"));

        community.getMembers().remove(user);
        user.getCommunitiesJoined().remove(community);

        try {
            communityRepository.save(community);
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to leave community " + e.getMessage());
        }
    }
}
