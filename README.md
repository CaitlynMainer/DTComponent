ICBMComponent
=============

An OpenComputers Component block for the ICBM mod.


Methods
-------
**(bool)isConnected()**

--Returns true if the block is connected(placed next to) an ICBM launch controller.


**(bool)launch()**

--Launches the missile


**(bool)canLaunch()**

--Returns true if the missile can launch, false otherwise.


**(double, double, double)getTarget()**

--Gets the launchers current x,y,z.


**(void)setTarget(double, double, double)**

--Sets the launchers current x,y,z. X,Z are the position, Y is the detonation height(how many blocks above the ground it'll explode.)

**(string)getMissileType()**

--Returns the name of the missle currently loaded.

**(string)getStatus()**

--Returns the launcher status. Use this to tell the user why the missile won't launch if canLaunch is false.


Downloads
---------
Latest Builds: http://pc-logix.com/mods/ICBMComponent/
