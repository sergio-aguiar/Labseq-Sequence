package com.github.sergioaguiar.labseq;

import java.math.BigInteger;

import com.github.sergioaguiar.labseq.utils.Labseq;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LabseqSequenceService 
{
    public String getLabseqAt(int n)
    {
        // Attempt to calculate the sequence value for index 'n'.
        BigInteger result = Labseq.calculateAt(n);

        // If the obtained result was null, then an OutOfMemoryError exception was handled.
        if (result == null)
        {
            // For larger index values, an exception can be thrown for lacking the memory for the calculation.
            return Labseq.INSUFFICIENT_MEMORY_STRING;
        }

        // If the given value for the index (n) is non-negative (and there was eough memory to calculate the sequence value), return the calculated labseq sequence value.
        return result.toString();
    }
}
