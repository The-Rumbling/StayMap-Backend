package com.therumbling.staymap.communities.domain.model.commands;

public record UndoLikePostCommand(Long postId, Long userId ) {
    
}
