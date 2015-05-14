/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.ShopOwnersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("shopOwnerService")
public class ShopOwnerService {

    @Autowired
    private ShopOwnersDao shopOwnersDao;

    public boolean createShopOwner(int userId, int shopId) {
        return shopOwnersDao.createShopOwner(userId, shopId);
    }

}
