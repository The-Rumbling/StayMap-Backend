package com.therumbling.staymap.iam.interfaces.rest.transform;

import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.domain.model.valueobjects.Role;
import com.therumbling.staymap.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        var role = user.getRole().equals(Role.FAN) ? "Fan" : "Artist";
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), role, user.getProfileImage(), token);
    }
}
