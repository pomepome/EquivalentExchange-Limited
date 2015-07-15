package ee.features.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ee.features.items.ItemKleinStar;

public class KleinUpgradeRecipe extends ShapelessRecipes {

	private ItemStack recipeOutput;
	private int EMC;
	private int[] maxEMCs = new int[]{50000,200000,800000,3200000,12800000,51200000};

	public KleinUpgradeRecipe(ItemStack p_i1918_1_, List p_i1918_2_) {
		super(p_i1918_1_, p_i1918_2_);
		this.recipeOutput = p_i1918_1_;
	}
	/**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        ArrayList var3 = new ArrayList(this.recipeItems);
        EMC = 0;
        for (int var4 = 0; var4 < 3; ++var4)
        {
            for (int var5 = 0; var5 < 3; ++var5)
            {
                ItemStack var6 = par1InventoryCrafting.getStackInRowAndColumn(var5, var4);

                if (var6 != null)
                {
                    boolean var7 = false;
                    Iterator var8 = var3.iterator();

                    while (var8.hasNext())
                    {
                        ItemStack var9 = (ItemStack)var8.next();

                        if (var9 == null)
                        {
                            return false;
                        }

                        if (var6.getItem() == var9.getItem() && ((var9.getItemDamage() == 32767 && (var6.getItemDamage() != 0 )) || var6.getItemDamage() == var9.getItemDamage()))
                        {
                        	boolean flag = false;
                        	if(var6.getItem() instanceof ItemKleinStar)
                        	{
                        		EMC += var6.getTagCompound().getInteger("EMC");
                        	}
                            var7 = true;
                            var3.remove(var9);
                            if(flag)
                            {
                            	var9 = null;
                            }
                            break;
                        }
                    }

                    if (!var7)
                    {
                        return false;
                    }
                }
            }
        }

        return var3.isEmpty();
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inventory)
    {
        ItemStack is = recipeOutput.copy();
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("MaxEMC",maxEMCs[is.getItemDamage()]);
        nbt.setInteger("EMC",EMC);
        is.setTagCompound(nbt);
        return is;
    }

}
