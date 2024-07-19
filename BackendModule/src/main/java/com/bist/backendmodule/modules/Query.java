package com.bist.backendmodule.modules;

import org.springframework.http.ResponseEntity;

/**
 * Query interface for executing operations.
 *
 * @param <I> The type of the input parameter
 * @param <O> The type of the output result
 */
public interface Query<I, O> {
    /**
     * Executes the query with the given input parameter.
     *
     * @param input The input parameter
     * @return ResponseEntity containing the result of the query execution
     */
    ResponseEntity<O> execute(I input);
}
