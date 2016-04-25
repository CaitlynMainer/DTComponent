package pcl.dtcomponent;

/**
 * @author Caitlyn
 *
 */

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemPeripheralBlock extends ItemBlock {

	public ItemPeripheralBlock(Block p_i45328_1_) {
		super(p_i45328_1_);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	
	@Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
          String name = "";
          switch(itemstack.getItemDamage())
          {
                 case 0:
                 {
                        name = "OC ICBM Component Block T1";
                        break;
                 }
                 case 1:
                 {
                        name = "OC ICBM Component Block T2";
                        break;
                 }
                 case 2:
                 {
                     	name = "OC ICBM Component Block T3";
                     	break;
                 }
                 default: name = "broken";
          }
          return getUnlocalizedName() + "." + name;
    }

}