package com.therumbling.staymap.communities.interfaces.rest.resources;

import java.util.Date;

public record PostResource(Long id, Long communityId, Long userId, String content, Date postedAt, String imageUrl) {
}
