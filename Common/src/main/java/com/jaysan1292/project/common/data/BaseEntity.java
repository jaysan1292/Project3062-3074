package com.jaysan1292.project.common.data;

/** @author Jason Recillo */
public abstract class BaseEntity<T> extends JSONSerializable<T> {
    public abstract long getId();

    public abstract void setId(long id);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}
