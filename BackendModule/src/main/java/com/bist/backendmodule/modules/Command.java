package com.bist.backendmodule.modules;

import org.springframework.http.ResponseEntity;

/**
 * Command interface for executing operations.
 *
 * @param <E> The type of the input entity
 * @param <B> The type of the binding result
 * @param <T> The type of the response entity
 */
public interface Command<E, B, T> {
    /**
     * Executes the command with the given input entity and binding result.
     *
     * @param entity The input entity
     * @param bindingResult The binding result
     * @return ResponseEntity containing the result of the command execution
     */
    ResponseEntity<T> execute(E entity, B bindingResult);
}
