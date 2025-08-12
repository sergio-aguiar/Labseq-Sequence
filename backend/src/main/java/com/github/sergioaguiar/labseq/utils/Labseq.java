package com.github.sergioaguiar.labseq.utils;

import java.math.BigInteger;
import java.util.HashMap;

public class Labseq 
{
    // HashMap used to cache all previously calculated labseq sequence values.
    // Since both Integer and Long can overflow from a service like this, went with BigInteger for values (due to the arbitrary size).
    private static HashMap<Integer, BigInteger> labseqCache = new HashMap<>();

    public static String INSUFFICIENT_MEMORY_STRING = "The provided index was too large to compute a sequence value for.";

    // Initiate the cache structure with the four initial values required to calculate the remaining indexes.
    static 
    {
        labseqCache.put(0, BigInteger.ZERO);
        labseqCache.put(1, BigInteger.ONE);
        labseqCache.put(2, BigInteger.ZERO);
        labseqCache.put(3, BigInteger.ONE);
    }

    public static BigInteger calculateAt(int index)
    {
        try
        {
            // If the value is already cached, return it.
            if (labseqCache.containsKey(index)) return labseqCache.get(index);

            // If the value isn't cached, get the highest known value (to start calculating from).
            int maxCached = labseqCache.keySet().stream().max(Integer::compareTo).orElse(3);

            // Start from the index after the highest known value, and calculate up to desired index (passed as a method argument).
            for (int i = maxCached + 1; i <= index; i++)
            {
                // Calculate l(n) = l(n - 4) + l(n - 3) and cache the value.
                labseqCache.put(i, labseqCache.get(i - 4).add(labseqCache.get(i - 3)));
            }

            // Return the last value calculated (cached at the final for-loop cycle).
            return labseqCache.get(index);
        }
        catch (OutOfMemoryError e)
        {
            // Return null to signal the exception happening.
            // This solution seems to not be working... Likely from the try-catch never even resolving due to the lack of memory itself.
            // Keeping it here, just in case. Will instead limit the program to indexes n <= 100000.
            return null;
        }
    }
}