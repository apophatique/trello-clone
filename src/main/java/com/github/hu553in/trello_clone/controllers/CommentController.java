package com.github.hu553in.trello_clone.controllers;

import com.github.hu553in.trello_clone.entities.Comment;
import com.github.hu553in.trello_clone.forms.comment.CreateCommentForm;
import com.github.hu553in.trello_clone.forms.comment.UpdateCommentForm;
import com.github.hu553in.trello_clone.services.comment.CommentService;
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
@RequestMapping("/board-columns/{boardColumnId}/cards/{cardId}/comments")
@Tag(name = "Comments")
public class CommentController {
    private final CommentService commentService;
    private final ServletContext servletContext;

    public CommentController(
            final CommentService commentService,
            final ServletContext servletContext
    ) {
        this.commentService = commentService;
        this.servletContext = servletContext;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Create comment",
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
                    description = "Create comment form",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateCommentForm.class, required = true)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Comment is created",
                            headers = {
                                    @Header(
                                            name = "Location",
                                            description = "Comment URI",
                                            required = true
                                    )
                            },
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
            @PathVariable final UUID cardId,
            @Valid @RequestBody final CreateCommentForm createCommentForm
    ) {
        return ResponseEntity.created(URI.create(
                servletContext
                        .getContextPath()
                        .concat("/board-columns/")
                        .concat(boardColumnId.toString())
                        .concat("/cards/")
                        .concat(cardId.toString())
                        .concat("/comments/")
                        .concat(commentService.create(boardColumnId, cardId, createCommentForm).toString())
        )).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Read all comments",
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
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Comment.class, required = true, requiredProperties = {
                                                    "id",
                                                    "createdAt",
                                                    "updatedAt"
                                            }),
                                            uniqueItems = true
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<List<Comment>> readAll(@PathVariable final UUID cardId) {
        return ResponseEntity.ok(commentService.readAll(cardId));
    }

    @GetMapping(value = "/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Read comment",
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
                    ),
                    @Parameter(
                            name = "commentId",
                            in = ParameterIn.PATH,
                            description = "Comment ID",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Comment.class, required = true)
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
    public ResponseEntity<Comment> read(
            @PathVariable final UUID cardId,
            @PathVariable("commentId") final UUID id
    ) {
        return commentService
                .read(cardId, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Update comment",
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
                    ),
                    @Parameter(
                            name = "commentId",
                            in = ParameterIn.PATH,
                            description = "Comment Id",
                            required = true
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update comment form",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateCommentForm.class, required = true)
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
            @PathVariable final UUID cardId,
            @PathVariable("commentId") final UUID id,
            @Valid @RequestBody final UpdateCommentForm updateCommentForm
    ) {
        commentService.update(boardColumnId, cardId, id, updateCommentForm);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    @Operation(
            description = "Delete comment",
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
                    ),
                    @Parameter(
                            name = "commentId",
                            in = ParameterIn.PATH,
                            description = "Comment ID",
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
    public ResponseEntity<?> delete(
            @PathVariable final UUID cardId,
            @PathVariable("commentId") final UUID id
    ) {
        commentService.delete(cardId, id);
        return ResponseEntity.ok().build();
    }
}
