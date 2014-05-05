package ee.features;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeFix implements IRecipe {

	ItemStack recipeOutput;
	List recipeItems = new ArrayList();

	public RecipeFix(ItemStack Output, ItemStack... itemstacks) {
		super();
		recipeOutput = Output.copy();
		for (int i = 0; i < itemstacks.length; i++) {
			recipeItems.add(itemstacks[i]);
		}
	}

	@Override
	public boolean matches(InventoryCrafting par1InventoryCrafting,
			World par2World) {
		ArrayList var3 = new ArrayList(this.recipeItems);

		for (int var4 = 0; var4 < 9; ++var4) {
			ItemStack var6 = par1InventoryCrafting.getStackInSlot(var4);

			if (var6 != null) {
				boolean var7 = false;
				Iterator var8 = var3.iterator();

				while (var8.hasNext()) {
					ItemStack var9 = (ItemStack) var8.next();

					if (var9 == null|| var6.getItem().getContainerItem() == null) {
						break;
					}

					if (var6.itemID == var9.itemID&& var6.getMaxDamage() != 0&& var6.getItem().getContainerItem().itemID == var6.getItem().itemID) {
						try {
							par1InventoryCrafting.setInventorySlotContents(var4, null);
							var7 = true;
							var3.remove(var9);
							break;
						} catch (Throwable t) {

						}
					}

					if (var6.itemID == var9.itemID&& (var9.getItemDamage() == -1 || var6.getItemDamage() == var9.getItemDamage())) {
						var7 = true;
						var3.remove(var9);
						break;
					}
				}

				if (!var7) {
					return false;
				}
			}
		}

		return var3.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		return recipeOutput.copy();
	}

	@Override
	public int getRecipeSize() {
		return recipeItems.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return recipeOutput;
	}

}
