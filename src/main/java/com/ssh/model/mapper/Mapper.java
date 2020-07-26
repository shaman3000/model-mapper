package com.ssh.model.mapper;

import com.ssh.model.mapper.support.Mapping;

/**
 * Main entry point for mapping definitions in business-related code.
 * Mapper implementation is stateless.
 * So it can be defined once and the same instance can be reused to map
 * set of objects. Mapper implementation is immutable and thread-safe.
 * Methods modifying internal unsafe objects has been marked with 'synchronized'.
 *
 * @param <S> Source bean type
 * @param <D> Destination bean type
 */
public class Mapper<S, D> extends Mapping<D, S> {

    /**
     * Main method to transform source object to destination.
     *
     * @param source
     * @param destination
     * @return The same object as in destination parameter
     */
    public D execute(S source, D destination) {
        doExecute(source, destination);
        return destination;
    }

}
