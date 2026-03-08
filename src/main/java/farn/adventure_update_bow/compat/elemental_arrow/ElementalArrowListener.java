package farn.adventure_update_bow.compat.elemental_arrow;

import farn.adventure_update_bow.api.ArrowRegisterEvent;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;

public class ElementalArrowListener {

    @EventListener(priority = ListenerPriority.HIGH)
    public void registerElementalArrow(ArrowRegisterEvent e) {
        if(FabricLoader.getInstance().isModLoaded("elementalarrows"))
            e.register(new ElementalArrowProperties());
    }
}
