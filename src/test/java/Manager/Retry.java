package Manager;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

   private int count;
   private int limit =4;
    @Override
    public boolean retry(ITestResult iTestResult) {
    if (!(iTestResult.isSuccess()))
    {
        if (count<limit)
        {
            count++;
            return true;
        }

    }
        return false;
    }
}
