package ee.features.items;

import net.minecraft.item.ItemStack;

public class ItemEEFunctional extends ItemEE {

	public ItemEEFunctional(String name) {
		super(name);
		this.setContainerItem(this).setMaxStackSize(1);
	}
	public ItemEEFunctional(String name,int damage) {
		super(name,damage);
		this.setContainerItem(this).setMaxStackSize(1);
	}

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

}
