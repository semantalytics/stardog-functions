package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.exception.NotComparableException;
import com.github.tkurz.media.ontology.function.TemporalFunction;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.semantalytics.stardog.kibble.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class TemporalEquals extends AbstractFunction implements UserDefinedFunction {

    public TemporalEquals() {
        super(2, Constants.NAMESPACE + "temporalEquals");
    }

    public TemporalEquals(final TemporalEquals temporalEquals) {
        super(temporalEquals);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(Entities.haveComparable(values)) {
            try {
                final Optional<TemporalEntity> entity1 = TemporalEntities.of(values[0]);
                final Optional<TemporalEntity> entity2 = TemporalEntities.of(values[1]);

                if(entity1.isPresent() && entity2.isPresent()) {
                    return ValueOrError.General.of(literal(TemporalFunction.equals(entity1.get(), entity2.get())));
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
    public TemporalEquals copy() {
        return new TemporalEquals(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}