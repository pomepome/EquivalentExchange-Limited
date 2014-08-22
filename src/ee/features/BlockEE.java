package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockEE extends Block
{
    public BlockEE(int par1, String name)
    {
        super(par1, Material.rock);
        this.setCreativeTab(EELimited.TabEE).setTextureName("ee:" + name).setUnlocalizedName(name);
        GameRegistry.registerBlock(this, name);
    }

    public BlockEE(int par1, Material material, String name)
    {
        super(par1, material);
        this.setCreativeTab(EELimited.TabEE).setTextureName("ee:" + name).setUnlocalizedName(name);
        GameRegistry.registerBlock(this, name);
    }
}
