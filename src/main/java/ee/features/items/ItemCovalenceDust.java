package ee.features.items;

import java.util.List;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ee.features.NameRegistry;
import ee.features.proxy.EEProxy;

public class ItemCovalenceDust extends ItemEE {

	public ItemCovalenceDust() {
		super(NameRegistry.Cov);
		String name = NameRegistry.Cov;
		this.setHasSubtypes(true).setMaxDamage(0);
		ModelBakery.addVariantName(this, "ee:"+name+"low","ee:"+name+"mid","ee:"+name+"high");
		EEProxy.getMC().getRenderItem().getItemModelMesher().register(this,0,new ModelResourceLocation("ee:"+name+"low","inventory"));
		EEProxy.getMC().getRenderItem().getItemModelMesher().register(this,1,new ModelResourceLocation("ee:"+name+"mid","inventory"));
		EEProxy.getMC().getRenderItem().getItemModelMesher().register(this,2,new ModelResourceLocation("ee:"+name+"high","inventory"));
	}
	 @SideOnly(Side.CLIENT)
	 @Override
	 /**
	  * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	  */
	 public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	 {
		 par3List.add(new ItemStack(par1, 1, 0));
		 par3List.add(new ItemStack(par1, 1, 1));
		 par3List.add(new ItemStack(par1, 1, 2));
	 }
	 @Override
	 public String getUnlocalizedName(ItemStack stack)
	 {
	 	return stack.getItemDamage() == 2 ? "item.Cov_high" : stack.getItemDamage() == 1 ? "item.Cov_mid" : "item.Cov_low";
	 }
}
