package farn.adventure_update_bow.action;

import farn.farn_util.api.item_usage.ActionType;
import farn.farn_util.api.item_usage.ActionTypeAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.modificationstation.stationapi.api.util.Identifier;

public class CompositeBowAction extends ActionType {
    public CompositeBowAction() {
        super(Identifier.of("composite_bow_action"));
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            createAnimation();
    }
    @Environment(EnvType.CLIENT)
    public void createAnimation() {
        animation = new ActionTypeAnimation();
    }

}
