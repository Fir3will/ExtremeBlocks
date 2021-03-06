package main.extremeblocks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import main.com.hk.eb.util.JavaHelp;
import main.extremeblocks.worldgen.Generation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vars
{
	public static boolean guidePausesGame;
	public static boolean xrayBlockWork;
	public static boolean customCraftingTable;
	public static boolean addVanillaRecipes;
	public static String counterMessage;

	public static boolean alterWorld;
	public static Map<Class<? extends Generation>, Boolean> gens = JavaHelp.newHashMap();

	public static boolean addMobs;

	public static boolean addLightedBlocks;
	public static boolean addFakeFloors;
	public static boolean checkVersion;
	public static boolean traditionalTrash;

	public static int copperSR;
	public static int trinquantiumSR;
	public static int tinSR;
	public static int glesterSR;
	public static int delvlishSR;
	public static int silverSR;
	public static int meteoriteSR;
	public static int onyxSR;
	public static int fluoriteSR;
	public static int compactStoneSR;
	public static int boneDirtSR;
	public static boolean playingIslands;
	// Cactus
	public static int numbOfCactus;
	public static int spawnedCactus;
	// Pumpkins
	public static int numbOfPumps;
	public static int spawnedPumps;
	// Melons
	public static int numbOfMelons;
	public static int spawnedMelons;
	// Trees
	public static int numbOfTrees;
	public static int spawnedTrees;
	public static final String CG = Config.generalConfig;
	public static final String SR = Config.oresConfig;
	public static final String GO = Config.worldConfig;
	public static final String CM = Config.mobConfig;

	public static final String LONG_MAX = "9,223,372,036,854,775,807";
	public static final String DOUBLE_MAX = "9,218,868,437,227,405,311";
	public static final String FLOAT_MAX = "5,183,643,170,566,569,984";
	public static final String INTEGER_MAX = "2,147,483,647";
	public static final String SHORT_MAX = "32,767";
	public static final String BYTE_MAX = "127";

	public static Logger logger = LogManager.getLogger("EB");

	public static void p(Object... o)
	{
		String s = "";
		for (int i = 0; i < o.length; i++)
		{
			s += "{" + o[i] + (i == o.length - 1 ? "}" : "}, ");
		}
		System.out.println(s);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Mob
	{
		String getName();

		String getVanillaName();
	}
}
