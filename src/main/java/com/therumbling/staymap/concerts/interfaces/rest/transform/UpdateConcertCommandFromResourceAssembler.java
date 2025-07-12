package com.therumbling.staymap.concerts.interfaces.rest.transform;

import com.therumbling.staymap.concerts.domain.model.commands.UpdateConcertCommand;
import com.therumbling.staymap.concerts.interfaces.rest.resources.UpdateConcertResource;

public class UpdateConcertCommandFromResourceAssembler {

    public static UpdateConcertCommand toCommandFromResource(UpdateConcertResource resource){
        return new UpdateConcertCommand(resource.description(),
                                        resource.imageUrl(),
                                        resource.date(),
                                        resource.venue(),
                                        resource.artist());
    }
}
