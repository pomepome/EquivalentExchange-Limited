package ee.proxies;

import ee.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {
	public EntityPlayer getEntityPlayerInstance()
	{
		return null;
	}
	public void registerRenderers() {}
	public void registerKies(){}
	public void registerClientOnlyEvents(){}
	public void registerNEIAddon(){}
}
