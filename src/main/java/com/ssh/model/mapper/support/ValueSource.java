package com.ssh.model.mapper.support;

import java.util.function.Function;

@FunctionalInterface
public interface ValueSource<S, V> extends Function<S, V> {

}
