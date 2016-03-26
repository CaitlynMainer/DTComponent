package bizzycola.icbmcomponent;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * @author Caitlyn
 *
 */
public class Config
{
    private int defaultComponentID = 2249;
    public final int componentID;
    
    private boolean defaultHardMode = false;
    public final boolean hardMode;

    public Config(Configuration config)
    {
        config.load();
        componentID = config.get("blocks", "ComponentID", defaultComponentID).getInt(defaultComponentID);
        hardMode = config.get("options", "HardMode", defaultHardMode, "Enable hard mode? Enables power usage, and multiple tiers of interfaces").getBoolean(defaultHardMode);
        if( config.hasChanged() )
        {
            config.save();
        }
    }
}
