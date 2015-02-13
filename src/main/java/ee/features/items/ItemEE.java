package ee.features.items;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.google.common.collect.Multimap;

import ee.features.EELimited;
import ee.features.EEProxy;

public abstract class ItemEE extends Item {
	int toolDamage = 0;
	public ItemEE(String name)
    {
        super();
        this.setUnlocalizedName(name).setCreativeTab(EELimited.TabEE);
        GameRegistry.registerItem(this, name);
        EEProxy.mc.getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("ee:"+name,"inventory"));
    }

    public ItemEE(String name, int damage)
    {
        super();
        this.setFull3D().setUnlocalizedName(name).setMaxDamage(200).setCreativeTab(EELimited.TabEE).setMaxStackSize(1);
        GameRegistry.registerItem(this, name);
        toolDamage = damage;
        EEProxy.mc.getRenderItem().getItemModelMesher().register(this, 0, new ModelResourceLocation("ee:"+name,"inventory"));
    }

    public boolean isWood(Block b)
    {

        if (b == null)
        {
            return false;
        }

        String name = b.getUnlocalizedName();
        return name.toLowerCase().contains("wood") || name.toLowerCase().contains("log");
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        onActivated(par1ItemStack);
        return true;
    }

    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        onActivated(var1);
        return var1;
    }

    public void onActivated(ItemStack is)
    {
        if (is.getHasSubtypes() || is.getMaxDamage() > 0)
        {
            is.setItemDamage(1 - is.getItemDamage());
        }
    }
    public Multimap getItemAttributeModifiers()
    {
        if (toolDamage == 0)
        {
            return super.getItemAttributeModifiers();
        }

        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double)this.toolDamage, 0));
        return multimap;
    }
}
