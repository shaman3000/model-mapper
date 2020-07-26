package com.ssh.model.mapper.support;

public class AttributeMapping<D, T, P> extends ChildMapping<D, T, P> {

    private final ValueDestination<D, T> valueDestination;

    protected AttributeMapping(ValueSource<P, T> valueSource, ValueDestination<D, T> valueDestination, boolean ignoreNulls) {
        super(valueSource, ignoreNulls);
        this.valueDestination = valueDestination;
    }

    @Override
    protected void doExecute(Object source, D destination) {
        Object rawValue = readRawValue(source);
        if (rawValue != null || ignoreNulls) {
            writeRawValue(rawValue, destination);
            doExecuteChildren(rawValue, destination);
        }
    }

    protected void writeRawValue(Object source, Object destination) {
        getRawDestination().accept(destination, source);
    }

    @SuppressWarnings("unchecked")
    protected ValueDestination<Object, Object> getRawDestination() {
        return (ValueDestination<Object, Object>) valueDestination;
    }

}
