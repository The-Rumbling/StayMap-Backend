package com.therumbling.staymap.iam.domain.model.commands;

import com.therumbling.staymap.iam.domain.model.valueobjects.Role;

public record SignUpCommand(String username, String password, Role role, String profileImage) {
}
