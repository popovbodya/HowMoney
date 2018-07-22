package ru.popov.bodya.core.converter;

/**
 * @author popovbodya
 */

public interface OneWayConverter<F, T> {

    T convert(F from);

}
