package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingRound implements IOreRegistrationHandler {
    public ProcessingRound() {
        OrePrefix.round.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
            RecipeMap.LATHE_RECIPES.recipeBuilder()
                    .inputs(OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material, 1))
                    .outputs(GT_Utility.copyAmount(1, stack))
                    .duration((int) Math.max(uEntry.material.getMass() / 4L, 1L))
                    .EUt(8)
                    .buildAndRegister();

            if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material)) {
                ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.round, uEntry.material, 1), GT_Proxy.tBits, "fX", "X ", Character.valueOf('X'), OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material));
            }
        }
//        TODO
//        if (GT_Mod.gregtechproxy.mAE2Integration) {
//            Api.INSTANCE.registries().matterCannon().registerAmmo(OreDictionaryUnifier.get(OrePrefix.round, uEntry.material, 1L), uEntry.material.getMass());
//        }
    }
}
