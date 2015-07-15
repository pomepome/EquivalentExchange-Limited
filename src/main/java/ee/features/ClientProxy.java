package ee.features;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import ee.features.entity.EntityLavaProjectile;
import ee.features.entity.EntityMobRandomizer;
import ee.features.entity.EntityWaterProjectile;
import ee.features.renderer.ChestItemRenderer;
import ee.features.renderer.ChestRenderer;
import ee.features.tile.TileEntityAlchChest;
import ee.network.KeyEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public EntityPlayer getEntityPlayerInstance()
	{
		return FMLClientHandler.instance().getClient().thePlayer;
	}
	public void registerRenderers()
	{
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EELimited.AlchChest),new ChestItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchChest.class,new ChestRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityLavaProjectile.class,new RenderSnowball(EELimited.LavaOrb));
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterProjectile.class,new RenderSnowball(EELimited.WaterOrb));
		RenderingRegistry.registerEntityRenderingHandler(EntityMobRandomizer.class,new RenderSnowball(EELimited.Randomizer));
	}
	public void registerKies()
	{
		KeyRegistry.registerKies();
	}
	public void registerClientOnlyEvents()
	{
		FMLCommonHandler.instance().bus().register(new KeyEvent());
	}
}
