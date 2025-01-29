# KieScanner Example

This example demonstrates how to use the KieScanner to automatically update the KieContainer version when an update is detected.

```
$ mvn clean test
...
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.sample.DroolsTest
Directory deleted successfully!
--- Building rules for com.sample:my-sample-a:1.0.0
--- Rules built ---
2025-01-29 12:30:50,227 [ForkJoinPool.commonPool-worker-1] INFO  org.drools.compiler.kie.builder.impl.KieContainerImpl Start creation of KieBase: defaultKieBase
2025-01-29 12:30:50,314 [ForkJoinPool.commonPool-worker-1] INFO  org.drools.compiler.kie.builder.impl.KieContainerImpl End creation of KieBase: defaultKieBase
Hello World 1
Hello World 1
2025-01-29 12:30:51,994 [Timer-0] INFO  org.drools.compiler.kie.builder.impl.InternalKieModuleProvider Creating KieModule for artifact com.sample:my-sample-a:1.0.0
2025-01-29 12:30:51,997 [Timer-0] INFO  org.drools.modelcompiler.CanonicalKieModuleProvider Artifact com.sample:my-sample-a:1.0.0 has executable model
2025-01-29 12:30:52,016 [Timer-0] INFO  org.kie.api.builder.KieScanner The following artifacts have been updated: {com.sample:my-sample-a:1.0.0=com.sample:my-sample-a:jar:1.0.0}
Hello World 1
--- Building rules for com.sample:my-sample-a:1.1.0
--- Rules built ---
Hello World 1
2025-01-29 12:30:53,652 [Timer-0] INFO  org.drools.compiler.kie.builder.impl.InternalKieModuleProvider Creating KieModule for artifact com.sample:my-sample-a:1.1.0
2025-01-29 12:30:53,656 [Timer-0] INFO  org.drools.modelcompiler.CanonicalKieModuleProvider Artifact com.sample:my-sample-a:1.1.0 has executable model
2025-01-29 12:30:53,691 [Timer-0] INFO  org.kie.api.builder.KieScanner The following artifacts have been updated: {com.sample:my-sample-a:1.0.0=com.sample:my-sample-a:jar:1.1.0}
Hello World 2
Hello World 1
Hello World 2
Hello World 1
Hello World 2
Hello World 1
```