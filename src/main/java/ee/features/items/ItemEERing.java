package ee.features.items;

import java.util.List;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ee.features.EEProxy;

public abstract class ItemEERing extends ItemEEFunctional {

	public ItemEERing(String name) {
		super(name);
		this.setHasSubtypes(true).setMaxDamage(0);
		ModelBakery.addVariantName(this, "ee:"+name+"On","ee:"+name+"Off");
		EEProxy.getMC().getRenderItem().getItemModelMesher().register(this,0,new ModelResourceLocation("ee:"+name+"On","inventory"));
		EEProxy.getMC().getRenderItem().getItemModelMesher().register(this,1,new ModelResourceLocation("ee:"+name+"Off","inventory"));
	}
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
	@Override
    public final void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        doPassive(par2World, (EntityPlayer)par2World.playerEntities.get(0), par1ItemStack);

        if (par1ItemStack.getItemDamage() == 0)
        {
            doPassive(par2World, (EntityPlayer)par2World.playerEntities.get(0), par1ItemStack, 0);
        }
    }
	public abstract void doPassive(World world, EntityPlayer player, ItemStack is, int d);
    public abstract void doPassive(World world, EntityPlayer player, ItemStack is);
}
