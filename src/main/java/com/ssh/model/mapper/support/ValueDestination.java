package com.ssh.model.mapper.support;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ValueDestination<D, V> extends BiConsumer<D, V> {

}
