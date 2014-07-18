package ee.features;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class FixRecipe implements IRecipe {

	/** Is the ItemStack that you get when craft the recipe. */
    private ItemStack recipeOutput;

    /** Is a List of ItemStack that composes the recipe. */
    public List recipeItems;
    public Map<Integer,Integer> enchantment;

    public FixRecipe(ItemStack par1ItemStack, List par2List)
    {
        this.recipeOutput = par1ItemStack;
        this.recipeItems = par2List;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        ArrayList var3 = new ArrayList(this.recipeItems);

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

                        if(var9==null)
                        {
                        	return false;
                        }

                        if (var6.itemID == var9.itemID && ((var9.getItemDamage() == -1 && var6.getItemDamage() != 0)|| var6.getItemDamage() == var9.getItemDamage()))
                        {
                        	if(var9.getItemDamage() == -1&&var6.isItemEnchanted())
                        	{
                        		enchantment = EnchantmentHelper.getEnchantments(var6);
                        	}
                        	else if(var9.getItemDamage() == -1)
                        	{
                        		enchantment = null;
                        	}
                            var7 = true;
                            var3.remove(var9);
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
    public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
    {
    	ItemStack is = recipeOutput.copy();
    	if(enchantment != null)
    	{
    		Set<Integer> keyset = enchantment.keySet();
    		Iterator it = keyset.iterator();
    		while(it.hasNext())
    		{
    			int id = (Integer)it.next();
    			int level = (Integer)enchantment.get(id);
    			is.addEnchantment(Enchantment.enchantmentsList[id],level);
    		}
    	}
    	if(!is.getHasSubtypes())
    	{
    		is.setItemDamage(0);
    	}
        return is;
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return this.recipeItems.size();
    }

	@Override
	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

}
