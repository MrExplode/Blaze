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
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an animation.
 */
@Getter
@Setter(AccessLevel.PROTECTED)
public class Animation {
    @Getter(AccessLevel.NONE)
    private final Animator animator;

    private final Ease ease;
    private final Ease backwardEase;
    private final RunType runType;
    private final double multiplier;

    private Runnable onFinish;

    private double value; //the animation value (0-1)
    private double progress; // the progress (0-1)

    private Direction direction = Direction.FORWARD;
    private boolean running = false;

    protected Animation(@NotNull Animator animator, @NotNull Ease ease, @Nullable Ease backwardEase, @NotNull RunType runType, double startValue, double multiplier) {
        if (startValue < 0 || startValue > 1) {
            throw new IllegalArgumentException("Invalid start value " + startValue);
        }

        this.animator = animator;
        this.ease = ease;
        this.backwardEase = backwardEase;
        this.runType = runType;
        this.progress = startValue;
        this.value = startValue;
        this.multiplier = multiplier;
    }

    /**
     * TODO
     */
    public Animation onFinish(Runnable r) {
        if (runType != RunType.FIRE_ONCE) {
            throw new IllegalStateException("can't set onFinish while the animation's runType is not RunType.FIRE_ONCE");
        }
        this.onFinish = r;
        return this;
    }

    /**
     * Returns the remaining value of this animation.
     *
     * @return the remaining value
     */
    public double getRemaining() {
        return 1 - value;
    }

    /**
     * Returns if the animation is going forward.
     *
     * @return true if the animation is going forward, false otherwise
     */
    public boolean isGoingForward() {
        return direction == Direction.FORWARD;
    }

    /**
     * Starts the animation.
     */
    public void start() {
        animator.getAnimations().add(this);
        running = true;
    }

    /**
     * Stops the animation.
     *
     * @implNote this doesn't reset the progress of this animation.
     */
    public void stop() {
        running = false;
    }

    /**
     * Resets the progress of this animation.
     */
    public void reset() {
        progress = 0;
    }
}
