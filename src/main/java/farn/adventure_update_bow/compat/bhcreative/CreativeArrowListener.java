package farn.adventure_update_bow.compat.bhcreative;

import farn.adventure_update_bow.api.ArrowRegisterEvent;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;

public class CreativeArrowListener {

    @SuppressWarnings("unused")
    @EventListener(priority = ListenerPriority.HIGHEST)
    public void registerCreativeArrow(ArrowRegisterEvent e) {
        if(FabricLoader.getInstance().isModLoaded("bhcreative"))
            e.register(new CreativeArrowProperties());
    }
}
