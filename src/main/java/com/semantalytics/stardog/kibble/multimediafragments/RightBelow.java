package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.function.SpatialFunction;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.semantalytics.stardog.kibble.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

import java.util.Optional;

public class RightBelow extends AbstractFunction implements UserDefinedFunction {

    public RightBelow() {
        super(2, Constants.NAMESPACE + "rightBelow");
    }

    public RightBelow(final RightBelow rightBelow) {
        super(rightBelow);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(Entities.haveComparable(values)) {
            try {
                final Optional<SpatialEntity> entity1 = SpatialEntities.of(values[0]);
                final Optional<SpatialEntity> entity2 = SpatialEntities.of(values[1]);

                if(entity1.isPresent() && entity2.isPresent()) {
                    return ValueOrError.Boolean.of(SpatialFunction.DirectionalFunction.rightBelow(entity1.get(), entity2.get()));
                } else {
                    return ValueOrError.Error;
                }
            } catch (ParseException | MediaFragmentURISyntaxException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public RightBelow copy() {
        return new RightBelow(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}