package farn.adventure_update_bow.impl;

import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.impl.item.ItemBowImpl;
import farn.farn_util.api.animation_hook.bipedmodel.BipedModelEvent;
import farn.farn_util.api.animation_hook.player_render.FirstPersonItemRotationEvent;
import farn.farn_util.api.animation_hook.player_render.PlayerRenderEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class BowAnimation {

    @EventListener
    public void applyFirstPersonItemRotation(FirstPersonItemRotationEvent event) {
        if(!event.plr.farnutil_isUsingItem() || !event.plr.farnutil_hasActionId(ItemBowImpl.getActionId())) return;
        GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
        float f10 = (float)event.heldStack.getItem().farnutil_getMaxDuration(event.heldStack) - ((float)event.plr.farnutil_getUsingDuration() - event.tick + 1.0F);
        float f18 = f10 / 20.0F;
        f18 = (f18 * f18 + f18 * 2.0F) / 3.0F;
        if(f18 > 1.0F) {
            f18 = 1.0F;
        }

        if(f18 > 0.1F) {
            GL11.glTranslatef(0.0F, MathHelper.sin((f10 - 0.1F) * 1.3F) * 0.01F * (f18 - 0.1F), 0.0F);
        }

        GL11.glTranslatef(0.0F, 0.0F, f18 * 0.1F);
        GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F + f18 * 0.2F);
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
    }

    @EventListener
    public void beforePlayerRender(PlayerRenderEvent.Before event) {
        boolean bool = event.player.farnutil_isUsingItem() &&
                event.player.farnutil_hasActionId(ItemBowImpl.getActionId());
        event.renderer.armor1.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
        event.renderer.armor2.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
        event.renderer.bipedModel.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
    }

    @EventListener
    public void afterPlayerRender(PlayerRenderEvent.After event) {
        event.renderer.armor1.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, false);
        event.renderer.armor2.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, false);
        event.renderer.bipedModel.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, false);
    }

    @EventListener
    public void playerAnimation(BipedModelEvent.SetAngle event) {
        if(event.model.modelflag_getModelFlag(AdventureUpdateBow.bow_rotate, false)) {
            event.model.rightArm.roll = 0.0F;
            event.model.leftArm.roll = 0.0F;
            event.model.rightArm.yaw = -0.06F + event.model.head.yaw;
            event.model.leftArm.yaw = 0.06F + event.model.head.yaw + 0.4F;
            event.model.rightArm.pitch = -1.5707964F + event.model.head.pitch;
            event.model.leftArm.pitch = -1.5707964F + event.model.head.pitch;
            event.model.rightArm.roll += MathHelper.cos(event.animationProgress * 0.09F) * 0.05F + 0.05F;
            event.model.leftArm.roll -= MathHelper.cos(event.animationProgress * 0.09F) * 0.05F + 0.05F;
            event.model.rightArm.pitch += MathHelper.sin(event.animationProgress * 0.067F) * 0.05F;
            event.model.leftArm.pitch -= MathHelper.sin(event.animationProgress * 0.067F) * 0.05F;
        }
    }
}
