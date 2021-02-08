package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.exception.NotComparableException;
import com.github.tkurz.media.ontology.function.SpatialFunction;
import com.github.tkurz.media.ontology.function.TemporalFunction;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.semantalytics.stardog.kibble.multimediafragments.utils.SpatialEntities;
import com.semantalytics.stardog.kibble.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

public class Overlaps extends AbstractFunction implements UserDefinedFunction {

    public Overlaps() {
        super(2, Constants.NAMESPACE + "overlaps");
    }

    public Overlaps(final Overlaps overlaps) {
        super(overlaps);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if (Entities.haveComparable(values)) {
            try {
                final Optional<TemporalEntity> temporalEntity1 = TemporalEntities.of(values[0]);
                final Optional<TemporalEntity> temporalEntity2 = TemporalEntities.of(values[1]);
                final Optional<SpatialEntity> spatialEntity1 = SpatialEntities.of(values[0]);
                final Optional<SpatialEntity> spatialEntity2 = SpatialEntities.of(values[1]);

                if(temporalEntity1.isPresent() && temporalEntity2.isPresent() && spatialEntity1.isPresent() && spatialEntity2.isPresent()) {
                    return ValueOrError.Boolean.of(TemporalFunction.overlaps(temporalEntity1.get(), temporalEntity2.get()) && SpatialFunction.TopologicalFunction.overlaps(spatialEntity1.get(), spatialEntity2.get()));
                } else {
                    return ValueOrError.Error;
                }
            } catch (ParseException | MediaFragmentURISyntaxException | NotComparableException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Overlaps copy() {
        return new Overlaps(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}