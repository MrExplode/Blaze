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

import static lombok.AccessLevel.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an animation
 */
@Getter
public class Animation {
    @Getter(NONE) private final Animator animator;
    private final Ease ease;
    @Nullable private final Ease backwardEase;
    private final RunType runType;
    private final double multiplier;

    @Nullable
    private Runnable onFinish;

    //the animation value
    @Setter(PROTECTED) private double value;
    //inverse of the animation value. might come handy when making centre out scaling animations
    @Setter(PROTECTED) private double inverse;
    @Setter(PROTECTED) private double progress;
    @Setter(PROTECTED) @Getter(PROTECTED) private boolean forward = true;
    @Setter(PROTECTED) private boolean running = false;
    @Setter(PROTECTED) private boolean finished = false;

    protected Animation(Animator animator, Ease ease, @Nullable Ease backwardEase, RunType runType, double startValue, double multiplier) {
        if (startValue < 0 || startValue > 1) throw new IllegalArgumentException("Invalid start value " + startValue);
        this.animator = animator;
        this.ease = ease;
        this.backwardEase = backwardEase;
        this.runType = runType;
        this.progress = startValue;
        this.value = startValue;
        this.multiplier = multiplier;
    }

    public Animation onFinish(Runnable r) {
        this.onFinish = r;
        return this;
    }

    public void start() {
        animator.getAnimations().add(this);
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void reset() {
        progress = 0;
    }
}
