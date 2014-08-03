package main.extremeblocks.blocks;

import main.com.hk.testing.util.BlockCustom;
import main.extremeblocks.Init;
import main.extremeblocks.Vars;
import net.minecraft.block.material.Material;

public class BlockXrayBlock extends BlockCustom
{
	private final boolean isOn;

	public BlockXrayBlock(boolean isOn)
	{
		super(Material.glass, (isOn ? "Unf" : "F") + "inished Xray Block");
		this.setBlockTextureName(Init.MODID + ":xrayblock");
		this.setCreativeTab(Init.tab_mainBlocks);

		this.isOn = isOn;
	}

	public boolean renderAsNormalBlock()
	{
		return Vars.xrayBlockWork ? !isOn : false;
	}

	public boolean isOpaqueCube()
	{
		return Vars.xrayBlockWork ? !isOn : false;
	}
}