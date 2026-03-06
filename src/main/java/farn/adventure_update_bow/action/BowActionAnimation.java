package farn.adventure_update_bow.action;

import farn.adventure_update_bow.AdventureUpdateBow;
import farn.farn_util.api.biped_model_extended.BipedModelHandler;
import farn.farn_util.api.item_usage.ActionAnimator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class BowActionAnimation extends ActionAnimator {

    @Environment(EnvType.CLIENT)
    public void applyFirstPersonItemRotation(float tick, float avgHeight, ClientPlayerEntity plr, ItemStack heldStack) {
        if(!plr.farnutil_isUsingItem()) return;
        GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
        float f10 = (float)heldStack.getItem().farnutil_getMaxDuration(heldStack) - ((float)plr.farnutil_getUsingDuration() - tick + 1.0F);
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

    @Environment(EnvType.CLIENT)
    public void beforePlayerRender(PlayerEntityRenderer renderer, PlayerEntity player, ItemStack heldStack, double x, double y, double z, float yaw, float pitch) {
        boolean bool = player.farnutil_isUsingItem() &&
                        player.farnutil_getActionType(player.farnutil_getUsingItem())
                                instanceof BowAction;
        renderer.armor1.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
        renderer.armor2.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
        renderer.bipedModel.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
    }

    @Environment(EnvType.CLIENT)
    public void afterPlayerRender(PlayerEntityRenderer renderer, PlayerEntity player, ItemStack heldStack, double x, double y, double z, float yaw, float pitch) {
        renderer.armor1.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, false);
        renderer.armor2.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, false);
        renderer.bipedModel.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, false);
    }

    static {
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            BipedModelHandler.setAngles.register(
            (mo, l, ld,pg, hy, hp, s) -> {
            if(mo.modelflag_getModelFlag(AdventureUpdateBow.bow_rotate, false)) {
                 mo.rightArm.roll = 0.0F;
                 mo.leftArm.roll = 0.0F;
                 mo.rightArm.yaw = -0.06F + mo.head.yaw;
                 mo.leftArm.yaw = 0.06F + mo.head.yaw + 0.4F;
                 mo.rightArm.pitch = -1.5707964F + mo.head.pitch;
                 mo.leftArm.pitch = -1.5707964F + mo.head.pitch;
                 mo.rightArm.roll += MathHelper.cos(pg * 0.09F) * 0.05F + 0.05F;
                 mo.leftArm.roll -= MathHelper.cos(pg * 0.09F) * 0.05F + 0.05F;
                 mo.rightArm.pitch += MathHelper.sin(pg * 0.067F) * 0.05F;
                 mo.leftArm.pitch -= MathHelper.sin(pg * 0.067F) * 0.05F;
            }
            });
        }
    }

}
