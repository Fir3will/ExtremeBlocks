package ExtremeBlocks.Blocks;

import java.util.List;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ExtremeBlocks.ExtremeBlocksMain;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFences extends BlockFence
{
       public BlockFences(int id, Material material)
       {
              super(id, "fences", material);
              this.setCreativeTab(ExtremeBlocksMain.EBFencesTab);
       }
      
    @Override
    public int damageDropped(int par1)
    {
        return par1;
    }
      
       @SideOnly(Side.CLIENT)
    private Icon[] icons;
  
       
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        icons = new Icon[15];
        
        for(int i = 0; i < icons.length; i++)
        {
        	String Name = "Fences-";
            icons[i] = par1IconRegister.registerIcon(ExtremeBlocksMain.modid + ":" + Name + i);
        }
    }
  
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
        switch(par2)
        {
            case 0:
            	return icons[0];
            case 1:
            	return icons[1];
            case 2:
            	return icons[2];
            case 3:
            	return icons[3];
            case 4:
            	return icons[4];
            case 5:
            	return icons[5];
            case 6:
            	return icons[6];  	
            case 7:
            	return icons[7];
            case 8:
            	return icons[8];
            case 9:
            	return icons[9];
            case 10:
            	return icons[10];
            case 11:
            	return icons[11];
            case 12:
            	return icons[12];
            case 13:
            	return icons[13];
            case 14:
            	return icons[14];
            case 15:
            	return icons[15];
            	
            default:
            {
            	System.out.println("Invalid metadata for " + this.getUnlocalizedName());
              	return icons[0];
            }
        }
    }
  
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
          for(int i = 0; i < 15; i++)
          {
                 par3List.add(new ItemStack(par1, 1, i));
          }
    }

}

