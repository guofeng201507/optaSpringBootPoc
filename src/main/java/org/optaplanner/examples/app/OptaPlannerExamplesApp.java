/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.app;

import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.swingui.OpenBrowserAction;
import org.optaplanner.examples.common.swingui.SolverAndPersistenceFrame;
import org.optaplanner.examples.nurserostering.app.NurseRosteringApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OptaPlannerExamplesApp extends JFrame {

    /**
     * Supported system properties: {@link CommonApp#DATA_DIR_SYSTEM_PROPERTY}.
     * @param args never null
     */
    public static void main(String[] args) {
        CommonApp.prepareSwingEnvironment();
        OptaPlannerExamplesApp optaPlannerExamplesApp = new OptaPlannerExamplesApp();
        optaPlannerExamplesApp.pack();
        optaPlannerExamplesApp.setLocationRelativeTo(null);
        optaPlannerExamplesApp.setVisible(true);
    }

    private static String determineOptaPlannerExamplesVersion() {
        String optaPlannerExamplesVersion = OptaPlannerExamplesApp.class.getPackage().getImplementationVersion();
        if (optaPlannerExamplesVersion == null) {
            optaPlannerExamplesVersion = "";
        }
        return optaPlannerExamplesVersion;
    }

    private JTextArea descriptionTextArea;
    private WebExamplesDialog webExamplesDialog;

    public OptaPlannerExamplesApp() {
        super("OptaPlanner examples " + determineOptaPlannerExamplesVersion());
        setIconImage(SolverAndPersistenceFrame.OPTA_PLANNER_ICON.getImage());
        setContentPane(createContentPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Container createContentPane() {
        JPanel contentPane = new JPanel(new BorderLayout(5, 5));
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel titleLabel = new JLabel("Which example do you want to see?", JLabel.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(20.0f));
        contentPane.add(titleLabel, BorderLayout.NORTH);
        JScrollPane examplesScrollPane = new JScrollPane(createExamplesPanel());
        examplesScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        examplesScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        examplesScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        contentPane.add(examplesScrollPane, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(createDescriptionPanel(), BorderLayout.CENTER);
        bottomPanel.add(createExtraPanel(), BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        return contentPane;
    }

    private JPanel createExamplesPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 4, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.add(createExampleButton(new NurseRosteringApp()));
        return panel;
    }

    private JButton createExampleButton(final CommonApp commonApp) {
        String iconResource = commonApp.getIconResource();
        Icon icon = iconResource == null ? new EmptyIcon() : new ImageIcon(getClass().getResource(iconResource));
        JButton button = new JButton(new AbstractAction(commonApp.getName(), icon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                commonApp.init(OptaPlannerExamplesApp.this, false);
            }
        });
        button.setHorizontalAlignment(JButton.LEFT);
        button.setHorizontalTextPosition(JButton.RIGHT);
        button.setVerticalTextPosition(JButton.CENTER);
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                descriptionTextArea.setText(commonApp.getDescription());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                descriptionTextArea.setText("");
            }

        });
        return button;
    }

    private JButton createDisabledExampleButton(final CommonApp commonApp) {
        JButton exampleButton = createExampleButton(commonApp);
        exampleButton.setEnabled(false);
        return exampleButton;
    }

    private JPanel createDescriptionPanel() {
        JPanel descriptionPanel = new JPanel(new BorderLayout(2, 2));
        descriptionPanel.add(new JLabel("Description"), BorderLayout.NORTH);
        descriptionTextArea = new JTextArea(8, 65);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionPanel.add(new JScrollPane(descriptionTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        return descriptionPanel;
    }

    private JPanel createExtraPanel() {
        JPanel extraPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        extraPanel.add(new JPanel());
        webExamplesDialog = new WebExamplesDialog();
        Action webExamplesAction = new AbstractAction("Show web examples") {
            @Override
            public void actionPerformed(ActionEvent event) {
                webExamplesDialog.setLocationRelativeTo(OptaPlannerExamplesApp.this);
                webExamplesDialog.setVisible(true);
            }
        };
        extraPanel.add(new JButton(webExamplesAction));
        Action homepageAction = new OpenBrowserAction("www.optaplanner.org", "https://www.optaplanner.org");
        extraPanel.add(new JButton(homepageAction));
        Action documentationAction = new OpenBrowserAction("Documentation", "https://www.optaplanner.org/learn/documentation.html");
        extraPanel.add(new JButton(documentationAction));
        return extraPanel;
    }

    private static class EmptyIcon implements Icon {

        @Override
        public int getIconWidth() {
            return 64;
        }

        @Override
        public int getIconHeight() {
            return 64;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            // Do nothing
        }

    }

    private class WebExamplesDialog extends JDialog {

        private WebExamplesDialog() {
            super(OptaPlannerExamplesApp.this, "Web examples", true);
            JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            contentPanel.add(createMiddlePanel(), BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            Action okAction = new AbstractAction("OK") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            };
            buttonPanel.add(new JButton(okAction));
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);
            setContentPane(contentPanel);
            pack();
        }

        public JComponent createMiddlePanel() {
            JPanel middlePanel = new JPanel(new GridLayout(0, 1));
            middlePanel.add(new JLabel("To see the webexamples, deploy the optaplanner-webexamples-*.war on an application server."));
            return middlePanel;
        }

    }

}
