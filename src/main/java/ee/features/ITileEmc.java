package ee.features;

public interface ITileEmc
{
	/**
	 * Set the EMC value of this Tile Entity
	 * @param value The EMC amount to set
	 */
	void setEmc(int value);

	/**
	 * Add EMC to this Tile Entity
	 * @param value The EMC amount to add
	 */
	void addEmc(int value);

	/**
	 * Remove EMC from the tile entity.<br>
	 * @param value The EMC amount to remove
	 */
	void removeEmc(int value);

	/**
	 * @return The stored EMC in this TileEntity
	 */
	int getStoredEmc();

	/**
	 * @return Whether or not the EMC buffer is full.
	 */
	boolean hasMaxedEmc();

	/**
	 * If this returns true, the Tile Entity will accept EMC from any valid provider.<br>
	 * EMC will be received only on the server side.
	 * @return If this Tile Entity can accept EMC from adjacent providers
	 */
	boolean isRequestingEmc();
}
