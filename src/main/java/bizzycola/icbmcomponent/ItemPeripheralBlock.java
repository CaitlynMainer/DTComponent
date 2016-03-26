package bizzycola.icbmcomponent;

/**
 * @author Caitlyn
 *
 */

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemPeripheralBlock extends ItemBlock {

	public ItemPeripheralBlock(int id) {
		super(id);
		if(ICBMComponent.cfg.hardMode)
			setHasSubtypes(true);
		setUnlocalizedName("PeripheralItemBlock");
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