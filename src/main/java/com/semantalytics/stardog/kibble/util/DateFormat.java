package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormat extends AbstractFunction implements UserDefinedFunction {

    public DateFormat() {
        super(2, UtilVocabulary.dateTimeFormat.stringValue());
    }

    private DateFormat(final DateFormat dateTimeFormat) {
        super(dateTimeFormat);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String time = Literal.str((Literal)values[0]);
            final String pattern = Literal.str((Literal)values[1]);

            DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
            return ValueOrError.Calendar.of(LocalDate.parse(time, format));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public DateFormat copy() {
        return new DateFormat(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.fromSpokenTime.name();
    }
}
