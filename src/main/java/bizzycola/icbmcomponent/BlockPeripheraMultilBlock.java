package bizzycola.icbmcomponent;


import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;


public class BlockPeripheraMultilBlock extends BlockContainer
{
	public BlockPeripheraMultilBlock(int blockID, Material blockMaterial)
	{
		super(blockID, blockMaterial);
		setCreativeTab(li.cil.oc.api.CreativeTab.instance);
		setUnlocalizedName("componentBlock");
		setTextureName("icbmcomponent:componentBlock");
	}
	
	public int damageDropped(int par1)
   	{
        return par1;
    }
	

	private Icon[] icons;
	      
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
	       icons = new Icon[3];
	            
	       for(int i = 0; i < icons.length; i++)
	       {
	        icons[i] = par1IconRegister.registerIcon("icbmcomponent:componentBlock"+i);
	       }
	}
	

	@Override
	public Icon getIcon(int par1, int par2)
	{
	       return icons[par2];
	}
	

	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
	    for (int var4 = 0; var4 < 3; ++var4)
	    {
	        par3List.add(new ItemStack(par1, 1, var4));
	    }
	}
	
	/*Tile entity stuff*/
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityPeripheralBlock();
	}
}
