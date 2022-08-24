/*
 *     Blaze - Animation Library
 *     Copyright (C) 2021  SunStorm (aka. MrExplode)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
