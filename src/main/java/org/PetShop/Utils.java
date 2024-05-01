package org.PetShop;

import java.time.LocalDate;
import java.util.Random;

public class Utils {
    private static Utils utils;
    private Utils()
    {

    }
    public static Utils getInstance()
    {
        if (utils==null)
        {
            utils=new Utils();
            return utils;
        }
        else {
            return utils;
        }
    }
    public int generateRandomNumber()
    {
        Random ran=new Random();
      return   ran.nextInt(1000*60*60)+ LocalDate.now().getDayOfMonth()+786;
    }
    public String getToken()
    {
        return PropertyManager.getInstance().getToken();
    }

}
