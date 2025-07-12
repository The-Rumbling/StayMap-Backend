package com.therumbling.staymap.communities.interfaces.rest;

import com.therumbling.staymap.communities.domain.model.aggregates.Post;
import com.therumbling.staymap.communities.domain.model.commands.LikePostCommand;
import com.therumbling.staymap.communities.domain.model.commands.UndoLikePostCommand;
import com.therumbling.staymap.communities.domain.model.queries.GetAllPostsByCommunityQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetAllPostsQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetPostByIdQuery;
import com.therumbling.staymap.communities.domain.services.PostCommandService;
import com.therumbling.staymap.communities.domain.services.PostQueryService;
import com.therumbling.staymap.communities.interfaces.rest.resources.CreatePostResource;
import com.therumbling.staymap.communities.interfaces.rest.resources.PostResource;
import com.therumbling.staymap.communities.interfaces.rest.transform.CreatePostCommandFromResourceAssembler;
import com.therumbling.staymap.communities.interfaces.rest.transform.PostResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/posts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Posts", description = "Operations related to Posts")
public class PostsController {
    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    public PostsController(PostCommandService postCommandService, PostQueryService postQueryService) {
        this.postCommandService = postCommandService;
        this.postQueryService = postQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new post", description = "Creates a new post with the provided details.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    public ResponseEntity<PostResource> createPost(@RequestBody CreatePostResource postResource) {
        var createdPostCommand = CreatePostCommandFromResourceAssembler.toCommandFromResource(postResource);
        var post = postCommandService.handle(createdPostCommand);

        if (post.getId() == null || post.getId() <= 0) return ResponseEntity.badRequest().build();

        var postResponse = PostResourceFromEntityAssembler.toResourceFromEntity(post);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResource>> getAllPosts(@RequestParam(required = false) Long communityId) {
        List<Post> posts;

        if (communityId != null) {
            posts = postQueryService.handle(new GetAllPostsByCommunityQuery(communityId));
        } else {
            posts = postQueryService.handle(new GetAllPostsQuery());
        }

        if (posts.isEmpty()) return ResponseEntity.notFound().build();

        var postResources = posts.stream()
            .map(PostResourceFromEntityAssembler::toResourceFromEntity)
            .toList();

        return ResponseEntity.ok(postResources);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get post by id", description = "Get post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found"),
            @ApiResponse(responseCode = "404", description = "Post not found")})
    public ResponseEntity<PostResource> getPostById(@PathVariable Long postId) {
        var post = postQueryService.handle(new GetPostByIdQuery(postId));
        if (post.isEmpty()) return ResponseEntity.notFound().build();
        var postEntity = post.get();
        var postResource = PostResourceFromEntityAssembler.toResourceFromEntity(postEntity);
        return ResponseEntity.ok(postResource);
    }

    @PostMapping("/{postId}/like")
    @Operation(summary = "Like a post", description = "User likes a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post liked successfully"),
            @ApiResponse(responseCode = "404", description = "Post or user not found")
    })
    public ResponseEntity<Void> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        postCommandService.handle(new LikePostCommand(postId, userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}/like")
    @Operation(summary = "Unlike a post", description = "User removes like from a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post unliked successfully"),
            @ApiResponse(responseCode = "404", description = "Post or user not found")
    })
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        postCommandService.handle(new UndoLikePostCommand(postId, userId));
        return ResponseEntity.ok().build();
    }
}
