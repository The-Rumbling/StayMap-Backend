package com.therumbling.staymap.communities.domain.services;

import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.domain.model.commands.CreatePostCommand;
import com.therumbling.staymap.communities.domain.model.commands.LikePostCommand;
import com.therumbling.staymap.communities.domain.model.commands.UndoLikePostCommand;

public interface PostCommandService {
    Post handle(CreatePostCommand command);

    void handle(LikePostCommand command);
    
    void handle(UndoLikePostCommand command);
}
