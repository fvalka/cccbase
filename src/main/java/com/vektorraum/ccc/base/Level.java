package com.vektorraum.ccc.base;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Interface for all Levels, for LevelRunner and UnitTest running
 *
 * Function from input lines as List&lt;String&gt; to output String
 */
public abstract class Level implements Function<List<String>, String> {
    private String resultDelimeter;

    public String resultsToString(Collection<Result> results) {
        resultDelimeter = ",";
        return results
                .stream()
                .sorted(Comparator.comparingInt(Result::getId))
                .map(Result::getSingleResult)
                .collect(Collectors.joining(resultDelimeter));
    }
}
