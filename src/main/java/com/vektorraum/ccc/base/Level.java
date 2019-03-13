package com.vektorraum.ccc.base;

import java.util.List;
import java.util.function.Function;

/**
 * Interface for all Levels, for LevelRunner and UnitTest running
 *
 * Function from input lines as List&lt;String&gt; to output String
 */
public interface Level extends Function<List<String>, String> {
}
