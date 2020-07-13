package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.google.common.primitives.UnsignedInts;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class InternetAddressToNumber extends AbstractFunction implements UserDefinedFunction {

    public InternetAddressToNumber() {
        super(1, InternetAddressVocabulary.toNumber.toString());
    }

    private InternetAddressToNumber(final InternetAddressToNumber internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(UnsignedInts.toLong(InetAddresses.coerceToInteger(InetAddresses.forString(ip)))));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new InternetAddressToNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.toNumber.toString();
    }

}
