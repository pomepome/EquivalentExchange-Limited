package ee.proxies;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import ee.addons.nei.NEIAddon;
import ee.features.EEBlocks;
import ee.features.EEItems;
import ee.features.KeyRegistry;
import ee.features.entities.EntityLavaProjectile;
import ee.features.entities.EntityMobRandomizer;
import ee.features.entities.EntityNovaPrimed;
import ee.features.entities.EntityWaterProjectile;
import ee.features.renderers.ChestItemRenderer;
import ee.features.renderers.ChestRenderer;
import ee.features.renderers.ColoredChestItemRenderer;
import ee.features.renderers.ColoredChestRenderer;
import ee.features.renderers.PedestalItemRenderer;
import ee.features.renderers.PedestalRenderer;
import ee.features.renderers.RenderNovaTNTPrimed;
import ee.features.tiles.DMPedestalTile;
import ee.features.tiles.TileEntityAlchChest;
import ee.features.tiles.TileEntityColoredAlchChest;
import ee.network.KeyEvent;
import ee.network.PacketHandler;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	@Override
	public EntityPlayer getEntityPlayerInstance()
	{
		return FMLClientHandler.instance().getClient().thePlayer;
	}
	public void registerRenderers()
	{
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EEBlocks.AlchChest),new ChestItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchChest.class,new ChestRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EEBlocks.cAlchChest),new ColoredChestItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityColoredAlchChest.class,new ColoredChestRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EEBlocks.DMPedestal), new PedestalItemRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(DMPedestalTile.class, new PedestalRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityLavaProjectile.class,new RenderSnowball(EEItems.LavaOrb));
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterProjectile.class,new RenderSnowball(EEItems.WaterOrb));
		RenderingRegistry.registerEntityRenderingHandler(EntityMobRandomizer.class,new RenderSnowball(EEItems.Randomizer));
		RenderingRegistry.registerEntityRenderingHandler(EntityNovaPrimed.class,new RenderNovaTNTPrimed());
	}
	public void registerKies()
	{
		KeyRegistry.registerKies();
	}
	public void registerClientOnlyEvents()
	{
		FMLCommonHandler.instance().bus().register(new KeyEvent());
	}
	public void registerNEIAddon()
	{
    	if(Loader.isModLoaded("NotEnoughItems"))
    	{
    		NEIAddon.register();
    	}
	}
}
