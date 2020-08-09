package com.fh.utils;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.vo.Cart;
import com.fh.cart.vo.CartItem;
import com.fh.product.po.Product;

import java.math.BigDecimal;
import java.util.List;

public class CartUtils {

    public static Cart getCart(Cart cart, Product product, int num) {
        List<CartItem> cartItemList = cart.getCartItemList( );
        CartItem cartItemInfo = new CartItem( );
        cartItemInfo.setGoodsId(product.getProductId( ).longValue( ));
        cartItemInfo.setGoodsName(product.getProductName( ));
        cartItemInfo.setImgUrl(product.getProductImg( ));
        cartItemInfo.setNum(num);
        cartItemInfo.setAttention(product.getAttention( ));
        cartItemInfo.setPrice(product.getPrice( ));
        BigDecimal muil = BigectomUtils.muil(num + "", cartItemInfo.getPrice( ).toString( ));
        cartItemInfo.setSubPrice(muil);
        cartItemList.add(cartItemInfo);
        if (cartItemList.size( ) > 0) {
            // 然后 跟新 购物车中的 总数量 总价格
            int totalNum = 0;
            BigDecimal totalPrice = new BigDecimal(0);
            for (CartItem cartItem : cartItemList) {
                totalNum += cartItem.getNum( );
                totalPrice = BigectomUtils.jia(totalPrice.toString( ), cartItem.getSubPrice( ).toString( ));
            }
            cart.setTotalPrice(totalPrice);
            cart.setTotalNum(totalNum);
            return cart;
        } else {
            RedisUtils.delKey(KeyUtils.cartKey(product.getProductId( )));
            return cart;
        }

    }


    /**
     * 提取了更新购物车  更新购物车数据
     *
     * @param memberId
     * @param cart
     */
    public static void updateCart(Long memberId, Cart cart) {
        if (cart != null) {
            List<CartItem> cartItemList = cart.getCartItemList( );
            int totalNum = 0;
            BigDecimal totalPrice = new BigDecimal(0);
            for (CartItem cartItem : cartItemList) {
                totalNum += cartItem.getNum( );
                totalPrice = BigectomUtils.jia(totalPrice.toString( ), cartItem.getSubPrice( ).toString( ));
            }
            cart.setTotalNum(totalNum);
            cart.setTotalPrice(totalPrice);
            if (cartItemList.size( ) == 0) {
                RedisUtils.delKey(KeyUtils.cartKey(memberId));
                return;
            }
            // 最后 跟新 redis 中的数据
            String carJson = JSONObject.toJSONString(cart);
            RedisUtils.setKey(KeyUtils.cartKey(memberId), carJson);
        }

    }

    public static CartItem createCartItmp(int num, Product product) {
        CartItem cartItemInfo = new CartItem( );
        cartItemInfo.setGoodsId(product.getProductId( ).longValue( ));
        cartItemInfo.setGoodsName(product.getProductName( ));
        cartItemInfo.setImgUrl(product.getProductImg( ));
        cartItemInfo.setNum(num);
        cartItemInfo.setAttention(product.getAttention( ));
        cartItemInfo.setPrice(product.getPrice( ));
        BigDecimal subPrice = BigectomUtils.muil(num + "", product.getPrice( ).toString( ));
        cartItemInfo.setSubPrice(subPrice);
        return cartItemInfo;
    }
}
