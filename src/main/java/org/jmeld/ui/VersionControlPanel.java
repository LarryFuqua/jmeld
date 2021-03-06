/*
   JMeld is a visual diff and merge tool.
   Copyright (C) 2007  Kees Kuip
   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.
   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.
   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA  02110-1301  USA
 */
package org.jmeld.ui;

import org.jmeld.JMeldException;
import org.jmeld.util.conf.ConfigurationListenerIF;
import org.jmeld.util.file.VersionControlDiff;
import org.jmeld.util.node.JMDiffNode;

import javax.swing.*;
import java.awt.*;

public class VersionControlPanel extends AbstractContentPanel implements ConfigurationListenerIF {
    private JMeldPanel mainPanel;
    private VersionControlDiff diff;

    public VersionControlPanel(JMeldPanel mainPanel, VersionControlDiff diff) {
        this.mainPanel = mainPanel;
        this.diff = diff;

        init();
    }

    private void init() {

        final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        VersionControlDiffPanel versionControlDiffPanel = new VersionControlDiffPanel(mainPanel, diff) {
            @Override
            protected void openInContext(JMDiffNode diffNode) throws JMeldException {
                BufferDiffPanel panel = new BufferDiffPanel(mainPanel, false, false);
                panel.setId("context diff");
                diffNode.diff();
                panel.setDiffNode(diffNode);
                panel.doGoToFirst();
                splitPane.setBottomComponent(panel);
                splitPane.setDividerLocation(.5);
                splitPane.updateUI();
            }
        };

        splitPane.setTopComponent(versionControlDiffPanel);
        splitPane.updateUI();

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, splitPane);
    }

    public void configurationChanged() { }
}
