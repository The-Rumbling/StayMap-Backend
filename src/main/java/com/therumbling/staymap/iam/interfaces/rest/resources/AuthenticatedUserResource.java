package com.therumbling.staymap.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String username, String type, String profileImage, String token) {

}
