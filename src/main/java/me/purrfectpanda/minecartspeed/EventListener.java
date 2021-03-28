package me.purrfectpanda.minecartspeed;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;

public class EventListener implements Listener {

    private static BlockFace[] Sides = {BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};

    private Minecartspeed Plugin;

    public EventListener(Minecartspeed plugin)
    {
        Plugin = plugin;
    }

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
                        String line2 = sign.getLine(1);
                        double speed = 0;
                        if (line2.equalsIgnoreCase("snail"))
                        {
                            speed = 0.5;
                        }
                        else if (line2.equalsIgnoreCase("zoom"))
                        {
                            speed = 10;
                        }
                        else if (line2.equalsIgnoreCase("trappy"))
                        {
                            speed = 25;
                        }
                        else
                        {
                            speed = Double.parseDouble(sign.getLine(1));
                        }

                        // Minecraft operates with speed per tick, our speed is per second,
                        // and normal server frame rate is 20 ticks per second
                        speed /= 20;
                        cart.setMaxSpeed(speed);
                        cart.setVelocity(cart.getVelocity().normalize().multiply(speed));
                    } catch (NumberFormatException e) {
                        Plugin.getLogger().warning("Sign at " +
                                sideBlock.getLocation().toString() +
                                " has illegal speed: " + sign.getLine(1));
                    }
                }
            }
        }
    }
}
