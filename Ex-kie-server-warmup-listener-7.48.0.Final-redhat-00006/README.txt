## kie-server-warmup-listener

You can implement KieServerEventListener to do some "warm-up" task right after kjar deployment so that kie-server can make the first response quicker.

### How to use
Environment: RHDM/RHPAM 7.10.1

- Build my-kie-server-warmup-listener
$ mvn clean install
- Copy target/my-kie-server-warmup-listener-7.48.0.Final-redhat-00006.jar to ~/jboss/standalone/deployments/kie-server.war/WEB-INF/lib/
- Start RHDM/RHPAM
- Build Ex-kie-server-kjar-7.48.0.Final-redhat-00006
$ mvn clean install
- Call CreateContainerWithInternalFireOnlyWarmup in Ex-kie-server-client-7.48.0.Final-redhat-00006
- You will see logs like this
```
19:00:35,080 INFO  [org.kie.server.services.impl.KieServerImpl] (default task-2) Container kie-server-kjar-example_1.0.0 (for release id com.sample:kie-server-kjar-example:1.0.0) successfully started
19:00:35,081 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) start warmup : internal-fire-only
19:00:35,081 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2)   internalWarmupFireOnly
19:00:35,081 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) warmup : kieSessionName = myStatelessKsession
19:00:35,083 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) warmup : kieSessionName = myStatefulKsession
19:00:35,084 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) warmup : kieSessionName = myStatelessKsession2
19:00:35,085 INFO  [stdout] (default task-2) init
19:00:35,086 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) warmup : kieSessionName = myStatefulKsession2
19:00:35,087 INFO  [stdout] (default task-2) init
19:00:35,087 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) afterContainerStarted : elapsed time = 6ms

```
Then, next request (StatelessTestInteger.java, StatelessTestPerson) will be (slightly) quicker.

- Call DisposeContainer in Ex-kie-server-client-7.48.0.Final-redhat-00006 to dispose the container
- Call CreateContainerWithInternalFullWarmup in Ex-kie-server-client-7.48.0.Final-redhat-00006
- You will see logs like this
```
19:02:40,480 INFO  [org.kie.server.services.impl.KieServerImpl] (default task-2) Container kie-server-kjar-example_1.0.0 (for release id com.sample:kie-server-kjar-example:1.0.0) successfully started
19:02:40,482 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) start warmup : internal-full
19:02:40,482 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2)   internalWarmupFull
19:02:40,482 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2)   obj = com.sample.Person@470da4b7
19:02:40,543 INFO  [stdout] (default task-2) Ha ha ha
19:02:40,543 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2)   batchResult = org.drools.core.runtime.impl.ExecutionResultImpl@6bfcbcc1
19:02:40,548 INFO  [stdout] (default task-2) init
19:02:40,549 INFO  [stdout] (default task-2) Hello, George
19:02:40,549 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2)   batchResult = org.drools.core.runtime.impl.ExecutionResultImpl@2ed58d46
19:02:40,549 INFO  [org.example.kie.server.warmup.MyKieServerWarmupEventListener] (default task-2) afterContainerStarted : elapsed time = 67ms
```
Then, next request (StatelessTestInteger.java, StatelessTestPerson) will be much quicker.


----

my-kie-server-warmup-listener is an example implementation. MyKieServerWarmupEventListener is automatically enlisted in kie-server by `META-INF/services/org.kie.server.services.api.KieServerEventListener`

MyKieServerWarmupEventListener.internalWarmupFireOnly() executes FireAllRules only.

MyKieServerWarmupEventListener.internalWarmupFull() inserts expected facts and executes FireAllRules so that it gives better warm-up. Notice that MyKieServerWarmupEventListener.internalWarmupFull() should know that there are 2 StatelessKieSession "myStatelessKsession" "myStatelessKsession2" are defined in a kjar (expecting to test with Ex-kie-server-kjar-7.48.0.Final-redhat-00006).

MyKieServerWarmupEventListenerVariant.java contains other approaches (kie-server-client REST call). But not actually called. Just as a hint for implementation.
