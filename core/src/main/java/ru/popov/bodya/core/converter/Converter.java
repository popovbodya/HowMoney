package ru.popov.bodya.core.converter;

/**
 * @author popovbodya
 */

public interface Converter<F, T> extends OneWayConverter<F, T> {

    F reverse(T to);

}
