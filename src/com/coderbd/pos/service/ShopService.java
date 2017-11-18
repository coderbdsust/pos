/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderbd.pos.service;

import com.coderbd.pos.dao.ShopOwnersDao;
import com.coderbd.pos.dao.ShopsDao;
import com.coderbd.pos.entity.Shop;
import com.coderbd.pos.entity.User;
import com.coderbd.pos.entity.pojo.ShopDailyProfit;
import com.coderbd.pos.entity.pojo.ShopBalance;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Biswajit Debnath
 */
@Service("shopService")
public class ShopService {

    @Autowired
    private ShopsDao shopsDao;

    @Autowired
    private ShopOwnersDao shopOwnersDao;

    public boolean createShop(User user, Shop shop) {
        return shopsDao.createShop(user, shop);
    }

    public List<Shop> getShops(User user) {
        return shopsDao.getShops(user);
    }

    public List<ShopBalance> getShopBalances(List<Shop> shops) {
        List<ShopBalance> shopBalances = new ArrayList<ShopBalance>();
        for (Shop shop : shops) {
            List<ShopDailyProfit> shopDailyProfits = shopsDao.getShopDailyProfits(shop);
            shopBalances.add(new ShopBalance(shop, shopDailyProfits));
        }
        return shopBalances;
    }

}
