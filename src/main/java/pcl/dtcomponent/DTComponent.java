package pcl.dtcomponent;

import java.net.URL;
import java.lang.reflect.Field;

import li.cil.oc.api.Items;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = DTComponent.modid, name = BuildInfo.modName, version=BuildInfo.versionNumber + "." + BuildInfo.buildNumber, dependencies = "after:OpenComputers")
//@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class DTComponent
{
	public static final String modid = BuildInfo.modID;
	public static Block peripheralBlock;
	public static Block peripheralMultiBlock;
	public static Config cfg = null;
	public static final Logger logger = LogManager.getFormatterLogger(BuildInfo.modID);
	
	@Instance("DTComponent")
	public static DTComponent instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
			cfg = new Config(new Configuration(event.getSuggestedConfigurationFile()));
	        ItemStack microchip    = null;
	        ItemStack microchip2    = null;
	        ItemStack microchip3    = null;
	        ItemStack pcb		   = null;
					
	        
	        if(cfg.hardMode == true) {
	        	
	        	
	    		// Check for Mod Update Detector
	    		if (event.getSourceFile().getName().endsWith(".jar") && event.getSide().isClient() && Config.enableMUD) {
	    			logger.info("Registering mod with OpenUpdater.");
	    			try {
	    				Class.forName("pcl.mud.OpenUpdater").getDeclaredMethod("registerMod", ModContainer.class, URL.class, URL.class).invoke(null, FMLCommonHandler.instance().findContainerFor(this),
	    						new URL("http://PC-Logix.com/DTComponent/get_latest_build.php?mcver=1.7.10"),
	    						new URL("http://PC-Logix.com/DTComponent/changelog.php?mcver=1.7.10"));
	    			} catch (Throwable e) {
	    				logger.info("OpenUpdater is not installed, not registering.");
	    			}
	    		}
	        	
	        	peripheralMultiBlock = new BlockPeripheraMultilBlock(Material.rock);
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
	        	peripheralBlock = new BlockPeripheralBlock(Material.rock);
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
