package com.github.hu553in.trello_clone.controllers;

import com.github.hu553in.trello_clone.entities.Card;
import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import com.github.hu553in.trello_clone.forms.card.CreateCardForm;
import com.github.hu553in.trello_clone.forms.card.UpdateCardForm;
import com.github.hu553in.trello_clone.services.card.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/board-columns/{boardColumnId}/cards")
@Tag(name = "Cards")
public class CardController {
    private final CardService cardService;
    private final ServletContext servletContext;

    public CardController(final CardService cardService, final ServletContext servletContext) {
        this.cardService = cardService;
        this.servletContext = servletContext;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Create card",
            parameters = {@Parameter(
                    name = "boardColumnId",
                    in = ParameterIn.PATH,
                    description = "Board column ID",
                    required = true
            )},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create card form",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateCardForm.class, required = true)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Card is created",
                            headers = {@Header(
                                    name = "Location",
                                    description = "Card URI",
                                    required = true
                            )},
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters or body",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(ref = "ErrorMap", required = true)
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<?> create(
            @PathVariable final UUID boardColumnId,
            @Valid @RequestBody final CreateCardForm createCardForm
    ) throws CustomMethodArgumentNotValidException {
        return ResponseEntity.created(URI.create(
                servletContext
                        .getContextPath()
                        .concat("/board-columns/")
                        .concat(boardColumnId.toString())
                        .concat("/cards/")
                        .concat(cardService.create(boardColumnId, createCardForm).toString())
        )).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Read all cards",
            parameters = {@Parameter(
                    name = "boardColumnId",
                    in = ParameterIn.PATH,
                    description = "Board column ID",
                    required = true
            )},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Card.class, required = true),
                                            uniqueItems = true
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<List<Card>> readAll(@PathVariable final UUID boardColumnId) {
        return ResponseEntity.ok(cardService.readAll(boardColumnId));
    }

    @GetMapping(value = "/{cardId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Read card",
            parameters = {
                    @Parameter(
                            name = "boardColumnId",
                            in = ParameterIn.PATH,
                            description = "Board column ID",
                            required = true
                    ),
                    @Parameter(
                            name = "cardId",
                            in = ParameterIn.PATH,
                            description = "Card ID",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Card.class, required = true)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters or body",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(ref = "ErrorMap", required = true)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Entity is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<Card> read(
            @PathVariable final UUID boardColumnId,
            @PathVariable("cardId") final UUID id
    ) {
        return cardService
                .read(boardColumnId, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{cardId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Update card",
            parameters = {
                    @Parameter(
                            name = "boardColumnId",
                            in = ParameterIn.PATH,
                            description = "Board column ID",
                            required = true
                    ),
                    @Parameter(
                            name = "cardId",
                            in = ParameterIn.PATH,
                            description = "Card ID",
                            required = true
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update card form",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateCardForm.class, required = true)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters or body",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(ref = "ErrorMap", required = true)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Entity is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<?> update(
            @PathVariable final UUID boardColumnId,
            @PathVariable("cardId") final UUID id,
            @Valid @RequestBody final UpdateCardForm updateCardForm
    ) throws CustomMethodArgumentNotValidException {
        cardService.update(boardColumnId, id, updateCardForm);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cardId}")
    @Operation(
            description = "Delete card",
            parameters = {
                    @Parameter(
                            name = "boardColumnId",
                            in = ParameterIn.PATH,
                            description = "Board column ID",
                            required = true
                    ),
                    @Parameter(
                            name = "cardId",
                            in = ParameterIn.PATH,
                            description = "Card ID",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters or body",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(ref = "ErrorMap", required = true)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Entity is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<?> delete(@PathVariable final UUID boardColumnId, @PathVariable("cardId") final UUID id) {
        cardService.delete(boardColumnId, id);
        return ResponseEntity.ok().build();
    }
}
