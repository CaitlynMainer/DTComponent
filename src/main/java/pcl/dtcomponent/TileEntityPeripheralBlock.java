package pcl.dtcomponent;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Analyzable;
import li.cil.oc.api.network.ComponentConnector;
import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import mekanism.api.Pos3D;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;
import defense.api.ILauncherContainer;
import defense.api.ILauncherController;
import defense.core.Vector2;

@Interface(iface = "li.cil.oc.api.network.Environment", modid = "OpenComputers")
public class TileEntityPeripheralBlock extends TileEntity implements Environment, Analyzable //implements IPeripheral
{
	World _world;
	ILauncherContainer controller;
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
		player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "" + chtMsg));
		return null;
	}

	private ComponentConnector node = Network.newNode(this, Visibility.Network).withComponent("dt_bridge").withConnector(32).create();

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
			tile = worldObj.getTileEntity(xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
			System.out.println(this.controller.getController().getLauncherType().name());
			if(tile instanceof ILauncherController){
				if(!(this.controller instanceof ILauncherController))
				{

					if(DTComponent.cfg.hardMode) {
						this.controller = (ILauncherContainer)tile;
						
//						if (this.getBlockMetadata() == 0 && this.controller.getEnergyCapacity(direction) <= 50000) {
//							this.controller = (ILauncherContainer)tile;
//						} else if (this.getBlockMetadata() == 1 && this.controller.getEnergyCapacity(direction)<= 80000) {
//							this.controller = (ILauncherContainer)tile;
//						} else if (this.getBlockMetadata() == 2 && this.controller.getEnergyCapacity(direction) <= 100000) {
//							this.controller = (ILauncherContainer)tile;
//						} else {
//							this.controller = null;
//						}

					} else {
						this.controller = (ILauncherContainer)tile;
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
		
		if (DTComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				controller.getController().launch();
				return new Object[] { true };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		controller.getController().launch();
		return new Object[] { true };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] canLaunch(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");

		if (DTComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				return new Object[]{ controller.getController().canLaunch() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		return new Object[]{ controller.getController().canLaunch() };
		
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] getStatus(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");

		if (DTComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				return new Object[]{ controller.getController().getStatus() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		return new Object[]{ controller.getController().getStatus() };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] getMissileType(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");

		//TODO
		if (DTComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				return new Object[]{ controller.getController().getMissile().getExplosiveType().getMissileName() };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		return new Object[]{ controller.getController().getMissile().getExplosiveType().getMissileName() };
	}

	@Callback
	@Method(modid = "OpenComputers")
	public Object[] getTarget(Context context, Arguments args) throws Exception
	{
		if(controller == null)
			throw new Exception("Not connected to an ICBM launch controller(try using isConnected() first).");
		
		if (DTComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				Pos3D pos = controller.getController().getTarget();
				return new Object[]{ pos.xPos, pos.yPos, pos.zPos };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		
		Pos3D pos = controller.getController().getTarget();
		return new Object[]{ pos.xPos, pos.yPos, pos.zPos };
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
		
		
		if (DTComponent.cfg.hardMode) {
			if (node.changeBuffer(-5) == 0) {
				Pos3D pos = new Pos3D(args.checkDouble(0), args.checkDouble(1), args.checkDouble(2));
				controller.getController().setTarget(pos);
				return new Object[]{ pos.xPos, pos.yPos, pos.zPos };
			} else {
				throw new Exception("Not enough power in OC Network.");
			}
		}
		
		Pos3D pos = new Pos3D(args.checkDouble(0), args.checkDouble(1), args.checkDouble(2));
		controller.getController().setTarget(pos);
		return new Object[]{ pos.xPos, pos.yPos, pos.zPos };
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