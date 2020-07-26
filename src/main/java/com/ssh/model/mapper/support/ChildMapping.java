package com.ssh.model.mapper.support;

/**
 * Mapping for nested objects. Extends base mapping with value source.
 *
 * @param <D> Destination type
 * @param <T> 'This' type
 * @param <P> Parent value type
 */
public class ChildMapping<D, T, P> extends Mapping<D, T> {

    private final ValueSource<P, T> valueSource;

    protected final boolean ignoreNulls;

    protected ChildMapping(ValueSource<P, T> valueSource, boolean ignoreNulls) {
        this.valueSource = valueSource;
        this.ignoreNulls = ignoreNulls;
    }

    protected Object readRawValue(Object source) {
        return getRawSource().apply(source);
    }

    @Override
    protected void doExecute(Object source, D destination) {
        Object rawValue = readRawValue(source);
        if (rawValue != null || ignoreNulls) {
            doExecuteChildren(rawValue, destination);
        }
    }

    @SuppressWarnings("unchecked")
    protected ValueSource<Object, Object> getRawSource() {
        return (ValueSource<Object, Object>) valueSource;
    }

}
