package main.extremeblocks.nei;

import java.util.ArrayList;
import java.util.List;
import main.com.hk.eb.util.IInfo;
import main.com.hk.eb.util.JavaHelp;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EBInfoRecipeHandler implements IUsageHandler, ICraftingHandler
{
	public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	public static int color = -12566464;
	ItemStack displayItem;
	boolean precise = false;
	String id;
	String name;
	String[] info;
	public boolean checkedOrder = false;
	public IInfo infoObj;
	int noLinesPerPage = 12;

	public EBInfoRecipeHandler()
	{
		displayItem = null;
	}

	public boolean checkOrder()
	{
		if (checkedOrder) return false;
		checkedOrder = true;
		return changeOrder(GuiUsageRecipe.usagehandlers) | changeOrder(GuiCraftingRecipe.craftinghandlers);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean changeOrder(ArrayList list)
	{
		int j = -1;
		for (int i = 0; i < list.size() - 1; i++)
		{
			if (list.get(i).getClass() == getClass())
			{
				j = i;
				break;
			}
		}
		if (j >= 0)
		{
			list.add(list.remove(j));
		}
		return false;
	}

	public EBInfoRecipeHandler(ItemStack stack)
	{
		Item item = stack.getItem();
		Block block = item instanceof ItemBlock ? ((ItemBlock) item).field_150939_a : null;
		if (item instanceof IInfo)
		{
			id = stack.getUnlocalizedName();
			name = StatCollector.translateToLocal(stack.getUnlocalizedName());
			precise = true;
			infoObj = (IInfo) item;
		}
		else if (block instanceof IInfo)
		{
			id = block.getUnlocalizedName();
			name = StatCollector.translateToLocal(block.getUnlocalizedName());
			precise = false;
			infoObj = (IInfo) block;
		}

		if (infoObj != null)
		{
			List<String> strings = splitString(infoObj.getInfo());
			info = strings.toArray(new String[strings.size()]);
		}

		displayItem = stack.copy();
		displayItem.stackSize = 1;
	}

	public List<String> splitString(String a)
	{
		List<String> list = JavaHelp.newArrayList();
		List<?> b = fontRenderer.listFormattedStringToWidth(a, getWidth() - 8);
		if (b.size() < noLinesPerPage)
		{
			list.add(a);
		}
		else
		{
			String c = "";
			for (int j = 0; j < b.size(); j++)
			{
				c = c + b.get(j) + " ";

				if (j > 0 && j % noLinesPerPage == 0)
				{
					String d = c.trim();
					list.add(d);
					c = "";
				}
			}

			c = c.trim();
			if (!"".equals(c))
			{
				list.add(c);
			}
		}
		return list;
	}

	@Override
	public String getRecipeName()
	{
		if (displayItem == null) return "Documentation";
		String s = Item.itemRegistry.getNameForObject(displayItem.getItem());

		String modid = s.split(":")[0];

		if ("minecraft".equals(modid)) return "Minecraft";
		ModContainer selectedMod = Loader.instance().getIndexedModList().get(modid);

		if (selectedMod == null) return modid;
		if (!selectedMod.getMetadata().autogenerated) return selectedMod.getMetadata().name;
		return selectedMod.getName();
	}

	@Override
	public int numRecipes()
	{
		return displayItem == null || info == null ? 0 : info.length;
	}

	@Override
	public void drawBackground(int recipe)
	{
	}

	public int getWidth()
	{
		return 166;
	}

	@Override
	public PositionedStack getResultStack(int recipe)
	{
		return new PositionedStack(displayItem, getWidth() / 2 - 9, 0, false);
	}

	@Override
	public void drawForeground(int recipe)
	{
		List<?> text = fontRenderer.listFormattedStringToWidth(info[recipe], getWidth() - 8);

		for (int i = 0; i < text.size(); i++)
		{
			String t = (String) text.get(i);
			GuiDraw.drawString(t, getWidth() / 2 - GuiDraw.getStringWidth(t) / 2, 18 + i * 8, color, false);
		}
	}

	@Override
	public List<PositionedStack> getIngredientStacks(int recipe)
	{
		return JavaHelp.newArrayList();
	}

	@Override
	public List<PositionedStack> getOtherStacks(int recipetype)
	{
		return JavaHelp.newArrayList();
	}

	@Override
	public void onUpdate()
	{
	}

	@Override
	public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
	{
		return false;
	}

	@Override
	public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe)
	{
		return null;
	}

	@Override
	public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe)
	{
		return null;
	}

	@Override
	public int recipiesPerPage()
	{
		return 1;
	}

	@Override
	public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe)
	{
		return currenttip;
	}

	@Override
	public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currenttip, int recipe)
	{
		return currenttip;
	}

	@Override
	public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe)
	{
		return false;
	}

	@Override
	public boolean mouseClicked(GuiRecipe gui, int button, int recipe)
	{
		return false;
	}

	public boolean isValidItem(ItemStack stack)
	{
		Item item = stack.getItem();
		Block block = item instanceof ItemBlock ? ((ItemBlock) item).field_150939_a : null;
		return item instanceof IInfo || block instanceof IInfo;
	}

	@Override
	public IUsageHandler getUsageHandler(String inputId, Object... ingredients)
	{
		if (!inputId.equals("item")) return this;

		for (Object ingredient : ingredients)
		{
			if (ingredient instanceof ItemStack && isValidItem((ItemStack) ingredient)) return new EBInfoRecipeHandler((ItemStack) ingredient);
		}

		return this;
	}

	@Override
	public ICraftingHandler getRecipeHandler(String outputId, Object... results)
	{
		if (!outputId.equals("item")) return this;

		for (Object result : results)
		{
			if (result instanceof ItemStack && isValidItem((ItemStack) result)) return new EBInfoRecipeHandler((ItemStack) result);
		}
		return this;
	}
}