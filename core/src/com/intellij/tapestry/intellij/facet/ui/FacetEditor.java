package com.intellij.tapestry.intellij.facet.ui;

import com.intellij.facet.Facet;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.VerticalFlowLayout;
import com.intellij.tapestry.intellij.facet.AddTapestrySupportUtil;
import com.intellij.tapestry.intellij.facet.TapestryFacet;
import com.intellij.tapestry.intellij.facet.TapestryFacetConfiguration;
import com.intellij.tapestry.intellij.facet.TapestryVersion;
import com.intellij.ui.HyperlinkLabel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class FacetEditor extends FacetEditorTab {

    private JPanel _mainPanel;
    private JTextField _filterName;
    private JTextField _applicationPackage;
    private JPanel _descriptionPanel;
    private TapestryFacetConfiguration _configuration;

    public FacetEditor(TapestryFacet facet, TapestryFacetConfiguration configuration) {
        _configuration = configuration;

        //Filter filter = IntellijWebDescriptorUtils.getTapestryFilter(facet.getWebFacet().getRoot());

        //_configuration.setFilterName(filter != null ? filter.getFilterName().getValue() : null);
        //_configuration.setApplicationPackage(IntellijWebDescriptorUtils.getApplicationPackage(facet.getWebFacet().getRoot()));

        if (_configuration.getFilterName() == null)
            _configuration.setFilterName(facet.getModule().getName().toLowerCase());

        _filterName.setText(_configuration.getFilterName());
        _applicationPackage.setText(_configuration.getApplicationPackage());
    }

    @Nls
    public String getDisplayName() {
        return "Tapestry";
    }

    public JComponent createComponent() {
        return _mainPanel;
    }

  @Override
  public void onFacetInitialized(@NotNull final Facet facet) {
    if (_configuration.getVersion() == null) _configuration.setVersion(TapestryVersion.TAPESTRY_5_1_0_5);

    AddTapestrySupportUtil.addSupportInWriteCommandAction(facet.getModule(), _configuration, false, false);
  }

  public boolean isModified() {
        return _configuration.getFilterName() != _filterName.getText() ||
                _configuration.getApplicationPackage() != _applicationPackage.getText();
    }

    public void apply() throws ConfigurationException {
        _configuration.setFilterName(_filterName.getText());
        _configuration.setApplicationPackage(_applicationPackage.getText());
    }

    public void reset() {
        _filterName.setText(_configuration.getFilterName());
        _applicationPackage.setText(_configuration.getApplicationPackage());
    }

    public void disposeUIResources() {
    }

    private void createUIComponents() {
        _descriptionPanel = new JPanel(new VerticalFlowLayout());
        _descriptionPanel.add(new JLabel("Tapestry is an open-source framework for creating dynamic, robust, highly scalable web applications in Java."));

        HyperlinkLabel hyperlinkLabel = new HyperlinkLabel("More about Tapestry");
        hyperlinkLabel.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                BrowserUtil.launchBrowser("http://tapestry.apache.org");
            }
        });

        _descriptionPanel.add(hyperlinkLabel);
    }
}
