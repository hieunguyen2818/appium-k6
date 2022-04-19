package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    public static AppiumDriver<MobileElement> getDrivers(Platforms platform) {

        if(platform==null) {
            throw new IllegalArgumentException(
                    "Platform cannot be null, you can provide one of these"+ Arrays.toString(Platforms.values()));
        }
        AppiumDriver<MobileElement> appiumDriver = null;
        Exception exception = null;
        try {
            //Desired Capabilities
            DesiredCapabilities desiredCaps = new DesiredCapabilities();
            desiredCaps.setCapability("platformName", "Android");
            desiredCaps.setCapability("automationName", "uiautomator2");
            desiredCaps.setCapability("udid", "emulator-5554");
            desiredCaps.setCapability("appPackage", "com.wdiodemoapp");
            desiredCaps.setCapability("appActivity", "com.wdiodemoapp.MainActivity");

            URL appiumServer = new URL("http://localhost:4723/wd/hub");

            switch (platform) {
                case android:
                    appiumDriver = new AndroidDriver<MobileElement>(appiumServer,desiredCaps);
                    break;
                case ios:
                    appiumDriver = new IOSDriver<MobileElement>(appiumServer,desiredCaps);

            }
        }catch (Exception e) {
            exception =e;
        }
        if (appiumDriver == null) {
            throw new RuntimeException(exception.getMessage());
        }
        appiumDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        return appiumDriver;
    }
}
