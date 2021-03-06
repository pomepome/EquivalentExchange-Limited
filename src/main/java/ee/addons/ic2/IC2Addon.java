package ee.addons.ic2;

import static ee.features.Level.*;

import cpw.mods.fml.common.registry.GameRegistry;
import ee.features.EEItems;
import ee.features.EELimited;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class IC2Addon
{
	public static void load(EELimited mod)
	{
		mod.addFixRecipe(LOW, IC2Items.getItem("treetap").getItem(), 5);
		mod.addFixRecipe(LOW, IC2Items.getItem("hazmatHelmet").getItem(), 4);
		mod.addFixRecipe(LOW, IC2Items.getItem("hazmatChestplate").getItem(), 6);
		mod.addFixRecipe(LOW, IC2Items.getItem("hazmatLeggings").getItem(), 6);
		mod.addFixRecipe(LOW, IC2Items.getItem("hazmatBoots").getItem(), 6);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeHelmet").getItem(), 5);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeChestplate").getItem(), 8);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeLeggings").getItem(), 7);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeBoots").getItem(), 4);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("wrench").getItem(), 6);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzePickaxe"), 3);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeAxe"), 3);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeSword"), 2);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeShovel"), 1);
		mod.addFixRecipe(MIDDLE, IC2Items.getItem("bronzeHoe"), 2);
		mod.addRecipe(new ShapelessOreRecipe(mod.getIC2("platecopper"),mod.gs(EEItems.Phil),"ingotCopper"));
		mod.addRecipe(new ShapelessOreRecipe(mod.getIC2("platetin"),mod.gs(EEItems.Phil),"ingotTin"));
		mod.addRecipe(new ShapelessOreRecipe(mod.getIC2("plategold"),mod.gs(EEItems.Phil),mod.gs(Items.gold_ingot)));
	    mod.addRecipe(new ShapelessOreRecipe(mod.getIC2("plateiron"),mod.gs(EEItems.Phil),mod.gs(Items.iron_ingot)));
	    mod.addRecipe(new ShapelessOreRecipe(mod.getIC2("platebronze"),mod.gs(EEItems.Phil),"ingotBronze"));
	    mod.addRecipe(new ShapelessOreRecipe(mod.getIC2("platelead"),mod.gs(EEItems.Phil),"ingotLead"));
	    mod.addSRecipe(mod.changeAmount(mod.getIC2("ironCableItem"),4),mod.gs(EEItems.Phil),mod.getIC2("plateiron"));
	    mod.addSRecipe(mod.changeAmount(mod.getIC2("copperCableItem"),4),mod.gs(EEItems.Phil),mod.getIC2("platecopper"));
	    mod.addSRecipe(mod.changeAmount(mod.getIC2("goldCableItem"),4),mod.gs(EEItems.Phil),mod.getIC2("plategold"));
	    mod.addSRecipe(mod.changeAmount(mod.getIC2("tinCableItem"),4),mod.gs(EEItems.Phil),mod.getIC2("platetin"));
	    ItemStack dp = IC2Items.getItem("diamondDust");
	    Recipes.macerator.addRecipe(new RecipeInputItemStack(mod.gs(Blocks.diamond_ore)), null, mod.changeAmount(dp, 2));
	    GameRegistry.addSmelting(dp, mod.gs(Items.diamond), 10);
	}
}
