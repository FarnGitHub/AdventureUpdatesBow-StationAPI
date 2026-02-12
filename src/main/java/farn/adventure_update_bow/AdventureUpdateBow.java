package farn.adventure_update_bow;

import farn.adventure_update_bow.action.BowAction;
import farn.adventure_update_bow.mod.AUBGlassConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

public class AdventureUpdateBow {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Environment(EnvType.CLIENT)
    public static int[] bowPulling = new int[3];

    @Environment(EnvType.CLIENT)
    public static int[] bowCompositePulling = new int[3];

    public static boolean GCAPI = false;

    public static Identifier bow_rotate;
    public static Identifier bow_action;
    public static BowAction bowAction;

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        for(int pull = 0; pull < bowPulling.length; ++pull) {
            bowPulling[pull] = Atlases.getGuiItems().addTexture(NAMESPACE.id("item/bow_pull_" + pull)).index;
            bowCompositePulling[pull] = Atlases.getGuiItems().addTexture(NAMESPACE.id("item/btw/bow_composite_pull_" + pull)).index;
        }
    }

    @EventListener
    public void init(InitEvent event) {
        bow_rotate = NAMESPACE.id("bow_rotate");
        bow_action = NAMESPACE.id("bow_action");
        bowAction = new BowAction();
        GCAPI = FabricLoader.getInstance().isModLoaded("gcapi3");
    }

    public static boolean isCritEnabled() {
        if(GCAPI) return AUBGlassConfig.instance.crit;
        return false;
    }

    public static boolean isEnabled() {
        if(GCAPI) return AUBGlassConfig.instance.enabled;
        return true;
    }
}
