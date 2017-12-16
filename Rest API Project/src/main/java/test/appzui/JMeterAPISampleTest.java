package test.appzui;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.processor.PostProcessor;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.java.sampler.BSFSampler;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Vikas on 9/13/16.
 */
public class JMeterAPISampleTest {
    public static void main(String[] argv) throws Exception {

        //Set jmeter home
        File jmeterHome = new File("C:\\Users\\Vikas\\IdeaProjects\\jmeter 3.0");
        String slash = System.getProperty("file.separator");
        //String slash = System.getProperty("file.separator");

        if (jmeterHome.exists()) {
            File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
            if (jmeterProperties.exists()) {
                //JMeter Engine
                StandardJMeterEngine jmeter = new StandardJMeterEngine();

                //JMeter initialization
                JMeterUtils.setJMeterHome(jmeterHome.getPath());
                JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
                JMeterUtils.initLogging();
                JMeterUtils.initLocale();

                // JMeter Test Plan Jorphan HashTree
                HashTree testPlanTree = new HashTree();

                // Profile HTTP Sampler -
                HTTPSamplerProxy allProfileSampler = new HTTPSamplerProxy();
                allProfileSampler.setDomain("localhost");
                allProfileSampler.setPort(8080);
                allProfileSampler.setPath("/project/appzui/profiles/1");
                allProfileSampler.setMethod("GET");
                allProfileSampler.setName("All Profiles");
                allProfileSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                allProfileSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());


                // Add Profile HTTP Sampler -
                HTTPSamplerProxy addProfileSampler = new HTTPSamplerProxy();
                addProfileSampler.setDomain("localhost");
                addProfileSampler.setPort(8080);
                addProfileSampler.setPath("/project/appzui/profiles");
                addProfileSampler.setMethod("POST");
                addProfileSampler.setName("Add Profiles");
                addProfileSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                addProfileSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

                // Retrive Profile HTTP Sampler -
                HTTPSamplerProxy getProfileSampler = new HTTPSamplerProxy();
                getProfileSampler.setDomain("localhost");
                getProfileSampler.setPort(8080);
                getProfileSampler.setPath("/project/appzui/profiles/${pId}");
                getProfileSampler.setMethod("GET");
                getProfileSampler.setName("Retrive Profiles");
                getProfileSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                getProfileSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

                // Retrive Profile HTTP Sampler -
                HTTPSamplerProxy delProfileSampler = new HTTPSamplerProxy();
                delProfileSampler.setDomain("localhost");
                delProfileSampler.setPort(8080);
                delProfileSampler.setPath("/project/appzui/profiles/${pId}");
                delProfileSampler.setMethod("DELETE");
                delProfileSampler.setName("Delete Profiles");
                delProfileSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
                delProfileSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

                //BSF Post Processor
                BSFSampler bsfSampler = new BSFSampler();
                bsfSampler.setName("BSF PostProcessor");
                //String script = "var data = prev.getResponseDataAsString();\n" + "var obj = JSON.parse(data);\n" + "vars.put(&quot;pId&quot;,obj.id);";
                bsfSampler.setScript("var data = prev.getResponseDataAsString();\n" + "var obj = JSON.parse(data);\n" + "vars.put(&quot;pId&quot;,obj.id);");
                bsfSampler.setScriptLanguage("javascript");
                bsfSampler.setProperty(TestElement.TEST_CLASS, BSFSampler.class.getName());
                bsfSampler.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());


                //BSF Post Processor
                //PostProcessor bsfPostProcessor = new PostProcessor();



                // Loop Controller
                LoopController loopController = new LoopController();
                loopController.setLoops(1);
                loopController.setFirst(true);
                loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
                loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
                loopController.initialize();

                // Thread Group
                ThreadGroup profiles = new ThreadGroup();
                profiles.setName("Profiles");
                profiles.setNumThreads(1);
                profiles.setRampUp(1);
                profiles.setSamplerController(loopController);
                profiles.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
                profiles.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

                // Test Plan
                TestPlan testPlan = new TestPlan("Sample");
                testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
                testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
                testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

                //Header Manager
                HeaderManager headerManager = new HeaderManager();
                headerManager.setName("HTTP Header Manager");
                headerManager.setProperty(Header.NAME, "headers");
                Header h = new Header();
                h.setName("Content-Type");
                h.setValue("application/json");
                headerManager.add(h);
                headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
                headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());

                //HTTP Default
                //ConfigTestElement configTestElement = new ConfigTestElement();


                // Construct Test Plan from previously initialized elements
                //testPlanTree.add(testPlan);
                HashTree threadGroupHashTreeA = testPlanTree.add(testPlan,headerManager);
                //threadGroupHashTreeA.add(profiles );
                HashTree threadGroupHashTreeB = threadGroupHashTreeA.add(profiles);
                threadGroupHashTreeB.add(allProfileSampler);
                threadGroupHashTreeB.add(addProfileSampler,bsfSampler);
                threadGroupHashTreeB.add(getProfileSampler);
                threadGroupHashTreeB.add(delProfileSampler);
                // save generated test plan to JMeter's .jmx file format
                SaveService.saveTree(testPlanTree, new FileOutputStream("C:\\Users\\Vikas\\IdeaProjects\\report\\jmeter_api_sample_custom.jmx"));

                //Summarizer output
                Summariser summer = null;
                String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                if (summariserName.length() > 0) {
                    summer = new Summariser(summariserName);
                }


                //.jtl file and csv
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
                System.out.println("JMeter .jmx script Path " + jmeterHome + slash + "jmeter_api_sample_custom.jmx");
                System.exit(0);

            }
        }
        System.err.println("jmeterHome property N/A");
        System.exit(1);

    }
}