package pcl.dtcomponent;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

/**
 * @author Caitlyn
 *
 */
public class Config
{
	public static boolean enableMUD = true;
    private boolean defaultHardMode = false;
    public final boolean hardMode;

    public Config(Configuration config)
    {
        config.load();
		enableMUD = config.get("general", "enableMUD", enableMUD, "Automatically check for mod updates.").getBoolean();
        hardMode = config.get("options", "HardMode", defaultHardMode, "Enable hard mode? Enables power usage, and multiple tiers of interfaces").getBoolean(defaultHardMode);
        if( config.hasChanged() )
        {
            config.save();
        }
    }
}
