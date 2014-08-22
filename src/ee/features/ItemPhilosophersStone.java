package ee.features;

import net.minecraft.item.ItemStack;

public class ItemPhilosophersStone extends ItemEE
{
    public ItemPhilosophersStone(int par1)
    {
        super(par1, NameRegistry.Philo);
        this.setMaxStackSize(1).setContainerItem(this);
    }
    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }
}
