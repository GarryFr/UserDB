package com.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import com.wicket.*;

public class StartApplication extends WebApplication
{
    @Override
    public Class getHomePage()
    {
        return LoginPage.class;
    }
}