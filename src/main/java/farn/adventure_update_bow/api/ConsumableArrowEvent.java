package farn.adventure_update_bow.api;

import net.mine_diver.unsafeevents.Event;

public class ConsumableArrowEvent extends Event {

    public void register(ConsumableArrow consumableArrow) {
        ConsumableArrows.consumables.add(consumableArrow);
    }
}
