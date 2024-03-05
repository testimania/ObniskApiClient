// Singleton

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesDispatcher implements PropertiesInterface {
//    final String PROPERTIES_FILE_NAME_BY_DEFAULT = "src/main/local_application.properties";
    final String PROPERTIES_FILE_NAME_BY_DEFAULT = "application.properties";
//    final String SERVER_BY_DEFAULT = "https://dev.sintec.club";
    final String SERVER_BY_DEFAULT = "https://sintec.club";
    final String PRODUCTION_SERVER_BY_DEFAULT = "https://sintec.club";
//    final String PORT_BY_DEFAULT = "11001";
    final String PORT_BY_DEFAULT = "11000";
    final String PRODUCTION_PORT_BY_DEFAULT = "11000";

    static PropertiesDispatcher instance;
    private File fileDefault;
    private String mode;
    Properties properties;
    private PropertiesDispatcher() {
        properties = new Properties();
        try {
            loadDefaults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mode = properties.getProperty("property_mode", null);
        if (mode == null){
            mode = "DEV";
        }
            System.out.println("prop disp mode:" + mode);

    }

    private void loadDefaults() throws IOException {
//        fileDefault = new File("application.properties") ;
        fileDefault = new File(PROPERTIES_FILE_NAME_BY_DEFAULT) ;
        properties.load( new FileInputStream(fileDefault));
    }

    public static PropertiesDispatcher getInstance(){
        if (instance == null) {
            instance = new PropertiesDispatcher();
        }
        return instance;
    }
    @Override
    public String getPropertyByName(String propertyName) {
            // special cases:
            // TODO: 15.10.2023  do shorter !
            if ( (properties.getProperty(propertyName, null) == null) &&  propertyName.equalsIgnoreCase("property_server")){
                System.out.println("1");
                return mode.equalsIgnoreCase("dev") ? SERVER_BY_DEFAULT : PRODUCTION_SERVER_BY_DEFAULT;
//                return SERVER_BY_DEFAULT;
            }
            if ( (properties.getProperty(propertyName, null) == null) &&  propertyName.equalsIgnoreCase("property_server_port")){
                System.out.println("2");
                return mode.equalsIgnoreCase("dev") ? PORT_BY_DEFAULT : PRODUCTION_PORT_BY_DEFAULT;
//                return PORT_BY_DEFAULT;
            }

            if (propertyName.equalsIgnoreCase("property_server")){
                System.out.println("1.1");
                return mode.equalsIgnoreCase("dev") ? SERVER_BY_DEFAULT : PRODUCTION_SERVER_BY_DEFAULT;
            }

            if (propertyName.equalsIgnoreCase("property_server_port")){
                System.out.println("2.1");
                return mode.equalsIgnoreCase("dev") ? PORT_BY_DEFAULT : PRODUCTION_PORT_BY_DEFAULT;
            }

            // basic code:
        System.out.println("3");
            return properties.getProperty(propertyName, null);
    }

    @Override
    public void setPropertyByName(String propertyName) {
        // TODO: 14.10.2023
        System.out.println(" ...in development...");
    }

    public static void main(String[] args) {
        PropertiesDispatcher propertiesDispatcher = new PropertiesDispatcher();
        System.out.println(propertiesDispatcher.getPropertyByName("property_server"));
        System.out.println(propertiesDispatcher.getPropertyByName("property_server_port"));

    }

}
