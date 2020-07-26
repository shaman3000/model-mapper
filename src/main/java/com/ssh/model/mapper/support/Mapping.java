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
     * @param valueSource Lazy (Supplier based) transformation from current value T to C
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
     * @param valueSource Lazy (Supplier based) transformation from current value T to C
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
     * @param valueSource Lazy (Supplier based) transformation from current value T to V
     * @param valueDestination Consumer function for the value
     * @param <V> Value type transformed from T
     * @return this
     */
    public <V> Mapping<D, T> map(ValueSource<T, V> valueSource,
                                 ValueDestination<D, V> valueDestination) {
        Mapping<D, V> mapping = new AttributeMapping<>(valueSource, valueDestination, false);
        children.add(mapping);
        return this;
    }

    protected void doExecuteChildren(Object source, D destination) {
        for (Mapping<D, ?> mapping : children) {
            mapping.doExecute(source, destination);
        }
    }

    protected void doExecute(Object source, D destination) {
        doExecuteChildren(source, destination);
    }

}
