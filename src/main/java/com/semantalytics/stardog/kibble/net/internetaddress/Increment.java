package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.google.common.net.InetAddresses.*;
import static com.stardog.stark.Values.literal;

public class Increment extends AbstractFunction implements UserDefinedFunction {

    public Increment() {
        super(1, InternetAddressVocabulary.isIp4MappedAddress.toString());
    }

    private Increment(final Increment internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();

        return ValueOrError.General.of(literal(InetAddresses.increment(InetAddresses.forString(ip)).toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new Increment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isIp4MappedAddress.toString();
    }

}
