package com.ssh.model.mapper.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Base mapping primitive.
 *
 * @param <D> Destination bean type
 * @param <T> 'This' type
 */
public class Mapping<D, T> {

    private final List<Mapping<D, ?>> children = new ArrayList<>();

    protected Mapping() {

    }

    /**
     * Children list getter.
     *
     * @return List of chilren mappings
     */
    public synchronized List<Mapping<D, ?>> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Child mapping factory.
     *
     * @param valueSource Function returning C typed value from current value T
     * @param <C> Child type transformed from T
     * @return Child mapping typed with C
     */
    public synchronized <C> Mapping<D, C> map(ValueSource<T, C> valueSource) {
        Mapping<D, C> mapping = new ChildMapping<>(valueSource, false);
        children.add(mapping);
        return mapping;
    }

    /**
     * Adapter to 'Child mapping factory'
     *
     * @param valueSource Function returning C typed value from current value T
     * @param mappingCallback Consumer of newly created 'Child mapping typed with C'
     * @param <C> Child type transformed from T
     * @return this
     */
    public <C> Mapping<D, T> map(ValueSource<T, C> valueSource,
                                 Consumer<Mapping<D, C>> mappingCallback) {
        mappingCallback.accept(map(valueSource));
        return this;
    }

    /**
     * Source to Destination mapping factory
     *
     * @param valueSource Function returning C typed value from current value T
     * @param valueDestination Consumer function for the value
     * @param <V> Value type transformed from T
     * @return this
     */
    public <V> Mapping<D, T> map(ValueSource<T, V> valueSource,
                                 ValueDestination<D, V> valueDestination) {
        children.add(new AttributeMapping<>(valueSource, valueDestination, false));
        return this;
    }

    protected void doExecuteChildren(Object source, D destination) {
        for (Mapping<D, ?> child : children) {
            child.doExecute(source, destination);
        }
    }

    protected void doExecute(Object source, D destination) {
        doExecuteChildren(source, destination);
    }

}
