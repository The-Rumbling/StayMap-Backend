package com.therumbling.staymap.communities.interfaces.rest.transform;

import com.therumbling.staymap.communities.domain.model.aggregates.Community;
import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.interfaces.rest.resources.CommunityResource;
import com.therumbling.staymap.iam.domain.model.aggregates.User;

import java.util.List;
import java.util.Set;

public class CommunityResourceFromEntityAssembler {
    private static List<Long> getPostIds(List<Post> posts) {
        return posts.stream().map(Post::getId).toList();
    }

    private static List<Long> getMemberIds(Set<User> members) {
        return members.stream().toList().stream().map(User::getId).toList();
    }

    public static CommunityResource toResourceFromEntity(Community entity) {
        return new CommunityResource(entity.getId(), entity.getName(), entity.getDescription(), entity.getImageUrl(), getPostIds(entity.getPosts()), getMemberIds(entity.getMembers()));
    }
}
