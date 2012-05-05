package configuration;

import org.apache.commons.configuration.*;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

public class App {
    public static void main( String[] args ) throws ConfigurationException {
        example1();
        example2();
        example3();
        example4();
        example5();
        example6();
    }
    
    private static void example1() throws ConfigurationException {
        System.out.println("== Example 1 ==");
        XMLConfiguration config = new XMLConfiguration("simple-const.xml");
        System.out.println(config.getString("database.url"));
        System.out.println(config.getString("database.port"));
    }
    
    private static void example2() throws ConfigurationException {
        System.out.println("== Example 2 ==");
        XMLConfiguration config = new XMLConfiguration("const.xml");
        System.out.println(config.getString("databases.database(0).url"));
        System.out.println(config.getString("databases.database(1).url"));
    }
    
    private static void example3() throws ConfigurationException {
        System.out.println("== Example 3 ==");
        XMLConfiguration config = new XMLConfiguration("const.xml");
        config.setExpressionEngine(new XPathExpressionEngine());
        System.out.println(config.getString("databases/database[name = 'dev']/url"));
        System.out.println(config.getString("databases/database[name = 'production']/url"));
    }
    
    private static void example4() throws ConfigurationException {
        System.out.println("== Example 4 ==");
        EnvironmentConfiguration config = new EnvironmentConfiguration();
        System.out.println(config.getString("ENV_TYPE"));
    }
    
    private static void example5() throws ConfigurationException {
        System.out.println("== Example 5 ==");
        System.out.println(getDbUrl());
    }
    
    private static String getDbUrl() throws ConfigurationException {
        EnvironmentConfiguration envConfig = new EnvironmentConfiguration();
        String env = envConfig.getString("ENV_TYPE");
        if("dev".equals(env) || "production".equals(env)) {
            XMLConfiguration xmlConfig = new XMLConfiguration("const.xml");
            xmlConfig.setExpressionEngine(new XPathExpressionEngine());
            return xmlConfig.getString("databases/database[name = '" + env + "']/url");
        } else {
            throw new IllegalStateException("%ENV_TYPE% environment variable is not set");
        }
    }

    private static void example6() throws ConfigurationException {
        System.out.println("== Example 6 ==");
        System.out.println(getDbUrl2());
    }
    
    private static String getDbUrl2() throws ConfigurationException {
        DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder("config.xml");
        boolean load = true;
        CombinedConfiguration config = builder.getConfiguration(load); 
        config.setExpressionEngine(new XPathExpressionEngine());
        String env = config.getString("ENV_TYPE");
        if("dev".equals(env) || "production".equals(env)) {
            return config.getString("databases/database[name = '" + env + "']/url");
        } else {
            throw new IllegalStateException("%ENV_TYPE% environment variable is not set");
        }
    }
}
