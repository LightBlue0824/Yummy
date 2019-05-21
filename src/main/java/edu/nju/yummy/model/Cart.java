package edu.nju.yummy.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    private Map<Integer, Map<Integer, Integer>> cart;     //shopid, dishid, num

    public Cart() {
        this.cart = new HashMap<>();
    }

    /**
     * 向购物车中添加商品1件
     * @param shopid
     * @param dishid
     */
    public void addDish(int shopid, int dishid){
        Map<Integer, Integer> dishOfThisShop;
        if(cart.containsKey(shopid)){           //已经在这个店里添加过商品
            dishOfThisShop = cart.get(shopid);
        }
        else{
            dishOfThisShop = new HashMap<>();
        }
        if(dishOfThisShop.containsKey(dishid)){     //已经添加过这个商品, 则数量+1
            dishOfThisShop.put(dishid, dishOfThisShop.get(dishid)+1);
        }
        else{           //还未添加过这个商品
            dishOfThisShop.put(dishid, 1);
        }
        cart.put(shopid, dishOfThisShop);
    }

    /**
     * 购物车中减少商品数量1件
     * @param shopid
     * @param dishid
     */
    public void subDish(int shopid, int dishid) {
        Map<Integer, Integer> dishOfThisShop = cart.get(shopid);
        if(dishOfThisShop.containsKey(dishid)){     //有这个商品, 则数量-1, 为0则移除
            if(dishOfThisShop.get(dishid)-1 == 0){
                dishOfThisShop.remove(dishid);
            }
            else{
                dishOfThisShop.put(dishid, dishOfThisShop.get(dishid)-1);
            }
        }
        cart.put(shopid, dishOfThisShop);
    }

    /**
     *
     * @param shopid
     * @param dishid
     */
    public void delDish(int shopid, int dishid) {
        Map<Integer, Integer> dishOfThisShop = cart.get(shopid);
        if(dishOfThisShop.containsKey(dishid)){     //有这个商品, 则数量-1, 为0则移除
            dishOfThisShop.remove(dishid);
        }
        cart.put(shopid, dishOfThisShop);
    }

    /**
     * 获取购物车中这个商店的商品数量
     * @param shopid
     * @return
     */
    public int itemSumOfThisShop(int shopid){
        int sum = 0;
        Map<Integer, Integer> dishOfThisShop;
        if(cart.containsKey(shopid)){           //已经在这个店里添加过商品
            dishOfThisShop = cart.get(shopid);
            if(dishOfThisShop != null){
                for(Integer temp : dishOfThisShop.values()){
                    sum += temp;
                }
            }
        }
        return sum;
    }

    /**
     * 清空这个店的购物车内容
     * @param shopid
     */
    public void delItemOfThisShop(int shopid){
        cart.remove(shopid);
    }

    public Map<Integer, Map<Integer, Integer>> getCart() {
        return cart;
    }

    public void setCart(Map<Integer, Map<Integer, Integer>> cart) {
        this.cart = cart;
    }

}
