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

public enum RunType {
    /**
     * The {@link Animation} loops until it's stopped.
     */
    LOOP,

    /**
     * The {@link Animation} plays once and stops.
     */
    FIRE_ONCE,

    /**
     * The {@link Animation} plays back and forth until it's stopped.
     */
    BOUNCE
}
