package farn.adventure_update_bow.impl;

import farn.adventure_update_bow.api.ArrowRegisterEvent;
import farn.adventure_update_bow.impl.elemental_arrow.ElementalArrowProperties;
import farn.adventure_update_bow.impl.vanilla.VanillaArrowProperties;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;

public class DefaultArrowListener {

    @SuppressWarnings("unused")
    @EventListener(priority = ListenerPriority.LOWEST)
    public void registerArrow(ArrowRegisterEvent e) {
        if(FabricLoader.getInstance().isModLoaded("elementalarrows"))
            ElementalArrowProperties.register();
        e.register(new VanillaArrowProperties());
    }
}
