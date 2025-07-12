package com.therumbling.staymap.iam.interfaces.rest.transform;

import com.therumbling.staymap.iam.domain.model.commands.UpdateUserCommand;
import com.therumbling.staymap.iam.interfaces.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(UpdateUserResource resource, Long userId) {
        return new UpdateUserCommand(userId, resource.username(), resource.profileImage());
    }
}
