/* 
 * Copyright (C) 2017 Petr Ryšavý <petr.rysavy@fel.cvut.cz>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.Sequence;
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.util.MathUtils;

/**
 * Decorator of sequence distance that considers both orientations of strings
 * and also possibilities that the sequences are from the complementary strands.
 * Assumption is that both distances are symmetric. If we have two reads, we
 * cannot be sure that they are oriented in the same way or thay are from the
 * same strand. Therefore this class calculates both directions and uses the
 * smaller one.
 *
 * @author Petr Ryšavý
 */
public class UnorientedComplementDistance extends DecoratedDistance<Sequence> {

    public UnorientedComplementDistance(DistanceCalculator<Sequence, ? extends Number> innerDistance) {
        super(innerDistance);

        if (!innerDistance.isSymmetric())
            throw new IllegalArgumentException("Only symmetric distances are supported.");
    }

    @Override
    public Double getDistance(Sequence a, Sequence b) {
        return MathUtils.min(super.getDistance(a, b),
                super.getDistance(a, b.reverse()),
                super.getDistance(a, b.complement()),
                super.getDistance(a, b.reverseComplement())
        );
    }
}
