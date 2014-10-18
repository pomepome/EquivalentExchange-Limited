package ee.features;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockEE extends Block
{
    public BlockEE(String name)
    {
        super(Material.rock);
        this.setCreativeTab(EELimited.TabEE).setBlockTextureName("ee:" + name).setBlockName(name);
        GameRegistry.registerBlock(this, name);
    }

    public BlockEE(Material material, String name)
    {
        super(material);
        this.setCreativeTab(EELimited.TabEE).setBlockTextureName("ee:" + name).setBlockName(name);
        GameRegistry.registerBlock(this, name);
    }
}