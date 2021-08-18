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

public enum RunType {
    /**
     * The {@link Animation animation} plays once and stops.
     */
    FIRE_ONCE {
        @Override
        public void tick(@NotNull Animation animation) {
            if (animation.getProgress() == 1) {
                animation.setRunning(false);

                if (animation.getOnFinish() != null) {
                    animation.getOnFinish().run();
                }
            }
        }
    },

    /**
     * The {@link Animation animation} loops until it's stopped.
     */
    LOOP {
        @Override
        public void tick(@NotNull Animation animation) {
            if (animation.getProgress() == 1) {
                animation.reset();
            }
        }
    },

    /**
     * The {@link Animation animation} plays back and forth until it's stopped.
     */
    BOUNCE {
        @Override
        public void tick(@NotNull Animation animation) {
            if (animation.getProgress() == 1) {
                animation.setDirection(Direction.BACKWARD);
            } else if (animation.getProgress() == 0) {
                animation.setDirection(Direction.FORWARD);
            }
        }
    };

    /**
     * Ticks the animation.
     *
     * @param animation the animation
     */
    public abstract void tick(@NotNull Animation animation);
}
