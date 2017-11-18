/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import javax.swing.JPanel;

/**
 *
 * @author Biswajit Debnath
 */
public class PanelSlider {

    public void changeThePanel(JPanel outerPanel, JPanel innerPanel) {
        outerPanel.removeAll();
        outerPanel.repaint();
        outerPanel.revalidate();
        outerPanel.add(innerPanel);
        outerPanel.repaint();
        outerPanel.revalidate();
    }

}
