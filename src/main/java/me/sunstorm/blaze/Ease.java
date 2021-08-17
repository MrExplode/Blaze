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

import lombok.AllArgsConstructor;

import java.util.function.Function;

import static java.lang.Math.PI;
import static net.jafama.FastMath.sin;
import static net.jafama.FastMath.cos;
import static net.jafama.FastMath.pow;

@AllArgsConstructor
public enum Ease {
    LINEAR(x -> x),

    SINE_IN(x -> 1 - cos((x * PI) / 2)),
    SINE_OUT(x -> sin((x * PI) / 2)),
    SINE_IN_OUT(x -> -(cos(PI * x) - 1) / 2),

    QUAD_IN(x -> x * x),
    QUAD_OUT(x -> 1 - (1 - x) * (1 - x)),
    QUAD_IN_OUT(x -> x < 0.5 ? 2 * x * x : 1 - pow(-2 * x + 2, 2) / 2),

    CUBIC_IN(x -> x * x * x),
    CUBIC_OUT(x -> 1 - pow(1 - x, 3)),
    CUBIC_IN_OUT(x -> x < 0.5 ? 4 * x * x * x : 1 - pow(-2 * x + 2, 3) / 2),

    BACK_IN(x -> {
        float c1 = 1.70158f;
        float c3 = c1 + 1;

        return c3 * x * x * x - c1 * x * x;
    }),
    BACK_OUT(x -> {
        float c1 = 1.70158f;
        float c3 = c1 + 1;
        return 1 + c3 * pow(x - 1, 3) + c1 * pow(x - 1, 2);
    }),
    BACK_IN_OUT(x -> {
        float c1 = 1.70158f;
        float c2 = c1 * 1.525f;
        return x < 0.5 ? (pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : (pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
    }),

    ELASTIC_IN(x -> {
        double c4 = (2 * Math.PI) / 3;
        return x == 0 ? 0 : x == 1 ? 1 : -pow(2, 10 * x - 10) * sin((x * 10 - 10.75) * c4);
    }),
    ELASTIC_OUT(x -> {
        double c4 = (2 * Math.PI) / 3;
        return x == 0 ? 0 : x == 1 ? 1 : pow(2, -10 * x) * sin((x * 10 - 0.75) * c4) + 1;
    }),
    ELASTIC_IN_OUT(x -> {
        double c5 = (2 * Math.PI) / 4.5;
        return x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? -(pow(2, 20 * x - 10) * sin((20 * x - 11.125) * c5)) / 2 : (pow(2, -20 * x + 10) * sin((20 * x - 11.125) * c5)) / 2 + 1;
    }),


    EXP_IN(x -> x == 0 ? 0 : pow(2, 10 * x - 10)),
    EXP_OUT(x -> x == 1 ? 1 : 1 - pow(2, -10 * x)),
    EXP_IN_OUT(x -> x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? pow(2, 20 * x - 10) / 2 : (2 - pow(2, -20 * x + 10)) / 2),

    BOUNCE_OUT(x -> {
        float n1 = 7.5625f;
        float d1 = 2.75f;
        if (x < 1 / d1) {
            return n1 * x * x;
        } else if (x < 2 / d1) {
            return n1 * (x -= 1.5 / d1) * x + 0.75;
        } else if (x < 2.5 / d1) {
            return n1 * (x -= 2.25 / d1) * x + 0.9375;
        } else {
            return n1 * (x -= 2.625 / d1) * x + 0.984375;
        }
    }),
    BOUNCE_IN(x -> 1 - BOUNCE_OUT.calc(1 - x)),
    BOUNCE_IN_OUT(x -> x < 0.5
            ? (1 - BOUNCE_OUT.calc(1 - 2 * x)) / 2
            : (1 + BOUNCE_OUT.calc(2 * x - 1)) / 2);

    private final Function<Double, Double> expression;

    public double calc(double value) {
        return expression.apply(value);
    }
}
