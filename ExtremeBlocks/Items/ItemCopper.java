package ExtremeBlocks.Items;

import ExtremeBlocks.ExtremeBlocksMain;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCopper extends Item 
{
	public ItemCopper(int par1) 
	{
		super(par1);
		this.setCreativeTab(ExtremeBlocksMain.EBBasicItemsTab);
		this.maxStackSize = 64;
		this.setUnlocalizedName("Copper");
	}
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(ExtremeBlocksMain.modid + ":" + (this.getUnlocalizedName().substring(5)));
    }
}