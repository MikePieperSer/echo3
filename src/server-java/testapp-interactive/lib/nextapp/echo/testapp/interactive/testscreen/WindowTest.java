/* 
 * This file is part of the Echo Web Application Framework (hereinafter "Echo").
 * Copyright (C) 2002-2009 NextApp, Inc.
 *
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 */

package nextapp.echo.testapp.interactive.testscreen;

import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.testapp.interactive.ButtonColumn;
import nextapp.echo.testapp.interactive.InteractiveApp;
import nextapp.echo.testapp.interactive.StyleUtil;

/**
 * Interactive test for top-level <code>Window</code>s.
 */
public class WindowTest extends SplitPane {
    
    public WindowTest() {
        super();
        setStyleName("TestControls");
        
        ButtonColumn controlsColumn = new ButtonColumn();
        controlsColumn.setStyleName("TestControlsColumn");
        add(controlsColumn);

        controlsColumn.addButton("Set window title", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getApplicationInstance().getDefaultWindow().setTitle(
                        "Echo Test Application " + ((int) (Math.random() * Integer.MAX_VALUE)));
            }
        });
        controlsColumn.addButton("Set Foreground", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getApplicationInstance().getDefaultWindow().setForeground(StyleUtil.randomColor());
            }
        });
        controlsColumn.addButton("Clear Foreground", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getApplicationInstance().getDefaultWindow().setForeground(null);
            }
        });
        controlsColumn.addButton("Set Background", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getApplicationInstance().getDefaultWindow().setBackground(StyleUtil.randomColor());
            }
        });
        controlsColumn.addButton("Clear Background", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getApplicationInstance().getDefaultWindow().setBackground(null);
            }
        });
        controlsColumn.addButton("Set Font", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getApplicationInstance().getDefaultWindow().setFont(StyleUtil.randomFont());
            }
        });
        controlsColumn.addButton("Clear Font", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getApplicationInstance().getDefaultWindow().setFont(null);
            }
        });
        controlsColumn.addButton("Multiple Content Changes", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InteractiveApp.getApp().displayWelcomePane();
                InteractiveApp.getApp().displayTestPane();
                InteractiveApp.getApp().displayWelcomePane();
                InteractiveApp.getApp().displayTestPane();
                InteractiveApp.getApp().displayWelcomePane();
                InteractiveApp.getApp().displayTestPane();
                InteractiveApp.getApp().displayTestPane();
                InteractiveApp.getApp().displayTestPane();
                InteractiveApp.getApp().displayWelcomePane();
                InteractiveApp.getApp().displayWelcomePane();
                InteractiveApp.getApp().displayWelcomePane();
                InteractiveApp.getApp().displayTestPane();
                InteractiveApp.getApp().displayWelcomePane();
                InteractiveApp.getApp().displayTestPane();
            }
        });
    }
}
