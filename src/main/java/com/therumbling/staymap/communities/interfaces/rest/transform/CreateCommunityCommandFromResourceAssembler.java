package com.therumbling.staymap.communities.interfaces.rest.transform;

import com.therumbling.staymap.communities.domain.model.commands.CreateCommunityCommand;
import com.therumbling.staymap.communities.interfaces.rest.resources.CreateCommunityResource;

public class CreateCommunityCommandFromResourceAssembler {
    public static CreateCommunityCommand toCommandFromResource(CreateCommunityResource resource) {
        return new CreateCommunityCommand(resource.name(), resource.description(), resource.image());
    }
}
