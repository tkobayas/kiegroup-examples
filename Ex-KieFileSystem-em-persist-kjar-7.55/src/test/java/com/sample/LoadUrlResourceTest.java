package com.sample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.drools.core.io.impl.UrlResource;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

public class LoadUrlResourceTest {

    private KieServices kieService = KieServices.Factory.get();

    private KieContainer kieContainer;

    //    private final String URL = "file:///home/tkobayas/usr/git/tkobayas/kiegroup-examples/Ex-KieFileSystem-em-persist-kjar-7.55/output/basic-kjar-em-55-kfs-1.0.0.jar";
    private final String URL = new File("output/basic-kjar-em-55-kfs-1.0.0.jar").toURI().toString();

    @Test
    public void testRules() throws Exception {

//        System.setProperty("drools.projectClassLoader.enableStoreFirst", "false");

        System.out.println("URL : " + URL);

        initialiseNewKieContainer(URL, null);

    }

    private void initialiseNewKieContainer(String url, List<String> logCollector) throws Exception {
        boolean printLog = false;
        if (logCollector == null) {
            logCollector = new ArrayList<String>();
            printLog = true;
        }
        String log = "\n********** [ INITIALISATION LOG DETAILS ]**********";
        logCollector.add(log);
        Date startTime = new Date();
        Date totalStartTime = new Date();
        /**
        * Geting the Instance of KieRepository
        */
        KieRepository kr = kieService.getRepository();
        logCollector.add("\n1. Time taken to get the KieRepository from kieService.getRepository() =" + ((new Date()).getTime() - startTime.getTime()) + "ms");

        UrlResource urlResource = (UrlResource) kieService.getResources().newUrlResource(url);
        //            urlResource.setUsername(this.REPO_USER_ID);
        //            urlResource.setPassword(this.REPO_PASSWORD);
        //            urlResource.setBasicAuthentication("enabled");

        /**
         * Getting the Input Stream of in-memory downloaded KJAR.
         */
        InputStream is = null;
        try {
            startTime = new Date();

            is = urlResource.getInputStream();

            logCollector.add("\n2. Time taken to download the KJAR from URL [" + url + "]=" + ((new Date()).getTime() - startTime.getTime()) + "ms");
        } catch (IOException e) {
            throw e;
        }

        /**
         * Getting the Kie Module from KJAR Input Stream
         */
        startTime = new Date();
        KieModule kModule = kr.addKieModule(kieService.getResources().newInputStreamResource(is));
        logCollector.add("\n3. Time taken to get the Kie module from in-memory downloaded KJAR InputStream =" + ((new Date()).getTime() - startTime.getTime()) + "ms");
        ReleaseId releaseId = kModule.getReleaseId();

        /**
         * Creating the KIE Container from releaseId 
         */
        startTime = new Date();
        kieContainer = kieService.newKieContainer(releaseId);
        logCollector.add("\n4. Time taken to get the KIE Container from KieService.newKieContainer(" + releaseId + ") =" + ((new Date()).getTime() - startTime.getTime()) + "ms");

        System.out.println("****************************** getKieBase");
        startTime = new Date();
        KieBase kbase = kieContainer.getKieBase("defaultKieBase");
        logCollector.add("\n5. Time taken to get the KIE Base from KieContainer.getKieBase(defaultKieBase) = " + ((new Date()).getTime() - startTime.getTime()) + "ms");
        logCollector.add(" ----------------------------------------------------------------------------------------------------------------------------------------------");
        logCollector.add("\n6. Total Time taken to initalise the KIE Container From New KJAR =" + ((new Date()).getTime() - totalStartTime.getTime()) + "ms");
        logCollector.add("************************************************************************************************************************************************************");
        if (printLog) {
            for (String logString : logCollector) {
                System.out.println(logString);
            }
        }
        System.out.println("rules size = " + kbase.getKiePackage("com.sample").getRules().size());

        //        KieSession kSession = null;
        //        try {
        //            kSession = kbase.newKieSession();
        //            List<String> resultList = new ArrayList<>();
        //            kSession.setGlobal("resultList", resultList);
        //            Person john = new Person("john", 25);
        //            kSession.insert(john);
        //            kSession.fireAllRules();
        //            System.out.println(resultList);
        //        } finally {
        //            kSession.dispose();
        //        }
    }
}
