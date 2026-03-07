package farn.adventure_update_bow.mixin.bow;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    static List<String> unitweakList = new ArrayList<>();
    static List<String> aetherList = new ArrayList<>();

    static {
        unitweakList.add("farn.adventure_update_bow.mixin.bow.client.vanilla.bow_renderer_fix.ItemBowHandHeldMixin");
        unitweakList.add("farn.adventure_update_bow.mixin.bow.client.vanilla.bow_renderer_fix.PlayerRendererBowMixin");
        aetherList.add("farn.adventure_update_bow.mixin.bow.common.aether.MixinPhoenixBow");
        aetherList.add("farn.adventure_update_bow.mixin.bow.common.aether.EntityFlamingArrowMixin");
        aetherList.add("farn.adventure_update_bow.mixin.bow.client.aether.PhoenixBowIconMixin");
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if(unitweakList.contains(mixinClassName)) {
            return !FabricLoader.getInstance().isModLoaded("unitweaks");
        }
        if(aetherList.contains(mixinClassName)) {
            return FabricLoader.getInstance().isModLoaded("aether");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
