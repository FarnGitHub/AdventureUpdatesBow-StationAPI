package farn.adventure_update_bow;

import farn.adventure_update_bow.api.ArrowRegisterEvent;
import farn.adventure_update_bow.impl.config.AUBGlassConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.registry.AfterBlockAndItemRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

@SuppressWarnings("unused")
public class AdventureUpdateBow {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Environment(EnvType.CLIENT)
    public static int[] bowPulling = new int[3];

    @Environment(EnvType.CLIENT)
    public static int[] phoenixBowPulling;

    public static boolean GCAPI = false;
    public static boolean uniTweak = false;

    public static Identifier bow_rotate;

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        boolean aetherLoad = FabricLoader.getInstance().isModLoaded("aether");
        if(aetherLoad)
            phoenixBowPulling = new int[3];
        for(int pull = 0; pull < bowPulling.length; ++pull) {
            bowPulling[pull] = Atlases.getGuiItems().addTexture(id("item/bow_pull_" + pull)).index;
            if(aetherLoad)
                phoenixBowPulling[pull] =  Atlases.getGuiItems().addTexture(id("item/PhoenixBow_pull_" + pull)).index;
        }
    }

    @EventListener
    public void init(InitEvent event) {
        bow_rotate = id("bow_rotate");
        GCAPI = FabricLoader.getInstance().isModLoaded("gcapi3");
        uniTweak = FabricLoader.getInstance().isModLoaded("unitweaks");
    }

    @EventListener
    public void registerAllArrow(AfterBlockAndItemRegisterEvent event) {
        FabricLoader.getInstance().getEntrypointContainers("adventure_update_bow", Object.class).forEach(EntrypointManager::setup);
        StationAPI.EVENT_BUS.post(new ArrowRegisterEvent());
    }

    public static boolean isCritEnabled() {
        if(GCAPI) return AUBGlassConfig.instance.crit;
        return false;
    }

    public static boolean isDurabilityEnabled() {
        if(GCAPI) return AUBGlassConfig.instance.durability;
        return true;
    }

    public static boolean isPreciseSkeleton() {
        if(GCAPI) return AUBGlassConfig.instance.preciseSkeleton;
        return true;
    }

    public static Identifier id(String id) {
        return NAMESPACE.id(id);
    }
}
