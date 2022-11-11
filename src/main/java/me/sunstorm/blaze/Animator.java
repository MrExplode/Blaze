package me.sunstorm.blaze;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds all the animations and handles the ticking of them.
 */
public class Animator {
    private final Set<Animation> animations = ConcurrentHashMap.newKeySet();

    /**
     * Updates all active animations. Should be called every tick.
     *
     * @param partialTicks the time delta since the last render
     */
    public void update(float partialTicks) {
        for (Animation animation : animations) {
            if (animation.paused()) {
                continue;
            }

            if (animation.type().tick(animation)) {
                animations.remove(animation);
                continue;
            }

            double progress = animation.type().getProgress(animation, partialTicks);
            animation.progress(progress);

            double value = animation.type().getValue(animation);
            animation.value(value);
        }
    }

    /**
     * Removes the given animation from the animation queue.
     *
     * @param animation the animation
     * @return true if the removal was successful, false otherwise
     */
    public boolean destroy(@NotNull Animation animation) {
        return animations.remove(animation);
    }

    /**
     * Inserts the given animation to the animation queue.
     *
     * @param animation the animation
     * @return true if the insertion was successful, false otherwise
     */
    public boolean start(@NotNull Animation animation) {
        return animations.add(animation);
    }
}
