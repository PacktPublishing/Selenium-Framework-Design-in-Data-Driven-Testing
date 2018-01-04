package com.framework.ux.utils.chapter10;

import java.io.File;

/**
 * @author Carl Cocchiaro
 *
 * Global Variable Utility Class
 *
 */
public class Global_VARS {
    // browser defaults
    public static final String BROWSER = "chrome";
    public static final String PLATFORM = "Windows 7";
    public static final String ENVIRONMENT = "local";
    public static String DEF_BROWSER = null;
    public static String DEF_PLATFORM = null;
    public static String DEF_ENVIRONMENT = null;

    // suite folder defaults
    public static String SUITE_NAME = null;
    public static final String TARGET_URL = "http://www.practiceselenium.com/";
    public static String propFile = "src/main/java/com/framework/ux/utils/chapter10/selenium.properties";
    public static final String SE_PROPS = new File(propFile).getAbsolutePath();
    public static final String TEST_OUTPUT_PATH = "test-output/";
    public static final String LOGFILE_PATH = TEST_OUTPUT_PATH + "Logs/";
    public static final String REPORT_PATH = TEST_OUTPUT_PATH + "Reports/";
    public static final String REPORT_CONFIG_FILE = "src/main/java/com/framework/ux/utils/chapter10/extent-config.xml";

    // suite timeout defaults
    public static final int TIMEOUT_MINUTE = 60;
    public static final int TIMEOUT_ELEMENT = 10;
}
