package pcl.dtcomponent;


import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class BlockPeripheralBlock extends BlockContainer
{
	public BlockPeripheralBlock(Material blockMaterial)
	{
		super(blockMaterial);
		//setCreativeTab(li.cil.oc.api.CreativeTab.Instance);
		//setUnlocalizedName("componentBlock");
		//setTextureName("icbmcomponent:componentBlock");
	}
	
	
	/*Tile entity stuff*/

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityPeripheralBlock();
	}
}