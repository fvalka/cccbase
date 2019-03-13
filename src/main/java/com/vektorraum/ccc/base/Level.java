package com.vektorraum.ccc.base;

import java.util.List;
import java.util.function.Function;

/**
 * Interface for all Levels, for LevelRunner and UnitTest running
 */
public interface Level extends Function<List<String>, String> {
}
