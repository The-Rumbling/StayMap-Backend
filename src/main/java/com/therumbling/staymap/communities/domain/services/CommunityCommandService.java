package com.therumbling.staymap.communities.domain.services;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.commands.CreateCommunityCommand;
import com.therumbling.staymap.communities.domain.model.commands.JoinCommunityCommand;
import com.therumbling.staymap.communities.domain.model.commands.LeaveCommunityCommand;

public interface CommunityCommandService {
    Community handle(CreateCommunityCommand command);

    void handle(JoinCommunityCommand command);

    void handle(LeaveCommunityCommand command);
}
