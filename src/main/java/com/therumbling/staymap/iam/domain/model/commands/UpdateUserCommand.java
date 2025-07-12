package com.therumbling.staymap.iam.domain.model.commands;

public record UpdateUserCommand(Long userId, String username, String profileImage) {
    
}
