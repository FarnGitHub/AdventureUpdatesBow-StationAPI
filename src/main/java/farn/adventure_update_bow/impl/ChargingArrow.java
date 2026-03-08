package farn.adventure_update_bow.impl;


public interface ChargingArrow {

    default void aub_setCrit(boolean value) {
        throw new AssertionError("this method should have been override by mixin");
    }

    default boolean aub_isCrit() {
        throw new AssertionError("this method should have been override by mixin");
    }
}
