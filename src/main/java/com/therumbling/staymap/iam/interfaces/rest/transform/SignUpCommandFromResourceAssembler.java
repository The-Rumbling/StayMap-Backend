package com.therumbling.staymap.iam.interfaces.rest.transform;

import com.therumbling.staymap.iam.domain.model.commands.SignUpCommand;
import com.therumbling.staymap.iam.domain.model.valueobjects.Role;
import com.therumbling.staymap.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        Role role;
        try {
            role = Role.valueOf(resource.type().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid role: " + resource.type());
        }
        return new SignUpCommand(resource.username(), resource.password(), role, resource.profileImage());
    }
}
