package com.framework.ux.utils.chapter10;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * @author Carl Cocchiaro
 *
 * ExtentReports HTML Reporter Class
 *
 */
public class ExtentTestNGIReporterListener implements IReporter {
    private String bitmapDir = Global_VARS.REPORT_PATH;
    private String seleniumRev = "3.7.1", docTitle = "SELENIUM FRAMEWORK DESIGN IN DATA-DRIVEN TESTING";
    private ExtentReports extent;

    /**
     * generateReport method
     *
     * @param xmlSuites
     * @param suites
     * @param outputDirectory
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites,
                               List<ISuite> suites,
                               String outputDirectory) {

        for (ISuite suite : suites) {
            init(suite);
            Map<String, ISuiteResult> results = suite.getResults();

            for ( ISuiteResult result : results.values() ) {
                try {
                    processTestResults(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        extent.flush();
    }

    /**
     * init method to customize report
     *
     * @param suite
     */
    private void init(ISuite suite) {
        File directory = new File(Global_VARS.REPORT_PATH);

        if ( !directory.exists() ) {
            directory.mkdirs();
        }

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(Global_VARS.REPORT_PATH + suite.getName() + ".html");

        // report attributes
        htmlReporter.config().setDocumentTitle(docTitle);
        htmlReporter.config().setReportName(suite.getName().replace("_", " "));
        htmlReporter.config().setChartVisibilityOnOpen(false);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("UTF-8");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setTimeStampFormat("MMM-dd-yyyy HH:mm:ss a");
        htmlReporter.loadXMLConfig(new File(Global_VARS.REPORT_CONFIG_FILE));

        extent = new ExtentReports();

        // report system info
        extent.setSystemInfo("Browser", Global_VARS.DEF_BROWSER);
        extent.setSystemInfo("Environment", Global_VARS.DEF_ENVIRONMENT);
        extent.setSystemInfo("Platform", Global_VARS.DEF_PLATFORM);
        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Selenium Version", seleniumRev);

        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);
    }

    /**
     * processTestResults method to create report
     *
     * @param r
     * @throws Exception
     */
    private void processTestResults(ISuiteResult r) throws Exception {
        ExtentTest test = null;
        Status status = null;
        String message = null;

        // gather results
        Set<ITestResult> passed = r.getTestContext().getPassedTests().getAllResults();
        Set<ITestResult> failed = r.getTestContext().getFailedTests().getAllResults();
        Set<ITestResult> skipped = r.getTestContext().getSkippedTests().getAllResults();
        Set<ITestResult> configs = r.getTestContext().getFailedConfigurations().getAllResults();
        Set<ITestResult> tests = new HashSet<ITestResult>();

        tests.addAll(passed);
        tests.addAll(skipped);
        tests.addAll(failed);

        // process results
        if ( tests.size() > 0 ) {
            // sort results by the Date field
            List<ITestResult> resultList = new LinkedList<ITestResult>(tests);

            class ResultComparator implements Comparator<ITestResult> {
                public int compare(ITestResult r1, ITestResult r2) {
                    return getTime(r1.getStartMillis()).compareTo(getTime(r2.getStartMillis()));
                }
            }

            Collections.sort(resultList , new ResultComparator ());

            for ( ITestResult result : resultList ) {
                if ( getTestParams(result).isEmpty() ) {
                    test = extent.createTest(result.getMethod().getMethodName());
                }

                else {
                    if ( getTestParams(result).split(",")[0].contains(result.getMethod().getMethodName()) ) {
                        test = extent.createTest(getTestParams(result).split(",")[0], getTestParams(result).split(",")[1]);
                    }

                    else {
                        test = extent.createTest(result.getMethod().getMethodName(), getTestParams(result).split(",")[1]);
                    }
                }

                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));

                for ( String group : result.getMethod().getGroups() ) {
                    if ( !group.isEmpty() ) {
                        test.assignCategory(group);
                    }

                    else {
                        int size = result.getMethod().getTestClass().toString().split("\\.").length;
                        String testName = result.getMethod().getRealClass().getName().toString().split("\\.")[size-1];
                        test.assignCategory(testName);
                    }
                }

                // get status
                switch(result.getStatus() ) {
                    case 1:
                        status = Status.PASS;
                        break;
                    case 2:
                        status = Status.FAIL;
                        break;
                    case 3:
                        status = Status.SKIP;
                        break;
                    default:
                        status = Status.INFO;
                        break;
                }

                // set colors of status
                if ( status.equals(Status.PASS) ) {
                    message = "<font color=#00af00>" + status.toString().toUpperCase() + "</font>";
                }

                else if ( status.equals(Status.FAIL) ) {
                    message = "<font color=#F7464A>" + status.toString().toUpperCase() + "</font>";
                }

                else if ( status.equals(Status.SKIP) ) {
                    message = "<font color=#2196F3>" + status.toString().toUpperCase() + "</font>";
                }

                else {
                    message = "<font color=black>" + status.toString().toUpperCase() + "</font>";
                }

                // log status in report
                test.log(status, message);

                if ( !getTestParams(result).isEmpty() ) {
                    test.log(Status.INFO, "TEST DATA = [" + getTestParams(result) + "]");
                }

                if ( result.getThrowable() != null ) {
                    test.log(Status.INFO, "EXCEPTION = [" + result.getThrowable().getMessage() + "]");

                    if ( !getTestParams(result).isEmpty() ) {
                        // must capture screenshot to include in report
                        if ( result.getAttribute("testBitmap") != null) {
                            test.log(Status.INFO, "SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(bitmapDir + result.getAttribute("testBitmap")).build());
                        }

                        test.log(Status.INFO, "STACKTRACE" + getStrackTrace(result));
                    }
                }
            }
        }
    }

    /**
     * getTime method to retrieve current date/time
     *
     * @param millis
     * @return Date
     */
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        
        return calendar.getTime();
    }

    /**
     * getTestParams method to retieve test parameters
     *
     * @param tr
     * @return String
     * @throws Exception
     */
    private String getTestParams(ITestResult tr) throws Exception {
        TestNG_ConsoleRunner runner = new TestNG_ConsoleRunner();

        return runner.getTestParams(tr);
    }

    /**
     * getStrackTrace method to retrieve stack trace
     *
     * @param result
     * @return String
     */
    private String getStrackTrace(ITestResult result) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        result.getThrowable().printStackTrace(printWriter);

        return "<br/>\n" + writer.toString().replace(System.lineSeparator(), "<br/>\n");
    }

}