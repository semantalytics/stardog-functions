package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Rotate extends AbstractFunction implements StringFunction {

    protected Rotate() {
        super(2, StringVocabulary.rotate.toString());
    }

    private Rotate(final Rotate rotate) {
        super(rotate);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertIntegerLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final int shift = Literal.intValue((Literal) values[1]);

            return ValueOrError.General.of(literal(rotate(string, shift)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Rotate copy() {
        return new Rotate(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.rotate.toString();
    }
}
