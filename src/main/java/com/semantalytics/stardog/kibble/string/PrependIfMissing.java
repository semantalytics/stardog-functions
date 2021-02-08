package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static com.stardog.stark.Values.literal;

public final class PrependIfMissing extends AbstractFunction implements StringFunction {

    protected PrependIfMissing() {
        super(Range.atLeast(2), StringVocabulary.prependIfMissing.toString());
    }

    private PrependIfMissing(final PrependIfMissing prependIfMissing) {
        super(prependIfMissing);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

      for(final Value value : values) {
          if(!assertStringLiteral(value)) {
              return ValueOrError.Error;
          }
      }

      final String string = ((Literal)values[0]).label();
      final String prefix = ((Literal)values[1]).label();
      final String[] prefixes = Arrays.stream(values).skip(2).map(v -> (Literal)v).map(Literal::label).toArray(String[]::new);

      return ValueOrError.General.of(literal(StringUtils.prependIfMissing(string, prefix, prefixes)));
    }

    @Override
    public PrependIfMissing copy() {
        return new PrependIfMissing(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.prependIfMissing.toString();
    }
}
