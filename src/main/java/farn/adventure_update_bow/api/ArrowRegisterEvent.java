package farn.adventure_update_bow.api;

import net.mine_diver.unsafeevents.Event;

public class ArrowRegisterEvent extends Event {

    public void register(ArrowProperties consumableArrow) {
        ArrowRegistry.register(consumableArrow);
    }
}
