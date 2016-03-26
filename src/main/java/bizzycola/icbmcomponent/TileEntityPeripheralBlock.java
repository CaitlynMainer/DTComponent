package bizzycola.icbmcomponent;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.Analyzable;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.ComponentConnector;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import resonant.api.explosion.ILauncherController;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;
import universalelectricity.api.energy.IEnergyContainer;
import universalelectricity.api.vector.Vector3;

@Interface(iface = "li.cil.oc.api.network.Environment", modid = "OpenComputers")
public class TileEntityPeripheralBlock extends TileEntity implements Environment, Analyzable //implements IPeripheral
{
	World _world;
	ILauncherController controller;
	TileEntity tile = null;
	Integer launcherTier = null;
	Integer  interfaceTier = null;

	//String[] ccMethods;
	public TileEntityPeripheralBlock()
	{
		//ocMethods = new String[] { "launch", "canLaunch", "getStatus", "getTarget", "setTarget", "getLauncherType" };
	}

	@Override
	public Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		String chtMsg = (controller == null) ? "No ICBM launch controller connected." : "ICBM launch controller connected.";
		player.addChatMessage(chtMsg);
		return null;
	}

	private ComponentConnector node = Network.newNode(this, Visibility.Network).withComponent("icbm_bridge").withConnector(32).create();

	@Override
	public Node node() {
		return node;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (node != null) node.remove();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (node != null) node.remove();
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (node != null && node.network() == null) {
			Network.joinOrCreateNetwork(this);
		}

		if(!(tile instanceof ILauncherController)){
			this.controller = null;
		}

		for(ForgeDirection direction: ForgeDirection.VALID_DIRECTIONS){
			tile = worldObj.getBlockTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
			if(tile instanceof ILauncherController){
				if(!(this.controller instanceof ILauncherController))
				{

					if(ICBMComponent.cfg.hardMode) {
						this.controller = (ILauncherController)tile;
						
						if (this.getBlockMetadata() == 0 && this.controller.getEnergyCapacity(direction) <= 50000) {
							this.controller = (ILauncherController)tile;
						} else if (this.getBlockMetadata() == 1 && this.controller.getEnergyCapacity(direction)<= 80000) {
							this.controller = (ILauncherController)tile;
						} else if (this.getBlockMetadata() == 2 && this.controller.getEnergyCapacity(direction) <= 100000) {
							this.controller = (ILauncherController)tile;
						} else {
							this.controller = null;
						}

					} else {
						this.controller = (ILauncherController)tile;
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		node.load(par1NBTTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		node.save(par1NBTTagCompound);
	}


	/*Lua*/

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] isConnected(Context context, Arguments args)
	{
		boolean isConnected = (controller == null) ? false : true;
		return new Object[]{ isConnected };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] launch(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");
		
		if (ICBMComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				controller.launch();
				return new Object[] { true };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		controller.launch();
		return new Object[] { true };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] canLaunch(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");

		if (ICBMComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				return new Object[]{ controller.canLaunch() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		return new Object[]{ controller.canLaunch() };
		
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] getStatus(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");

		if (ICBMComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				return new Object[]{ controller.getStatus() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		return new Object[]{ controller.getStatus() };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] getMissileType(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");

		//TODO
		if (ICBMComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				return new Object[]{ controller.canLaunch() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		return new Object[]{ controller.getMissile().getExplosiveType().getMissileName() };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] getTarget(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");
		
		if (ICBMComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				Vector3 pos = controller.getTarget();
				return new Object[]{ pos.x(), pos.y(), pos.z() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		
		Vector3 pos = controller.getTarget();
		return new Object[]{ pos.x(), pos.y(), pos.z() };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] setTarget(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");

		if(!args.isDouble(0) || !args.isDouble(1) || !args.isDouble(2))
		{
			throw new Exception("setTarget expects 3 double as arguments(x,y,z).");
		}
		
		
		if (ICBMComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				Vector3 pos = new Vector3(args.checkDouble(0), args.checkDouble(1), args.checkDouble(2));
				controller.setTarget(pos);
				return new Object[]{ pos.x(), pos.y(), pos.z() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		
		Vector3 pos = new Vector3(args.checkDouble(0), args.checkDouble(1), args.checkDouble(2));
		controller.setTarget(pos);
		return new Object[]{ pos.x(), pos.y(), pos.z() };
	}


	@Override
	public void onConnect(Node node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnect(final Node node) {
		node.remove();
	}


	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

	}

}