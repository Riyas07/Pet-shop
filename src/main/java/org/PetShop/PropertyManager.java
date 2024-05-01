package org.PetShop;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static PropertyManager propertyManager;
    private Properties properties;
    private PropertyManager()
    {
        try
        {
            InputStream inputStream=new FileInputStream("config/config.properties");
            properties=new Properties();
            properties
                    .load(inputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static PropertyManager getInstance()
    {
        if (propertyManager==null)
        {
            propertyManager=new PropertyManager();
            return propertyManager;
        }
        else {
            return propertyManager;
        }
    }
    public String getToken()
    {
      return   properties.getProperty("token");
    }
    public String get_baseUrl()
    {
        return properties.getProperty("base_url");
    }

}
