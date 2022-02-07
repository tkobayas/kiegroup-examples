This example is to demonstrate https://issues.redhat.com/browse/DROOLS-6053

How to use:

A) Run CreateKjarBinaryTest
 -> It will create basic-kjar-em-55-kfs-1.0.0.jar under output directory
 
B) Run LoadUrlResourceTest
 -> I will load the basic-kjar-em-55-kfs-1.0.0.jar. Thanks to DROOLS-6053, getKieBase() is faster than an usual kjar. (How much fast would depend on your rules and environment)
 