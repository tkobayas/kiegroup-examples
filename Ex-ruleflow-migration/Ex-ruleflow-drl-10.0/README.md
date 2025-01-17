
This example demonstrates Sample-ruleflow.drl which manages ruleFlowGroup as the same as sample-ruleflow.bpmn in Ex-ruleflow-bpmn-7.74.

If inserted Person's age > 18, group1, group2, group4 are triggered 

```
group1 : R1
group2 : R2
group4 : R4
Person [name=John, age=20]
```

If inserted Person's age <= 18, group1, group3, group4 are triggered

```
group1 : R1
group3 : R3
group4 : R4
Person [name=Paul, age=15]
```