/*
 * Copyright (c) 2019, 2021, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package sun.awt;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class PlatformGraphicsInfo {

    public static GraphicsEnvironment createGE() {
        return new X11GraphicsEnvironment();
    }

    public static Toolkit createToolkit() {
        return new sun.awt.X11.XToolkit();
    }

    /**
      * Called from java.awt.GraphicsEnvironment when
      * to check if on this platform, the JDK should default to
      * headless mode, in the case the application did specify
      * a value for the java.awt.headless system property.
      */
    @SuppressWarnings("removal")
    public static boolean getDefaultHeadlessProperty() {
        return
            AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {

               final String display = System.getenv("DISPLAY");
               return  display == null || display.trim().isEmpty();
            });
    }

    /**
      * Called from java.awt.GraphicsEnvironment when
      * getDefaultHeadlessProperty() has returned true, and
      * the application has called an API that requires headful.
      */
    public static String getDefaultHeadlessMessage() {
        return
            "\nNo X11 DISPLAY variable was set,\n" +
            "but this program performed an operation which requires it.";
    }
}
