package ca.retrylife.marketcap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

import io.sentry.Sentry;

/**
 * Utils for turning Minecraft objects into hashes
 */
public class HashUtil {

    /**
     * Get a unique hash for a physical container
     * 
     * @param container {@link Container}
     * @return Hash string
     */
    public static String getContainerHash(Container container) {

        // Get needed components
        Location location = container.getLocation();
        World world = container.getWorld();
        Block block = container.getBlock();

        // Build data string to be hashed
        String data = String.format("Container<%s:%s:%s>", location.toString(), world.getName(),
                block.getType().toString());

        // Get hash function
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Sentry.captureException(e);
            e.printStackTrace();
            return data;
        }

        // Build hash
        digest.update(data.getBytes());
        return new String(digest.digest());

    }

}