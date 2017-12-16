package test.appzui;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Vikas on 9/19/16.
 */

public class AppZuiTest {
    public static void main(String[] argv) throws Exception {

        // Paths
        String jMeterPath = "C:\\Users\\Vikas\\IdeaProjects\\jmeter 3.0";
        String appPath = "/project/appzui";

        //JmeterHome for the JMeter utilities
        File jmeterHome = new File(jMeterPath);
        String slash = System.getProperty("file.separator");
        if (jmeterHome.exists()) {

            //Properties File
            File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
            File userProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "myuser.properties");
            if (jmeterProperties.exists()) {
                //JMeter Engine
                StandardJMeterEngine jmeter = new StandardJMeterEngine();

                //JMeter initialization
                JMeterUtils.setJMeterHome(jmeterHome.getPath());
                JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
                JMeterUtils.loadProperties(userProperties.getPath());
                JMeterUtils.initLogging();
                JMeterUtils.initLocale();

                // JMeter Test Plan Jorphan HashTree
                HashTree testPlanTree = new HashTree();


                //User Defined Variables
                Arguments udv = new Arguments();
                udv.setEnabled(true);
                udv.setName("User Defined Arguments");
                udv.addArgument("APP_SERVER", "${__property(APP_SERVER,APP_SERVER)}");
                udv.addArgument("APP_CONTEXT", "${__property(APP_CONTEXT,APP_CONTEXT)}");
                udv.addArgument("PROTOCOL", "${__property(PROTOCOL,PROTOCOL)}");
                udv.addArgument("Thread", "${__p(Thread)}");
                udv.addArgument("RampUp", "${__P(RampUp)}");
                udv.addArgument("Loop", "${__P(Loop)}");
                udv.addArgument("PATH", "${__P(PATH)}");
                udv.setProperty(Arguments.GUI_CLASS, ArgumentsPanel.class.getName());

                // Test Plan
                TestPlan testPlan = new TestPlan("appZui");
                testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
                testPlan.setEnabled(true);
                testPlan.getComment();
                testPlan.setFunctionalMode(false);
                testPlan.setSerialized(false);
                testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());
                testPlan.setUserDefinedVariables(udv);
                testPlan.getTestPlanClasspath();


                //HTTP Header Manager
                HeaderManager httpHeaderManager = new HeaderManager();
                httpHeaderManager.setName("HTTP Header Manager");
                httpHeaderManager.setProperty(Header.NAME, "HTTP Header");
                Header h = new Header();
                h.setName("Content-Type");
                h.setValue("application/json");
                httpHeaderManager.add(h);
                httpHeaderManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
                httpHeaderManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

            /*
                //Add and Retrieve
                ThreadGroup add_Retrieve = new ThreadGroup();
                add_Retrieve.setEnabled(true);
                add_Retrieve.setProperty(TestElement.GUI_CLASS,ThreadGroupGui.class.getName());
                add_Retrieve.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
                LoopController lC_ar = new LoopController();
                lC_ar.setProperty(TestElement.GUI_CLASS,LoopControlPanel.class.getName());
                lC_ar.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                lC_ar.setContinueForever(false);
                lC_ar.setLoops(10);
                lC_ar.setEnabled(true);
                add_Retrieve.setNumThreads(15);
                add_Retrieve.setRampUp(8);
                add_Retrieve.getStartTime();
                add_Retrieve.getEndTime();
                add_Retrieve.setScheduler(false);

                GenericController controller_add = new GenericController();
                controller_add.setProperty(TestElement.GUI_CLASS, LogicControllerGui.class.getName());
                controller_add.setProperty(TestElement.TEST_CLASS,GenericController.class.getName());
                controller_add.setEnabled(true);

                    IfController controller_profile = new IfController();
                    controller_profile.setProperty(TestElement.GUI_CLASS, IfControllerPanel.class.getName());
                    controller_profile.setEnabled(true);
                    String cond = "(\"${profileName\" != \"<EOF>\") && (\"${userId}\".length>0)";
                    controller_profile.setCondition(cond);
                    controller_profile.setEvaluateAll(false);
                        HTTPSamplerProxy add_profile = new HTTPSamplerProxy();
                        add_profile.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
                        add_profile.setProperty(TestElement.TEST_CLASS,HTTPSamplerProxy.class.getName());
                        add_profile.setPostBodyRaw(true);
                        //add_profile.getArguments(Arguments, udv);
            */

                // Construct Test Plan Tree
                testPlanTree.add(testPlan);
                HashTree threadGroupHashTreeA = testPlanTree.add(httpHeaderManager);
                threadGroupHashTreeA.add(udv);

                /*
                HashTree threadGroupHashTreeB = threadGroupHashTreeA.add(udv,)
                //threadGroupHashTreeA.add(profiles );
                HashTree threadGroupHashTreeB = threadGroupHashTreeA.add(profiles);
                threadGroupHashTreeB.add(allProfileSampler);
                threadGroupHashTreeB.add(addProfileSampler,bsfSampler);
                threadGroupHashTreeB.add(getProfileSampler);
                threadGroupHashTreeB.add(delProfileSampler);
                */


                // save generated test plan to JMeter's .jmx file format
                SaveService.saveTree(testPlanTree, new FileOutputStream("C:\\Users\\Vikas\\IdeaProjects\\report\\jmeter_api_sample01.jmx"));

                //output Summariser
                Summariser summer = null;
                String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                if (summariserName.length() > 0) {
                    summer = new Summariser(summariserName);
                }

                // Store execution results .jtl file
                String reportFile = "C:\\Users\\Vikas\\IdeaProjects\\report\\report.jtl";
                String csvFile = "C:\\Users\\Vikas\\IdeaProjects\\report\\report.csv";
                ResultCollector logger = new ResultCollector(summer);
                logger.setFilename(reportFile);
                ResultCollector csvlogger = new ResultCollector(summer);
                csvlogger.setFilename(csvFile);
                testPlanTree.add(testPlanTree.getArray()[0], logger);
                testPlanTree.add(testPlanTree.getArray()[0], csvlogger);

                // Run Test Plan
                jmeter.configure(testPlanTree);
                jmeter.run();

                System.out.println("Test completed " + jmeterHome + slash + "report.jtl file for results");
                System.out.println("JMeter .jmx script Path " + jmeterHome + slash + "jmeter_api_sample01.jmx");
                System.exit(0);
            }
        }
        System.err.println("jmeterHome property N/A");
        System.exit(1);
    }
}

