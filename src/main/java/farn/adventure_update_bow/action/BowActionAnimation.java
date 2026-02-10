package farn.adventure_update_bow.action;

import farn.farn_util.api.item_usage.ActionTypeAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class BowActionAnimation extends ActionTypeAnimation {

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

}
