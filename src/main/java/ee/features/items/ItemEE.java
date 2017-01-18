package ee.features.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ee.features.EELimited;
import ee.features.KeyRegistry;
import ee.features.items.interfaces.IChargeable;
import ee.features.items.interfaces.IExtraFunction;
import ee.features.items.interfaces.IModeChange;
import ee.features.items.interfaces.IProjectileShooter;
import net.minecraft.block.Block;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemEE extends Item
{
	int toolDamage = 0;

    public ItemEE(String name)
    {
        super();
        this.setUnlocalizedName(name).setTextureName("ee:" + name).setCreativeTab(EELimited.TabEE);
        GameRegistry.registerItem(this, name);
    }

    public ItemEE(String name, int damage)
    {
        super();
        this.setFull3D().setUnlocalizedName(name).setTextureName("ee:" + name).setMaxDamage(200).setCreativeTab(EELimited.TabEE).setMaxStackSize(1);
        GameRegistry.registerItem(this, name);
        toolDamage = damage;
        ItemSword s;
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
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        return this.getItemAttributeModifier();
    }
    private Multimap getItemAttributeModifier()
    {
        if (toolDamage == 0)
        {
            return HashMultimap.create();
        }

        Multimap multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.toolDamage, 0));
        return multimap;
    }
    private String getKeyName(KeyBinding key)
    {
    	return Keyboard.getKeyName(key.getKeyCode());
    }
    private String getSimpleName(Item item)
    {
    	if(item == null)
    	{
    		return "";
    	}
    	return item.getUnlocalizedName().replace("item.", "");
    }
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
    {
    	if(stack == null)
    	{
    		return;
    	}
    	Item item = stack.getItem();
    	if(item instanceof IChargeable || item instanceof IExtraFunction || item instanceof IProjectileShooter)
    	{
    		GameSettings settings = FMLClientHandler.instance().getClient().gameSettings;
    		if(!Keyboard.isKeyDown(settings.keyBindSneak.getKeyCode()))
    		{
    			list.add(String.format("Press <%s> to show more information.",Keyboard.getKeyName(settings.keyBindSneak.getKeyCode())));
    		}
    		else
    		{
    			String modeChange = getKeyName(KeyRegistry.array[0]);
    			String charge = getKeyName(KeyRegistry.array[1]);
    			String shooter = getKeyName(KeyRegistry.array[2]);
    			String extra = getKeyName(KeyRegistry.array[3]);

    			String simpleName = getSimpleName(item);
    			if(item instanceof IModeChange)
    			{
    				list.add(String.format("%sPress %s%s %sto %s", EnumChatFormatting.RESET,EnumChatFormatting.YELLOW,modeChange,EnumChatFormatting.RESET,StatCollector.translateToLocal("tips."+simpleName+",mode")));
    			}
    		}
    	}
    }
}
