package farn.adventure_update_bow.action;

import farn.adventure_update_bow.ModernBow;
import farn.farn_util.api.item_usage.ActionType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

public class BowAction extends ActionType {
    public BowAction() {
        super(ModernBow.bow_action);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            createAnimation();
    }
    @Environment(EnvType.CLIENT)
    public void createAnimation() {
        animation = new BowActionAnimation();
    }

}
