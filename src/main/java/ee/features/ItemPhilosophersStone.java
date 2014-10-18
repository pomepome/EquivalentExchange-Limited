package ee.features;

import net.minecraft.item.ItemStack;

public class ItemPhilosophersStone extends ItemEE {
	public ItemPhilosophersStone()
    {
        super(NameRegistry.Philo);
        this.setMaxStackSize(1).setContainerItem(this);
    }
    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }
}
