package com.therumbling.staymap.concerts.interfaces.rest;

import com.therumbling.staymap.concerts.domain.model.commands.CancelAttendanceCommand;
import com.therumbling.staymap.concerts.domain.model.commands.ConfirmAttendanceCommand;
import com.therumbling.staymap.concerts.domain.model.commands.DeleteConcertCommand;
import com.therumbling.staymap.concerts.domain.model.queries.GetAllConcertsQuery;
import com.therumbling.staymap.concerts.domain.model.queries.GetConcertByIdQuery;
import com.therumbling.staymap.concerts.domain.services.ConcertCommandService;
import com.therumbling.staymap.concerts.domain.services.ConcertQueryService;
import com.therumbling.staymap.concerts.interfaces.rest.resources.ConcertResource;
import com.therumbling.staymap.concerts.interfaces.rest.resources.CreateConcertResource;
import com.therumbling.staymap.concerts.interfaces.rest.transform.ConcertResourceFromEntityAssembler;
import com.therumbling.staymap.concerts.interfaces.rest.transform.CreateConcertCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/concerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Concerts", description = "Operations related to Concerts")
public class ConcertsController {
    private final ConcertCommandService concertCommandService;
    private final ConcertQueryService concertQueryService;

    public ConcertsController(ConcertCommandService concertCommandService, ConcertQueryService concertQueryService) {
        this.concertCommandService = concertCommandService;
        this.concertQueryService = concertQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new concert", description = "Creates a new concert with the provided details.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Concert created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    public ResponseEntity<ConcertResource> createConcert(@RequestBody CreateConcertResource concertResource) {
        var createdConcertCommand = CreateConcertCommandFromResourceAssembler.toCommandFromResource(concertResource);
        var concert = concertCommandService.handle(createdConcertCommand);
        if (concert.getId() == null || concert.getId() <= 0)
            return ResponseEntity.badRequest().build();
        var concertResponse = ConcertResourceFromEntityAssembler.toResourceFromEntity(concert);
        return new ResponseEntity<>(concertResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all concerts", description = "Get all concerts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concerts found"),
            @ApiResponse(responseCode = "404", description = "Concerts not found")})
    public ResponseEntity<List<ConcertResource>> getAllCommunities() {
        var concerts = concertQueryService.handle(new GetAllConcertsQuery());
        if (concerts.isEmpty()) return ResponseEntity.notFound().build();
        var concertResources = concerts.stream()
                .map(ConcertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(concertResources);
    }

    @GetMapping("/{concertId}")
    @Operation(summary = "Get concert by ID", description = "Retrieve a concert by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Concert found"),
        @ApiResponse(responseCode = "404", description = "Concert not found")})
    public ResponseEntity<ConcertResource> getConcertById(@PathVariable Long concertId) {
        var concert = concertQueryService.handle(new GetConcertByIdQuery(concertId));

        if (concert == null) return ResponseEntity.notFound().build();
        
        var concertResource = ConcertResourceFromEntityAssembler.toResourceFromEntity(concert.get());
        return ResponseEntity.ok(concertResource);
    }

    @PostMapping("/{concertId}/attend")
    @Operation(summary = "Attend to concert", description = "Attend to concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attend to concert confirmed successfully"),
            @ApiResponse(responseCode = "404", description = "Attend to concert confirmed failed")})
    public ResponseEntity<Void> confirmAttendance(@PathVariable Long concertId, @RequestParam Long userId) {
        concertCommandService.handle(new ConfirmAttendanceCommand(concertId, userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{concertId}/attend")
    @Operation(summary = "Cancel attendance to a concert", description = "Cancel attendance to a concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attend to concert cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Attend to concert callelled failed")})
    public ResponseEntity<Void> cancelAttendance(@PathVariable Long concertId, @RequestParam Long userId) {
        concertCommandService.handle(new CancelAttendanceCommand(concertId, userId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{concertId}/attendees")
    @Operation(summary = "Check if user is attending the concert", description = "Returns true if the given user is attending the specified concert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User attendance status retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    public ResponseEntity<Boolean> isUserAttendingConcert(@PathVariable Long concertId, @RequestParam Long userId) {
        Boolean isAttending = concertQueryService.checkAttendance(concertId, userId);
        return ResponseEntity.ok(isAttending);
    }

    @DeleteMapping("/{concertId}")
    @Operation(summary = "Delete concert", description = "Delete concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Concert deleted"),
            @ApiResponse(responseCode = "404", description = "Concert not found")})
    public ResponseEntity<?> deleteConcert(@PathVariable Long concertId) {
        var deleteConcertCommand = new DeleteConcertCommand(concertId);
        concertCommandService.handle(deleteConcertCommand);
        return ResponseEntity.ok("Concert with given id successfully deleted");
    }
}
