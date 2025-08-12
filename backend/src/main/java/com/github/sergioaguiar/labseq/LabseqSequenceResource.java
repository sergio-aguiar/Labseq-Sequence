package com.github.sergioaguiar.labseq;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.github.sergioaguiar.labseq.utils.Labseq;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/labseq")
@OpenAPIDefinition
(
    info = @Info
    (
        title = "Labseq API",
        version = "1.0.0",
        description = "REST API for calculating Labseq sequence values."
    ),
    tags = 
    {
        @Tag
        (
            name = "Labseq Sequence",
            description = "Operations related to the Labseq Sequence."
        )
    }
)
public class LabseqSequenceResource 
{
    @Inject
    LabseqSequenceService labseqService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{n}")
    @Tag(name = "Labseq Sequence")
    @Operation
    (
        summary = "Get a value from the Labseq sequence.",
        description = "Calculates and returns the Labseq sequence value at index 'n'."
    )
    @APIResponses(value = 
        {
            @APIResponse
            (
                responseCode = "200",
                description = "Successfully calculated the Labseq sequence value for the given index.",
                content = @Content
                (
                    mediaType = MediaType.TEXT_PLAIN,
                    examples =
                    {
                        @ExampleObject
                        (
                            name = "nonNegativeExample", value = "21"
                        )
                    } 
                )
            ),
            @APIResponse
            (
                responseCode = "400",
                description = "Invalid index value given (either a negative value or a value above 100000).",
                content = @Content
                (
                    mediaType = MediaType.TEXT_PLAIN,
                    examples =
                    {
                        @ExampleObject
                        (
                            name = "negativeExample", value = "Values submitted for 'n' must be non-negative integers. Given value: -1"
                        ),
                        @ExampleObject
                        (
                            name = "largeIndexExample", value = "Values submitted for 'n' must not exceed 100000. Given value: 1000000"
                        )
                    } 
                )
            ),
            @APIResponse
            (
                responseCode = "413",
                description = "Could not calculate a Labseq sequence value for the given index (out of memory).",
                content = @Content
                (
                    mediaType = MediaType.TEXT_PLAIN,
                    examples =
                    {
                        @ExampleObject
                        (
                            name = "outOfMemoryExample", value = "The provided index was too large to compute a sequence value for."
                        )
                    } 
                )
            )
        }
    )
    public Response getLabseqAt
    (
        @Parameter
        (
            description = "The sequence index to use to return a value.",
            example = "20",
            required = true,
            in = ParameterIn.PATH   
        ) 
        @PathParam("n") int n
    )
    {
        // If the given value for the index (n) is negative, return a message explaining the constraint.
        if (n < 0) 
        {
            return Response
                .status(Status.BAD_REQUEST)
                .entity("Values submitted for 'n' must be non-negative integers. Given value: " + n)
                .build();
        }

        if (n > 100000)
        {
            return Response
                .status(Status.BAD_REQUEST)
                .entity("Values submitted for 'n' must not exceed 100000. Given value: " + n)
                .build();
        }

        // Obtain the sequence calculation result.
        String calcResult = labseqService.getLabseqAt(n);

        // If the given value for the index (n) was too large to compute a sequence value, return a message explaining the constraint.
        if (calcResult.equals(Labseq.INSUFFICIENT_MEMORY_STRING))
        {
            return Response
                .status(Status.REQUEST_ENTITY_TOO_LARGE)
                .entity(calcResult)
                .build();
        }

        // Return the labseq sequence value obtained from the relevant service call.
        return Response
            .ok(calcResult)
            .build();
    }
}
