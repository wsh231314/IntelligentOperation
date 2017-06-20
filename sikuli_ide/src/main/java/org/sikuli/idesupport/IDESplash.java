/*
 * Copyright (c) 2010-2016, Sikuli.org, sikulix.com
 * Released under the MIT License.
 *
 */

package org.sikuli.idesupport;

import org.sikuli.script.RunTime;

import javax.swing.*;
import java.awt.*;

public class IDESplash extends JFrame {
    RunTime runTime = null;
    JLabel action;
    JLabel step;

    public IDESplash(RunTime rt) {
        init(rt);
    }

    void init(RunTime rt) {
        runTime = rt;
        setResizable(false);
        setUndecorated(true);
        Container pane = getContentPane();
        ((JComponent) pane).setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(new JLabel(" "));
        JLabel title = new JLabel("");
        title.setAlignmentX(CENTER_ALIGNMENT);
        pane.add(title);
        pane.add(new JLabel(" "));
        action = new JLabel(" ");
        action.setAlignmentX(CENTER_ALIGNMENT);
        pane.add(action);
        pane.add(new JLabel(" "));
        step = new JLabel("... starting");
        step.setAlignmentX(CENTER_ALIGNMENT);
        pane.add(step);
        pane.add(new JLabel(" "));
        JLabel version = new JLabel("");
        version.setAlignmentX(CENTER_ALIGNMENT);
        pane.add(version);
        pane.add(new JLabel(" "));
        pack();
        setSize(200, 155);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void showAction(String actionText) {
        action.setText(actionText);
        repaint();
    }

    public void showStep(String stepTitle) {
        step.setText(stepTitle);
        repaint();
    }
}