package com.sample;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.drools.base.base.ValueType;
import org.drools.base.rule.accessor.Evaluator;
import org.drools.compiler.rule.builder.EvaluatorDefinition;
import org.drools.drl.parser.impl.Operator;

public class SupersetOfEvaluatorDefinition implements EvaluatorDefinition {

    public static final Operator SUPERSET_OF = Operator.addOperatorToRegistry("supersetOf", false);
    public static final Operator NOT_SUPERSET_OF = Operator.addOperatorToRegistry("supersetOf", true);
    private static final String[] SUPPORTED_IDS = {SUPERSET_OF.getOperatorString()};

    private Evaluator[] evaluator;

    public SupersetOfEvaluatorDefinition() {
    }

    @Override
    public String[] getEvaluatorIds() {
        return SupersetOfEvaluatorDefinition.SUPPORTED_IDS;
    }

    @Override
    public boolean isNegatable() {
        return true;
    }

    @Override
    public Evaluator getEvaluator(final ValueType type, final String operatorId, final boolean isNegated, final String parameterText, final Target leftTarget, final Target rightTarget) {
        return new SupersetOfEvaluator(type, isNegated);
    }

    @Override
    public Evaluator getEvaluator(final ValueType type, final String operatorId, final boolean isNegated, final String parameterText) {
        return getEvaluator(type, operatorId, isNegated, parameterText, Target.FACT, Target.FACT);
    }

    @Override
    public Evaluator getEvaluator(final ValueType type, final Operator operator, final String parameterText) {
        return this.getEvaluator(type, operator.getOperatorString(), operator.isNegated(), parameterText);
    }

    @Override
    public Evaluator getEvaluator(final ValueType type, final Operator operator) {
        return this.getEvaluator(type, operator.getOperatorString(), operator.isNegated(), null);
    }

    @Override
    public boolean supportsType(final ValueType vt) {
        return true;
    }

    @Override
    public Target getTarget() {
        return Target.FACT;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(evaluator);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        evaluator = (Evaluator[]) in.readObject();
    }
}
