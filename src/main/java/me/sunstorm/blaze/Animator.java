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

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds all the animations and handles the ticking of them.
 */
public class Animator {
    @Getter(AccessLevel.PROTECTED)
    private final Set<Animation> animations = ConcurrentHashMap.newKeySet();

    /**
     * Updates all active animations. Should be called every tick.
     *
     * @param partialTicks the time delta since the last render
     */
    public void update(float partialTicks) {
        for (Animation animation : animations) {
            if (!animation.isRunning()) {
                animations.remove(animation);
                continue;
            }

            animation.getRunType().tick(animation);

            if (animation.isGoingForward()) {
                animation.setProgress(Math.min(1, animation.getProgress() + partialTicks * animation.getMultiplier()));
            } else {
                animation.setProgress(Math.max(0, animation.getProgress() - partialTicks * animation.getMultiplier()));
            }

            Ease selectedEase = animation.getEase();
            if (animation.getBackwardEase() != null && !animation.isGoingForward()) {
                selectedEase = animation.getBackwardEase();
            }

            animation.setValue(selectedEase.calc(animation.getProgress()));
        }
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease the ease of the animation
     * @return the created animation
     */
    public Animation create(Ease ease) {
        return create(ease, RunType.FIRE_ONCE, Speed.MEDIUM);
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease  the ease of the animation
     * @param type  the type of this animation
     * @param speed the speed of this animation
     * @return the created animation
     */
    public Animation create(Ease ease, RunType type, double speed) {
        return create(ease, null, type, 0, speed);
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease         the ease of the animation
     * @param backwardEase the ease of this animation when the animation is going backwards
     * @param type         the type of this animation
     * @param speed        the speed of this animation
     * @return the created animation
     */
    public Animation create(Ease ease, Ease backwardEase, RunType type, double speed) {
        return create(ease, backwardEase, type, 0, speed);
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease         the ease of the animation
     * @param backwardEase the ease of this animation when the animation is going backwards
     * @param type         the type of this animation
     * @param startValue   the starting value of this animation
     * @param speed        the speed of this animation
     */
    public Animation create(Ease ease, Ease backwardEase, RunType type, double startValue, double speed) {
        return new Animation(this, ease, backwardEase, type, startValue, speed);
    }
}
