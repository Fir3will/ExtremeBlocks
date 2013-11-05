package ExtremeBlocks.Blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import ExtremeBlocks.ExtremeBlocksMain;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCementWall extends Block {
	public BlockCementWall(int par1, Material par2) {
		super(par1, par2);
		this.setHardness(2.0F);
		this.setUnlocalizedName("CementWall");
		this.setCreativeTab(ExtremeBlocksMain.EBBasicBlocksTab);
		this.setStepSound(soundStoneFootstep);
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return this.blockID;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon(ExtremeBlocksMain.modid
				+ ":" + (this.getUnlocalizedName().substring(5)));
	}
}