package com.therumbling.staymap.concerts.interfaces.rest.transform;

import com.therumbling.staymap.concerts.domain.model.commands.CreateConcertCommand;
import com.therumbling.staymap.concerts.interfaces.rest.resources.CreateConcertResource;

public class CreateConcertCommandFromResourceAssembler {

    public static CreateConcertCommand toCommandFromResource(CreateConcertResource resource){
        return new CreateConcertCommand(resource.description(),
                                         resource.image(),
                                         resource.date(),
                                         VenueAssembler.toEntityFromResource(resource.venue()),
                                         resource.artist(),
                                         resource.userId());
    }
}
