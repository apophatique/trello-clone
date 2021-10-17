package com.github.hu553in.trello_clone.controllers;

import com.github.hu553in.trello_clone.entities.BoardColumn;
import com.github.hu553in.trello_clone.exceptions.CustomMethodArgumentNotValidException;
import com.github.hu553in.trello_clone.forms.board_column.CreateBoardColumnForm;
import com.github.hu553in.trello_clone.forms.board_column.UpdateBoardColumnForm;
import com.github.hu553in.trello_clone.services.board_column.BoardColumnService;
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
@RequestMapping("/board-columns")
@Tag(name = "Board columns")
public class BoardColumnController {
    private final BoardColumnService boardColumnService;
    private final ServletContext servletContext;

    public BoardColumnController(
            final BoardColumnService boardColumnService,
            final ServletContext servletContext
    ) {
        this.boardColumnService = boardColumnService;
        this.servletContext = servletContext;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Create board column",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Create board column form",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateBoardColumnForm.class, required = true)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Board column is created",
                            headers = {
                                    @Header(
                                            name = "Location",
                                            description = "Board column URI",
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
            @Valid @RequestBody final CreateBoardColumnForm createBoardColumnForm
    ) throws CustomMethodArgumentNotValidException {
        return ResponseEntity.created(URI.create(
                servletContext
                        .getContextPath()
                        .concat("/board-columns/")
                        .concat(boardColumnService.create(createBoardColumnForm).toString())
        )).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Read all board columns",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = BoardColumn.class, required = true),
                                            uniqueItems = true
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    public ResponseEntity<List<BoardColumn>> readAll() {
        return ResponseEntity.ok(boardColumnService.readAll());
    }

    @GetMapping(value = "/{boardColumnId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Read board column",
            parameters = {
                    @Parameter(
                            name = "boardColumnId",
                            in = ParameterIn.PATH,
                            description = "Board column ID",
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BoardColumn.class, required = true)
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
    public ResponseEntity<BoardColumn> read(@PathVariable("boardColumnId") final UUID id) {
        return boardColumnService
                .read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{boardColumnId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Update board column",
            parameters = {
                    @Parameter(
                            name = "boardColumnId",
                            in = ParameterIn.PATH,
                            description = "Board column ID",
                            required = true
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update board column form",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateBoardColumnForm.class, required = true)
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
            @PathVariable("boardColumnId") final UUID id,
            @Valid @RequestBody final UpdateBoardColumnForm updateBoardColumnForm
    ) throws CustomMethodArgumentNotValidException {
        boardColumnService.update(id, updateBoardColumnForm);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardColumnId}")
    @Operation(
            description = "Delete board column",
            parameters = {
                    @Parameter(
                            name = "boardColumnId",
                            in = ParameterIn.PATH,
                            description = "Board column ID",
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
    public ResponseEntity<?> delete(@PathVariable("boardColumnId") final UUID id) {
        boardColumnService.delete(id);
        return ResponseEntity.ok().build();
    }
}
