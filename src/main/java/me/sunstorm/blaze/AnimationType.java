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
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an animation type.
 */
public interface AnimationType {
    /**
     * Ticks the animation.
     *
     * @param animation the animation
     */
    boolean tick(@NotNull Animation animation);

    /**
     * Returns the new progress for the given animation.
     *
     * @param animation the animation
     * @return the new progress
     */
    default double getProgress(@NotNull Animation animation, float partialTicks) {
        return Math.min(1, animation.progress() + partialTicks * animation.speed());
    }

    /**
     * Returns the new value for the given animation.
     *
     * @param animation the animation
     * @return the new value
     */
    default double getValue(@NotNull Animation animation) {
        return animation.ease().apply(animation.progress());
    }

    /**
     * Returns an animation type what plays back and forth until it's manually stopped.
     *
     * @return the bouncing animation type instance
     */
    @NotNull
    static AnimationType bouncing() {
        return Bouncing.INSTANCE;
    }

    /**
     * Returns an animation type what plays back and forth until it's manually stopped.
     *
     * @return the bouncing animation type instance
     */
    @NotNull
    static AnimationType bouncing(@NotNull Ease backwardEase) {
        Bouncing bounce = new Bouncing();
        bounce.backwardEase = backwardEase;
        return bounce;
    }

    /**
     * Returns a new animation type what plays once and stops.
     *
     * @return a new once animation type instance
     */
    @NotNull
    static AnimationType once() {
        return Once.INSTANCE;
    }

    /**
     * Returns a new animation type what plays once and stops.
     *
     * @param runnable the task what will run after the animation finished
     * @return a new once instance
     */
    @NotNull
    static AnimationType once(@Nullable Runnable runnable) {
        Once once = new Once();
        once.finishTask = runnable;
        return once;
    }

    /**
     * Returns an animation type what loops until it's manually stopped.
     *
     * @return the loop animation type instance
     */
    @NotNull
    static AnimationType loop() {
        return Loop.INSTANCE;
    }

    /**
     * The {@link Animation animation} plays back and forth until it's stopped.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class Bouncing implements AnimationType {
        private boolean goingForward = true;
        private Ease backwardEase = null;
        private static final Bouncing INSTANCE = new Bouncing();

        @Override
        public boolean tick(Animation progressible) {
            if (progressible.progress() == 1) {
                goingForward = false;
            } else if (progressible.progress() == 0) {
                goingForward = true;
            }

            return false;
        }

        @Override
        public double getProgress(@NotNull Animation animation, float partialTicks) {
            if (goingForward) {
                return AnimationType.super.getProgress(animation, partialTicks);
            } else {
                return Math.max(0, animation.progress() - partialTicks * animation.speed());
            }
        }

        @Override
        public double getValue(@NotNull Animation animation) {
            Ease selectedEase = animation.ease();

            if (backwardEase != null && !goingForward) {
                selectedEase = backwardEase;
            }

            return selectedEase.apply(animation.progress());
        }
    }

    /**
     * The {@link Animation animation} plays once and stops.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class Once implements AnimationType {
        private static final Once INSTANCE = new Once();
        private Runnable finishTask;

        @Override
        public boolean tick(Animation progressible) {
            if (progressible.progress() == 1) {
                if (finishTask != null) {
                    finishTask.run();
                }
                return true;
            }
            return false;
        }
    }

    /**
     * The {@link Animation animation} loops until it's stopped.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class Loop implements AnimationType {
        private static final Loop INSTANCE = new Loop();

        @Override
        public boolean tick(Animation progressible) {
            if (progressible.progress() == 1) {
                progressible.progress(0);
            }

            return false;
        }
    }
}
