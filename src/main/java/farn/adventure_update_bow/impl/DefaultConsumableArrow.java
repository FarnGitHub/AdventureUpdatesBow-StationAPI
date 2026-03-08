package farn.adventure_update_bow.impl;

import farn.adventure_update_bow.api.ConsumableArrowEvent;
import farn.adventure_update_bow.impl.elementalArrow.ElementalArrowConsumable;
import farn.adventure_update_bow.impl.vanilla.GenericConsumableArrow;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;

public class DefaultConsumableArrow {

    @SuppressWarnings("unused")
    @EventListener(priority = ListenerPriority.LOWEST)
    public void registerArrow(ConsumableArrowEvent e) {
        if(FabricLoader.getInstance().isModLoaded("elementalarrows"))
            ElementalArrowConsumable.register();
        e.register(new GenericConsumableArrow());
    }
}
