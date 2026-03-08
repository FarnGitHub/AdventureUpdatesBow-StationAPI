package farn.adventure_update_bow.impl.item;

import farn.adventure_update_bow.api.ArrowRegisterEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mine_diver.unsafeevents.listener.ListenerPriority;

public class DefaultArrowListener {

    @SuppressWarnings("unused")
    @EventListener(priority = ListenerPriority.LOWEST)
    public void registerArrow(ArrowRegisterEvent e) {
        e.register(new VanillaArrowProperties());
    }
}
