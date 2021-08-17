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

public class Animator {
    @Getter(AccessLevel.PROTECTED) private final Set<Animation> animations = ConcurrentHashMap.newKeySet();

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
            if (animation.getRunType() == RunType.FIRE_ONCE && animation.getProgress() == 1) {
                animation.setRunning(false);
                animation.setFinished(true);
                if (animation.getOnFinish() != null)
                    animation.getOnFinish().run();
                animations.remove(animation);
            }

            if (animation.getRunType() == RunType.LOOP && animation.getProgress() == 1)
                animation.setProgress(0);

            if (animation.getRunType() == RunType.BOUNCE) {
                if (animation.getProgress() == 1)
                    animation.setForward(false);
                else if (animation.getProgress() == 0)
                    animation.setForward(true);
            }

            if (animation.isForward())
                animation.setProgress(Math.min(1, animation.getProgress() + partialTicks * animation.getMultiplier()));
            else
                animation.setProgress(Math.max(0, animation.getProgress() - partialTicks * animation.getMultiplier()));

            Ease selectedEase;
            if (animation.getBackwardEase() == null || animation.isForward())
                selectedEase = animation.getEase();
            else
                selectedEase = animation.getBackwardEase();

            animation.setValue(selectedEase.calc(animation.getProgress()));
            animation.setInverse(1 - animation.getValue());
        }
    }

    /**
     * Creates an {@link Animation} with the specified ease, {@link RunType#FIRE_ONCE} and {@link Speed#MEDIUM}
     */
    public Animation create(Ease ease) {
        return create(ease, null, RunType.FIRE_ONCE, 0, Speed.MEDIUM.getValue());
    }

    public Animation create(Ease ease, RunType type, Speed speed) {
        return create(ease, null, type, 0, speed.getValue());
    }

    public Animation create(Ease ease, RunType type, double speed) {
        return create(ease, null, type, 0, speed);
    }

    public Animation create(Ease ease, Ease backwardEase, RunType type, Speed speed) {
        return create(ease, backwardEase, type, 0, speed.getValue());
    }

    public Animation create(Ease ease, Ease backwardEase, RunType type, double speed) {
        return create(ease, backwardEase, type, 0, speed);
    }

    /**
     * Creates an {@link Animation} with the specified parameters
     *
     * @param ease type that's used when the animation is going forward
     * @param backwardEase ease type that's used when the animation is going backward
     * @param type run type
     * @param startValue starting point of the animation
     * @param speed speed of the animation
     */
    public Animation create(Ease ease, Ease backwardEase, RunType type, double startValue, double speed) {
        return new Animation(this, ease, backwardEase, type, startValue, speed);
    }
}
