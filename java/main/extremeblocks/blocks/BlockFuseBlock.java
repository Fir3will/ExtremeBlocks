package main.extremeblocks.blocks;

import java.util.Random;
import main.com.hk.eb.util.BlockCustom;
import main.com.hk.eb.util.MPUtil;
import main.extremeblocks.Init;
import main.extremeblocks.tileentities.TileEntityFuse;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFuseBlock extends BlockCustom
{
	public BlockFuseBlock()
	{
		super(Material.circuits, "Fuse Block");
		setBlockTextureName(Init.MODID + ":fuse");
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15F, 1.0F);
		tileClass = TileEntityFuse.class;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return world.getBlock(x, y - 1, z).isOpaqueCube();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return Init.fuse;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return Init.fuse;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int lol, float sideX, float sideY, float sideZ)
	{
		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
		{
			if (MPUtil.isServerSide())
			{
				world.setBlock(x, y, z, Blocks.fire);
			}
			player.getCurrentEquippedItem().damageItem(1, player);
			return true;
		}
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (MPUtil.isServerSide())
		{
			if (!canPlaceBlockAt(world, x, y, z))
			{
				this.dropBlockAsItem(world, x, y, z, 0, 0);
				world.setBlockToAir(x, y, z);
			}
			super.onNeighborBlockChange(world, x, y, z, block);
		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}
}
