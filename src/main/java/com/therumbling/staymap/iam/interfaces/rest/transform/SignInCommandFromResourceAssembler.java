package com.therumbling.staymap.iam.interfaces.rest.transform;

import com.therumbling.staymap.iam.domain.model.commands.SignInCommand;
import com.therumbling.staymap.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
