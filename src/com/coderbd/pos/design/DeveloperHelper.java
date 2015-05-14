/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.design;

import com.coderbd.pos.constraints.Role;
import javax.swing.JComboBox;

/**
 *
 * @author Biswajit Debnath
 */
public class DeveloperHelper {

    public void addRole(JComboBox roleComboBox) {
        roleComboBox.removeAllItems();
        roleComboBox.addItem(Role.USER_DEVELOPER);
        roleComboBox.addItem(Role.USER_MANAGER);
        roleComboBox.addItem(Role.USER_STAFF);
    }

}
