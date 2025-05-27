package com.sample;

import java.util.Collection;

import org.drools.base.base.ValueResolver;
import org.drools.base.base.ValueType;
import org.drools.base.rule.accessor.FieldValue;
import org.drools.base.rule.accessor.ReadAccessor;
import org.drools.mvel.evaluators.BaseEvaluator;
import org.drools.mvel.evaluators.VariableRestriction;
import org.kie.api.runtime.rule.FactHandle;

public class SupersetOfEvaluator extends BaseEvaluator {

    public SupersetOfEvaluator() {
    }

    public SupersetOfEvaluator(final ValueType type, final boolean isNegated) {
        super(type, isNegated ? SupersetOfEvaluatorDefinition.NOT_SUPERSET_OF : SupersetOfEvaluatorDefinition.SUPERSET_OF);
    }

    @Override
    public boolean evaluate(final ValueResolver valueResolver, final ReadAccessor extractor, final FactHandle factHandle, final FieldValue value) {
        final Object objectValue = extractor.getValue(valueResolver, factHandle);
        return evaluateExpression((Collection) objectValue, (Collection) value.getValue());
    }

    @Override
    public boolean evaluate(final ValueResolver valueResolver, final ReadAccessor ira, final FactHandle leftOperandFact, final ReadAccessor ira1, final FactHandle rightOperandFact) {
        return evaluateExpression((Collection) leftOperandFact.getObject(), (Collection) rightOperandFact.getObject());
    }

    @Override
    public boolean evaluateCachedLeft(final ValueResolver valueResolver, final VariableRestriction.VariableContextEntry context, final FactHandle right) {
        final Object valRight = context.extractor.getValue(valueResolver, right.getObject());
        // a left input is a right operand. a right input is a left operand. so swapped.
        return evaluateExpression((Collection) valRight, (Collection) ((VariableRestriction.ObjectVariableContextEntry) context).left);
    }

    @Override
    public boolean evaluateCachedRight(final ValueResolver reteEvaluator, final VariableRestriction.VariableContextEntry context, final FactHandle left) {
        final Object varLeft = context.declaration.getExtractor().getValue(reteEvaluator, left);
        // a left input is a right operand. a right input is a left operand. so swapped.
        return evaluateExpression((Collection) ((VariableRestriction.ObjectVariableContextEntry) context).right, (Collection) varLeft);
    }

    private boolean evaluateExpression(final Collection leftOperandCollection, final Collection rightOperandCollection) {
        return getOperator().isNegated() ^ leftOperandCollection.containsAll(rightOperandCollection);
    }
}