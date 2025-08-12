package com.github.sergioaguiar.labseq.mockServicetests;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import com.github.sergioaguiar.labseq.LabseqSequenceService;
import com.github.sergioaguiar.labseq.utils.Labseq;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
public class LabseqSequenceResourceTest 
{
	@InjectMock
	LabseqSequenceService mockService;

	@Test    
    public void testGetLabseqAtEndpoint_OutOfMemoryException()
	{
		// Simulating a case where a system would get an OutOfMemoryError exception thrown with an index such as n=1000.
		// Though, pretty sure in actual practical cases, this will not work due to not being able to resolve the lowest-level try-catch.
		when(mockService.getLabseqAt(1000))
			.thenReturn(Labseq.INSUFFICIENT_MEMORY_STRING);

		// Test query to "/labseq/1000", should return "The provided index was too large to compute a sequence value for.".
        given()
        	.pathParam("n", 1000)
        .when()
        	.get("/labseq/{n}")
        .then()
        	.statusCode(413)
        	.body(is(Labseq.INSUFFICIENT_MEMORY_STRING));
    }
}