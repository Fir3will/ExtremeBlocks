package main.extremeblocks.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderPipe implements IItemRenderer
{
	private ModelPipe model;
	private final String fileName;

	public ItemRenderPipe(String fileName)
	{
		this.fileName = fileName;
		this.model = new ModelPipe();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		(new TileEntityPipeRender(fileName)).render(0.0D, 0.0D, 0.0D);
	}

}