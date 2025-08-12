package com.github.sergioaguiar.labseq.realServiceTests;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class LabseqSequenceResourceTest 
{
    @Test    
    public void testGetLabseqAtEndpoint_WithNonNegativeValue() 
    {
		/*
			Test query to "/labseq/20", should return "21".

			Calculations:
			l(0)  = 0
			l(1)  = 1
			l(2)  = 0
			l(3)  = 1
			l(4)  = l(0)  +  l(1) = 0 +  1 =  1
			l(5)  = l(1)  +  l(2) = 1 +  0 =  1
			l(6)  = l(2)  +  l(3) = 0 +  1 =  1
			l(7)  = l(3)  +  l(4) = 1 +  1 =  2
			l(8)  = l(4)  +  l(5) = 1 +  1 =  2
			l(9)  = l(5)  +  l(6) = 1 +  1 =  2
			l(10) = l(6)  +  l(7) = 1 +  2 =  3
			l(11) = l(7)  +  l(8) = 2 +  2 =  4
			l(12) = l(8)  +  l(9) = 2 +  2 =  4
			l(13) = l(9)  + l(10) = 2 +  3 =  5
			l(14) = l(10) + l(11) = 3 +  4 =  7
			l(15) = l(11) + l(12) = 4 +  4 =  8
			l(16) = l(12) + l(13) = 4 +  5 =  9
			l(17) = l(13) + l(14) = 5 +  7 = 12
			l(18) = l(14) + l(15) = 7 +  8 = 15
			l(19) = l(15) + l(16) = 8 +  9 = 17
			l(20) = l(16) + l(17) = 9 + 12 = 21
		 */
        given()
        	.pathParam("n", 20)
        .when()
        	.get("/labseq/{n}")
        .then()
        	.statusCode(200)    
        	.body(is("21"));
    }

	@Test    
    public void testGetLabseqAtEndpoint_WithNegativeValue() 
    {
		// Test query to "/labseq/-1", should return "Values submitted for 'n' must be non-negative integers. Given value: -1".
        given()
        	.pathParam("n", -1)
        .when()
        	.get("/labseq/{n}")
        .then()
        	.statusCode(400)
        	.body(is("Values submitted for 'n' must be non-negative integers. Given value: -1"));
    }

	@Test    
    public void testGetLabseqAtEndpoint_WithLargeIndexValue()
	{
		// Test query to "/labseq/1000000", should return "Values submitted for 'n' must not exceed 100000. Given value: 1000000".
        given()
        	.pathParam("n", 1000000)
        .when()
        	.get("/labseq/{n}")
        .then()
        	.statusCode(400)
        	.body(is("Values submitted for 'n' must not exceed 100000. Given value: 1000000"));
    }
}