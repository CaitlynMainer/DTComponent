package pcl.dtcomponent;


import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


public class BlockPeripheraMultilBlock extends BlockContainer
{
	public BlockPeripheraMultilBlock(Material blockMaterial)
	{
		super(blockMaterial);
		setCreativeTab(li.cil.oc.api.CreativeTab.instance);
		//setUnlocalizedName("componentBlock");
		//setTextureName("icbmcomponent:componentBlock");
	}
	
	public int damageDropped(int par1)
   	{
        return par1;
    }
	
    @Override
    public void registerBlockIcons(IIconRegister reg) {
	       icons = new IIcon[3];
           
	       for(int i = 0; i < icons.length; i++)
	       {
	        icons[i] = reg.registerIcon("icbmcomponent:componentBlock"+i);
	       }
    }
	
	private IIcon[] icons;

	@Override
	public IIcon getIcon(int par1, int par2)
	{
	       return icons[par2];
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
	    for (int var4 = 0; var4 < 3; ++var4) {
	    	list.add(new ItemStack(item, 1, var4));
	    }
	}
	
	/*Tile entity stuff*/
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileEntityPeripheralBlock();
	}
}
