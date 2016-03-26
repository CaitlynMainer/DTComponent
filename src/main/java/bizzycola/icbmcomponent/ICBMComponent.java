package bizzycola.icbmcomponent;

import java.net.URL;
import java.util.logging.Logger;
import java.lang.reflect.Field;

import li.cil.oc.api.Items;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


@Mod(modid = ICBMComponent.modid, name = BuildInfo.modName, version=BuildInfo.versionNumber + "." + BuildInfo.buildNumber, dependencies = "after:OpenComputers")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ICBMComponent
{
	public static final String modid = BuildInfo.modID;
	public static Block peripheralBlock;
	public static Block peripheralMultiBlock;
	public static Config cfg = null;
	
	@Instance("ICBMComponent")
	public static ICBMComponent instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
			cfg = new Config(new Configuration(event.getSuggestedConfigurationFile()));
	        ItemStack microchip    = null;
	        ItemStack microchip2    = null;
	        ItemStack microchip3    = null;
	        ItemStack pcb		   = null;
					
	        
	        if(cfg.hardMode == true) {
	        	peripheralMultiBlock = new BlockPeripheraMultilBlock(cfg.componentID,Material.rock);
	        	System.out.println("[ICBMComponent] Enabling HardMode");
	        	GameRegistry.registerBlock(peripheralMultiBlock, ItemPeripheralBlock.class, modid + peripheralMultiBlock.getUnlocalizedName().substring(5));
	        	
	    		LanguageRegistry.addName(new ItemStack(peripheralMultiBlock, 1, 0), "OC ICBM Component Block T1");
	    		LanguageRegistry.addName(new ItemStack(peripheralMultiBlock, 1, 1), "OC ICBM Component Block T2");
	    		LanguageRegistry.addName(new ItemStack(peripheralMultiBlock, 1, 2), "OC ICBM Component Block T3");
	    		
	    		    peripheralBlock.setCreativeTab(li.cil.oc.api.CreativeTab.instance);
	    	        microchip    = Items.get("chip1").createItemStack(1);
	    	        microchip2    = Items.get("chip2").createItemStack(1);
	    	        microchip3    = Items.get("chip3").createItemStack(1);
	    		    pcb = Items.get("printedCircuitBoard").createItemStack(1);
	    		
		        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(peripheralMultiBlock, 1, 0),
		                "IMI",
		                "MPM",
		                "IMI",
		                'I', "nuggetIron", 'M', microchip, 'P', pcb));
		        
		        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(peripheralMultiBlock, 1, 1),
		                "IMI",
		                "MPM",
		                "IMI",
		                'I', "nuggetIron", 'M', microchip2, 'P', pcb));
		        
		        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(peripheralMultiBlock, 1, 2),
		                "IMI",
		                "MPM",
		                "IMI",
		                'I', "nuggetIron", 'M', microchip3, 'P', pcb));
	    		
	        } else {
	        	peripheralBlock = new BlockPeripheralBlock(cfg.componentID,Material.rock);
		        GameRegistry.registerBlock(peripheralBlock, modid + peripheralBlock.getUnlocalizedName().substring(5));
		        
		        LanguageRegistry.addName(peripheralBlock, "OC ICBM Component Block");
	    		    peripheralBlock.setCreativeTab(li.cil.oc.api.CreativeTab.instance);
	    	        microchip    = Items.get("chip1").createItemStack(1);
	    	        microchip2    = Items.get("chip2").createItemStack(1);
	    	        microchip3    = Items.get("chip3").createItemStack(1);
	    		    pcb = Items.get("printedCircuitBoard").createItemStack(1);
		        GameRegistry.addRecipe( new ShapedOreRecipe( new ItemStack(peripheralBlock, 1),
		                "IMI",
		                "MPM",
		                "IMI",
		                'I', "nuggetIron", 'M', microchip, 'P', pcb));
		        
	        }
	        

	        
	        GameRegistry.registerTileEntity(TileEntityPeripheralBlock.class,"TileEntityPeripheralBlock");


	}
}
