package farn.adventure_update_bow.action;

import farn.adventure_update_bow.AdventureUpdateBow;
import farn.farn_util.api.item_usage.ActionAnimator;
import farn.farn_util.api.item_usage.ActionHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class BowAction extends ActionHandler {
    public BowAction() {
        super(AdventureUpdateBow.bow_action);
    }

    @Environment(EnvType.CLIENT)
    public ActionAnimator createAnimation() {
        return new BowActionAnimation();
    }

}
