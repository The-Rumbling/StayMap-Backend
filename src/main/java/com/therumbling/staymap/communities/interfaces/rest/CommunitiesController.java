package com.therumbling.staymap.communities.interfaces.rest;

import com.therumbling.staymap.communities.domain.model.commands.JoinCommunityCommand;
import com.therumbling.staymap.communities.domain.model.commands.LeaveCommunityCommand;
import com.therumbling.staymap.communities.domain.model.queries.GetAllCommunitiesQuery;
import com.therumbling.staymap.communities.domain.model.queries.GetCommunityByIdQuery;
import com.therumbling.staymap.communities.domain.services.CommunityCommandService;
import com.therumbling.staymap.communities.domain.services.CommunityQueryService;
import com.therumbling.staymap.communities.interfaces.rest.resources.CommunityResource;
import com.therumbling.staymap.communities.interfaces.rest.resources.CreateCommunityResource;
import com.therumbling.staymap.communities.interfaces.rest.transform.CommunityResourceFromEntityAssembler;
import com.therumbling.staymap.communities.interfaces.rest.transform.CreateCommunityCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/communities", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Communities", description = "Operations related to Communities")
public class CommunitiesController {
    private final CommunityCommandService communityCommandService;
    private final CommunityQueryService communityQueryService;

    public CommunitiesController(CommunityCommandService communityCommandService, CommunityQueryService communityQueryService) {
        this.communityCommandService = communityCommandService;
        this.communityQueryService = communityQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new community", description = "Creates a new community with the provided details.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Community created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    public ResponseEntity<CommunityResource> createCommunity(@RequestBody CreateCommunityResource communityResource) {
        var createdCommunityCommand = CreateCommunityCommandFromResourceAssembler.toCommandFromResource(communityResource);
        var community = communityCommandService.handle(createdCommunityCommand);
        if (community.getId() == null || community.getId() <= 0)
            return ResponseEntity.badRequest().build();
        var communityResponse = CommunityResourceFromEntityAssembler.toResourceFromEntity(community);
        return new ResponseEntity<>(communityResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all communities", description = "Get all communities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Communities found"),
            @ApiResponse(responseCode = "404", description = "Communities not found")})
    public ResponseEntity<List<CommunityResource>> getAllCommunities() {
        var communities = communityQueryService.handle(new GetAllCommunitiesQuery());
        if (communities.isEmpty()) return ResponseEntity.notFound().build();
        var communityResources = communities.stream()
                .map(CommunityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(communityResources);
    }

    @GetMapping("/{communityId}")
    @Operation(summary = "Get community by id", description = "Get community by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Community found"),
            @ApiResponse(responseCode = "404", description = "Community not found")})
    public ResponseEntity<CommunityResource> getCommunityById(@PathVariable Long communityId) {
        var community = communityQueryService.handle(new GetCommunityByIdQuery(communityId));
        if (community.isEmpty()) return ResponseEntity.notFound().build();
        var communityEntity = community.get();
        var communityResource = CommunityResourceFromEntityAssembler.toResourceFromEntity(communityEntity);
        return ResponseEntity.ok(communityResource);
    }

    @PostMapping("/{communityId}/join")
    @Operation(summary = "Join to community", description = "Join to community")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Join to Community successfully"),
            @ApiResponse(responseCode = "404", description = "Join to Community failed")})
    public ResponseEntity<Void> joinCommunity(@PathVariable Long communityId, @RequestParam Long userId) {
        communityCommandService.handle(new JoinCommunityCommand(communityId, userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{communityId}/leave")
    @Operation(summary = "Leave to community", description = "Leave to community")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leave to Community successfully"),
            @ApiResponse(responseCode = "404", description = "Leave to Community failed")})
    public ResponseEntity<Void> leaveCommunity(@PathVariable Long communityId, @RequestParam Long userId) {
        communityCommandService.handle(new LeaveCommunityCommand(communityId, userId));
        return ResponseEntity.ok().build();
    }
}
