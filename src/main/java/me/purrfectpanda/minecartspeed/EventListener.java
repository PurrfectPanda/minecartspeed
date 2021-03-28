package me.purrfectpanda.minecartspeed;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;

import java.util.HashSet;

public class EventListener implements Listener {

    private static BlockFace[] Sides = {BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event)
    {
        Minecart cart = (Minecart) event.getVehicle();
        if (cart == null || cart.getPassengers().isEmpty())
        {
            return;
        }

        Block cartBlock = cart.getWorld().getBlockAt(cart.getLocation());
        for (BlockFace face : Sides)
        {
            Block sideBlock = cartBlock.getRelative(face);
            BlockState blockState = sideBlock.getState();
            if (blockState instanceof Sign) {
                Sign sign = (Sign) blockState;
                if (sign.getLine(0).equalsIgnoreCase("speed")) {
                    try {
                        double speed = Double.parseDouble(sign.getLine(1));
                        speed /= 20;
                        cart.setMaxSpeed(speed);
                        cart.setVelocity(cart.getVelocity().normalize().multiply(speed));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
    }
}
