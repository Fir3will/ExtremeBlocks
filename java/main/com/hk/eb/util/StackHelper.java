package main.com.hk.eb.util;

import java.util.List;
import java.util.Map;
import main.extremeblocks.util.StackInv;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class StackHelper
{
	/**
	 * Checks for the given Enchantment on the specified stack. Also checks if the level is enough to pass.
	 * 
	 * @param stack The stack to check
	 * @param ench The enchantment to check for
	 * @param level The level to check for. If this is < 1, it will check just for the enchantment
	 * @param strict Whether the enchantment should have the exact level,
	 * no more. If this is off then if the enchantment level is higher then
	 * the level you're looking for, it will return true.
	 * @return if the stack has the enchantment with the given level
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasEnchantment(ItemStack stack, Enchantment ench, int level, boolean strict)
	{
		Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(stack);
		if (map.containsKey(new Integer(ench.effectId))) return strict ? map.get(new Integer(ench.effectId)) == level : map.get(new Integer(ench.effectId)) >= level;
		return false;
	}

	public static int getMaxMetadataOf(Item item)
	{
		return item.getMaxDamage();
	}

	public static boolean isBlockOrItem(Object obj)
	{
		return obj instanceof Block || obj instanceof Item;
	}

	public static boolean isEmpty(ItemStack... stacks)
	{
		if (stacks != null)
		{
			for (ItemStack stack : stacks)
			{
				if (stack != null) return false;
			}
		}
		return true;
	}

	public static void getRidOfNulls(ItemStack... stacks)
	{
		for (int i = 0; i < stacks.length; i++)
		{
			if (stacks[i] != null && stacks[i].stackSize <= 0)
			{
				stacks[i] = null;
			}
		}
	}

	public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == stack2) return true;
		if (stack1 == null || !stack1.isStackable() || !stack2.isStackable()) return false;
		return ItemStack.areItemStacksEqual(stack1, stack2);
	}

	public static int mergeStacks(ItemStack mergeSource, ItemStack mergeTarget, boolean doMerge)
	{
		if (!canStacksMerge(mergeSource, mergeTarget)) return 0;
		int mergeCount = Math.min(mergeTarget.getMaxStackSize() - mergeTarget.stackSize, mergeSource.stackSize);
		if (mergeCount < 1) return 0;
		if (doMerge)
		{
			mergeTarget.stackSize += mergeCount;
			mergeSource.stackSize -= mergeCount;
			if (mergeSource.stackSize < 0)
			{
				mergeSource = null;
			}
		}
		return mergeCount;
	}

	public static boolean[] addToInv(IInventory inv, ItemStack... itemstacks)
	{
		boolean[] val = new boolean[itemstacks.length];
		for (int i = 0; i < itemstacks.length; i++)
		{
			val[i] = addToInv(inv, itemstacks[i]);
		}
		return val;
	}

	public static boolean addToInv(IInventory inv, ItemStack par1ItemStack)
	{
		List<ItemStack> inventorySlots = JavaHelp.newArrayList();
		boolean flag1 = false;
		int k = 0;
		ItemStack itemstack1 = null;
		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			inventorySlots.add(inv.getStackInSlot(i));
		}
		if (par1ItemStack == null) return false;
		if (par1ItemStack.isStackable())
		{
			while (par1ItemStack.stackSize > 0 && k < inv.getSizeInventory())
			{
				itemstack1 = inventorySlots.get(k);
				if (itemstack1 != null && itemstack1.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
				{
					int l = itemstack1.stackSize + par1ItemStack.stackSize;
					if (l <= par1ItemStack.getMaxStackSize())
					{
						par1ItemStack.stackSize = 0;
						itemstack1.stackSize = l;
						inv.markDirty();
						flag1 = true;
					}
					else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
					{
						par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = par1ItemStack.getMaxStackSize();
						inv.markDirty();
						flag1 = true;
					}
				}
				++k;
			}
		}
		if (par1ItemStack.stackSize > 0)
		{
			k = 0;
			while (k < inv.getSizeInventory())
			{
				itemstack1 = inventorySlots.get(k);
				if (itemstack1 == null)
				{
					inv.setInventorySlotContents(k, par1ItemStack.copy());
					inv.markDirty();
					par1ItemStack.stackSize = 0;
					flag1 = true;
					break;
				}
				++k;
			}
		}
		if (itemstack1 != null && itemstack1.stackSize == 0)
		{
			itemstack1 = null;
		}
		if (par1ItemStack != null && par1ItemStack.stackSize == 0)
		{
			par1ItemStack = null;
		}
		return flag1;
	}

	public static boolean addToInvSlot(IInventory inv, ItemStack par1ItemStack, int slot)
	{
		if (par1ItemStack == null || slot < 0 || slot >= inv.getSizeInventory()) return false;
		boolean flag1 = false;
		ItemStack itemstack1 = inv.getStackInSlot(slot);
		if (par1ItemStack.isStackable())
		{
			if (itemstack1 != null && itemstack1.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
			{
				int l = itemstack1.stackSize + par1ItemStack.stackSize;
				if (l <= par1ItemStack.getMaxStackSize())
				{
					par1ItemStack.stackSize = 0;
					itemstack1.stackSize = l;
					inv.markDirty();
					flag1 = true;
				}
				else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize())
				{
					par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
					itemstack1.stackSize = par1ItemStack.getMaxStackSize();
					inv.markDirty();
					flag1 = true;
				}
			}
		}
		if (par1ItemStack.stackSize > 0)
		{
			itemstack1 = inv.getStackInSlot(slot);
			if (itemstack1 == null)
			{
				inv.setInventorySlotContents(slot, par1ItemStack.copy());
				inv.markDirty();
				par1ItemStack.stackSize = 0;
				flag1 = true;
			}
		}
		if (itemstack1 != null && itemstack1.stackSize == 0)
		{
			itemstack1 = null;
		}
		if (par1ItemStack != null && par1ItemStack.stackSize == 0)
		{
			par1ItemStack = null;
		}
		return flag1;
	}

	public static ItemStack[] combineLikeStacks(ItemStack[] stack)
	{
		StackInv inv = new StackInv(new ItemStack[stack.length]);
		addToInv(inv, stack);
		return inv.inventory;
	}

	public static ItemStack consumeItem(ItemStack stack, int amount)
	{
		if (stack != null && stack.stackSize <= amount) return null;
		for (int i = 0; i < amount; i++)
		{
			stack = consumeItem(stack);
		}
		return stack;
	}

	public static ItemStack consumeItem(ItemStack stack)
	{
		if (stack == null || stack.stackSize <= 0) return null;
		if (stack.stackSize == 1)
		{
			if (stack.getItem().hasContainerItem(stack)) return stack.getItem().getContainerItem(stack);
			else return null;
		}
		else
		{
			stack.splitStack(1);
			return stack;
		}
	}

	public static NBTTagCompound saveToNBT(ItemStack[] stacks)
	{
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < stacks.length; ++i)
		{
			if (stacks[i] != null)
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte) i);
				stacks[i].writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag("Items", items);
		return compound;
	}

	public static ItemStack[] loadFromNBT(int size, NBTTagCompound compound)
	{
		ItemStack[] inventory = new ItemStack[size];
		NBTTagList items = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < items.tagCount(); ++i)
		{
			NBTTagCompound item = items.getCompoundTagAt(i);
			byte slot = item.getByte("Slot");
			if (slot >= 0 && slot < inventory.length)
			{
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		return inventory;
	}

	public static ItemStack[] asItemStacks(Item... items)
	{
		if (items.length <= 0) return new ItemStack[0];
		ItemStack[] stacks = new ItemStack[items.length];
		for (int i = 0; i < stacks.length; i++)
		{
			stacks[i] = new ItemStack(items[i]);
		}
		return stacks;
	}

	public static boolean areStacksSameTypeCrafting(ItemStack stack1, ItemStack stack2)
	{
		return OreDictionary.itemMatches(stack1, stack2, false);
	}

	public static boolean hasItem(IInventory tile, Item item)
	{
		return indexOfItem(tile, item) >= 0;
	}

	public static boolean consumeItemFrom(IInventory tile, Item item)
	{
		if (hasItem(tile, item))
		{
			int i = indexOfItem(tile, item);
			tile.setInventorySlotContents(i, consumeItem(tile.getStackInSlot(i)));
			return true;
		}
		return false;
	}

	public static void clearInv(IInventory tile)
	{
		for (int i = 0; i < tile.getSizeInventory(); i++)
		{
			tile.setInventorySlotContents(i, null);
		}
	}

	public static int indexOfItem(IInventory tile, Item item)
	{
		for (int i = 0; i < tile.getSizeInventory(); i++)
		{
			if (tile.getStackInSlot(i) != null && tile.getStackInSlot(i).getItem() == item) return i;
		}
		return -1;
	}

	/*
	 * TESTING STUFF!
	 */

	public static int doInsertItemInv(IInventory inv, ItemStack item, ForgeDirection inventorySide)
	{
		ISidedInventory sidedInv = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
		ISlotIterator slots;

		if (sidedInv != null)
		{
			if (inventorySide == null)
			{
				inventorySide = ForgeDirection.UNKNOWN;
			}
			slots = new SidedSlotter(sidedInv.getAccessibleSlotsFromSide(inventorySide.ordinal()));
		}
		else
		{
			slots = new InvSlotter(inv.getSizeInventory());
		}

		int numInserted = 0;
		int numToInsert = item.stackSize;

		// PASS1: Try to add to an existing stack
		while (numToInsert > 0 && slots.hasNext())
		{
			int slot = slots.nextSlot();
			if (sidedInv == null || sidedInv.canInsertItem(slot, item, inventorySide.ordinal()))
			{
				ItemStack contents = inv.getStackInSlot(slot);
				if (contents != null && StackHelper.canStacksMerge(contents, item))
				{
					int freeSpace = Math.min(inv.getInventoryStackLimit(), contents.getMaxStackSize()) - contents.stackSize; // some inventories like using itemstacks with invalid stack sizes
					if (freeSpace > 0)
					{
						int noToInsert = Math.min(numToInsert, freeSpace);
						ItemStack toInsert = item.copy();
						toInsert.stackSize = contents.stackSize + noToInsert;
						// isItemValidForSlot() may check the stacksize, so give it the number the stack would have in the end.
						// If it does something funny, like "only even numbers", we are screwed.
						if (sidedInv != null || inv.isItemValidForSlot(slot, toInsert))
						{
							numInserted += noToInsert;
							numToInsert -= noToInsert;
							inv.setInventorySlotContents(slot, toInsert);
						}
					}
				}
			}
		}

		slots.reset();
		// PASS2: Try to insert into an empty slot
		while (numToInsert > 0 && slots.hasNext())
		{
			int slot = slots.nextSlot();
			if (sidedInv == null || sidedInv.canInsertItem(slot, item, inventorySide.ordinal()))
			{
				ItemStack contents = inv.getStackInSlot(slot);
				if (contents == null)
				{
					ItemStack toInsert = item.copy();
					toInsert.stackSize = MathHelp.min(numToInsert, inv.getInventoryStackLimit(), toInsert.getMaxStackSize()); // some inventories like using itemstacks with invalid stack sizes
					if (sidedInv != null || inv.isItemValidForSlot(slot, toInsert))
					{
						numInserted += toInsert.stackSize;
						numToInsert -= toInsert.stackSize;
						inv.setInventorySlotContents(slot, toInsert);
					}
				}
			}
		}

		if (numInserted > 0)
		{
			inv.markDirty();
		}
		return numInserted;

	}

	public static IInventory getInventory(IInventory inv)
	{
		if (inv instanceof TileEntityChest)
		{
			TileEntityChest chest = (TileEntityChest) inv;
			TileEntityChest neighbour = null;
			if (chest.adjacentChestXNeg != null)
			{
				neighbour = chest.adjacentChestXNeg;
			}
			else if (chest.adjacentChestXPos != null)
			{
				neighbour = chest.adjacentChestXPos;
			}
			else if (chest.adjacentChestZNeg != null)
			{
				neighbour = chest.adjacentChestZNeg;
			}
			else if (chest.adjacentChestZPos != null)
			{
				neighbour = chest.adjacentChestZPos;
			}
			if (neighbour != null) return new InventoryLargeChest("container.chestDouble", inv, neighbour);
			return inv;
		}
		return inv;
	}

	private static class InvSlotter implements ISlotIterator
	{
		final private int size;
		private int current;

		InvSlotter(int size)
		{
			this.size = size;
			current = 0;
		}

		@Override
		public int nextSlot()
		{
			return current++;
		}

		@Override
		public void reset()
		{
			current = 0;
		}

		@Override
		public boolean hasNext()
		{
			return current < size;
		}
	}

	private static class SidedSlotter implements ISlotIterator
	{
		final private int[] slots;
		private int current;

		SidedSlotter(int[] slots)
		{
			this.slots = slots;
			current = 0;
		}

		@Override
		public int nextSlot()
		{
			return slots[current++];
		}

		@Override
		public void reset()
		{
			current = 0;
		}

		@Override
		public boolean hasNext()
		{
			return slots != null && current < slots.length;
		}
	}

	private interface ISlotIterator
	{
		int nextSlot();

		boolean hasNext();

		void reset();
	}
}