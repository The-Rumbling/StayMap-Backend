package com.therumbling.staymap.iam.interfaces.rest.transform;

import com.therumbling.staymap.iam.domain.model.aggregates.User;
import com.therumbling.staymap.iam.domain.model.valueobjects.Role;
import com.therumbling.staymap.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        String role = user.getRole().equals(Role.FAN) ? "Fan" : "Artist"; 
        return new UserResource(user.getId(), user.getUsername(), role, user.getProfileImage());
    }
}
