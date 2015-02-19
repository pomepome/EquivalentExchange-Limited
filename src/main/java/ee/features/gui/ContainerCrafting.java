package ee.features.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerCrafting extends ContainerWorkbench {

	public ContainerCrafting(EntityPlayer p, World worldIn) {
		super(p.inventory, worldIn,new BlockPos((int)p.posX,(int)p.posY,(int)p.posZ));
	}

	@Override
    public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

}
