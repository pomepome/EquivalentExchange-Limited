package ee.features;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemVolcanaite extends ItemFunction {

	public ItemVolcanaite(int id) {
		super(id,INameRegistry.Volcanite);
	}

	@Override
	public void doPassive(World world, EntityPlayer player, ItemStack is) {
	}

	public void doVaporize(ItemStack var1, World var2, EntityPlayer var3,int range)
    {
        boolean var4 = false;

        if(range %2 == 0)
        {
        	range++;
        }

        int count = (range + 1) / 2;

        int ox = (int)var3.posX - count;
        int oy = (int)var3.posY - count;
        int oz = (int)var3.posZ - count;

        for(int i = 0;i < range;i++)
        {
        	for(int j = 0;j < range;j++)
        	{
        		for(int k = 0;k < range;k++)
        		{
        			int nx = ox + i;
        			int ny = oy + j;
        			int nz = oz + k;
        			if(var2.getBlockMaterial(nx, ny, nz) == Material.water)
        			{
        				var4 = true;
        				var2.setBlockWithNotify(ox, oy, oz, 0);
        			}
        		}
        	}
        }
        if(var4)
        {
        	EEProxy.playSoundAtPlayer("random.fizz", var3, 1.0F, 1.2F / (var2.rand.nextFloat() * 0.2F + 0.9F));
        }
    }

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
		if(var3.isSneaking())
		{
			doVaporize(var1,var2,var3,21);
		}
		return var1;
    }

}
