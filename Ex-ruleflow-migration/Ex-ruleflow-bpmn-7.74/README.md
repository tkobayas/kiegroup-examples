This example demonstrates drl + bpmn ruleflow using drools + jbpm 7.74.0.

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