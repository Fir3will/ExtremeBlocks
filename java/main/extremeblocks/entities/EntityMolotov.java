package main.extremeblocks.entities;

import main.com.hk.eb.util.MPUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityMolotov extends EntityThrowable
{
	public EntityMolotov(World par1World)
	{
		super(par1World);
	}

	public EntityMolotov(World par1World, EntityLivingBase par2EntityLivingBase)
	{
		super(par1World, par2EntityLivingBase);
	}

	public EntityMolotov(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}

	public EntityMolotov(World par1World, EntityLivingBase par2EntityLivingBase, float power)
	{
		super(par1World, par2EntityLivingBase);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop)
	{
		switch (mop.sideHit)
		{
			case 0: // BOTTOM
				mop.blockY--;
				break;
			case 1: // TOP
				mop.blockY++;
				break;
			case 2: // EAST
				mop.blockZ--;
				break;
			case 3: // WEST
				mop.blockZ++;
				break;
			case 4: // NORTH
				mop.blockX--;
				break;
			case 5: // SOUTH
				mop.blockX++;
				break;
		}
		if (MPUtil.isServerSide())
		{
			if (mop.typeOfHit == MovingObjectType.BLOCK)
			{
				worldObj.newExplosion(this, posX, posY, posZ, 5.0F, true, false);
			}
			if (mop.typeOfHit == MovingObjectType.ENTITY)
			{
				worldObj.newExplosion(this, posX, posY, posZ, 5.0F, true, false);
			}
			setDead();
		}
	}
}
