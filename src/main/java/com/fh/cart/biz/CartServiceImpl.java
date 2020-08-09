package com.fh.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.vo.Cart;
import com.fh.cart.vo.CartItem;
import com.fh.common.Constant;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import com.fh.product.mapper.IProductMapper;
import com.fh.product.po.Product;
import com.fh.utils.BigectomUtils;
import com.fh.utils.CartUtils;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements ICartService {

    @Resource
    private IProductMapper productMapper;

    @Override
    public ServerResponse addCart(Long memberId, Long goodsId, int num) {
        //验证商品信息
        Product product = productMapper.getProductById(goodsId);
        if (product == null) {
            return ServerResponse.error(ResponseEnum.CARTINFO_is_null);
        }
        //验证商品的状态
        if (product.getStatus( ) != Constant.PRODUCUT_STATU_DOWN) {
            return ServerResponse.error(ResponseEnum.PRODUCT_STATU_DOWN);
        }
        // 判断 redis 中是否有 该会员的 购物车的信息
        String cartStr = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        //如果 存在 购物车信息 则分情况 处理
        if (StringUtils.isNotEmpty(cartStr)) {
            Cart cart = JSONObject.parseObject(cartStr, Cart.class);
            // 首先判断 该商品在 购物车中 是否已存在  存在 则 找到后  重新计算 数量 小计
            List<CartItem> cartItemList = cart.getCartItemList( );
            CartItem ctem = null;
            for (CartItem cartItem : cartItemList) {
                if (cartItem.getGoodsId( ).longValue( ) == goodsId.longValue( )) {
                    ctem = cartItem;
                    break;
                }
            }
            if (ctem != null) {   //说明购物车 已存在该商品 则更新 数量 小计
                ctem.setNum(ctem.getNum( ) + num);
                int n = ctem.getNum( );
                if (n <= 0) {    //如果 一直减减  到商品的数量为0了 则 删除该商品
                    cartItemList.remove(ctem);    //移除集合中的商品
                } else {
                    BigDecimal muil = BigectomUtils.muil(ctem.getNum( ) + "", ctem.getPrice( ).toString( ));
                    ctem.setSubPrice(muil);
                }
                // 然后 去 更新 购物车 的 总数量 总价格
                CartUtils.updateCart(memberId, cart);
            } else {
                // 该商品 不存在  则将该商品贴加到 购物车中去
                if (num <= 0) {  //为了防止 商品不存在 直接传数量为 负数
                    return ServerResponse.error(ResponseEnum.CATEITMP_NUM);
                }
                CartItem cartItemInfo = CartUtils.createCartItmp(num, product);
                //将商品贴加到 购物车中
                cart.getCartItemList( ).add(cartItemInfo);
                // 然后 跟新 购物车中的 总数量 总价格
                CartUtils.updateCart(memberId, cart);
            }

        } else {
            // 不存在 购物车信息 则 创建购物车
            if (num <= 0) {  //为了防止 商品不存在 直接传数量为 负数
                return ServerResponse.error(ResponseEnum.CATE_NUM);
            }
            Cart cart = new Cart( );
            List<CartItem> cartItemList = cart.getCartItemList( );
            CartItem cartItemInfo = CartUtils.createCartItmp(num, product);
            //将商品贴加到 购物车中
            cartItemList.add(cartItemInfo);
            // 然后 跟新 购物车中的 总数量 总价格
            CartUtils.updateCart(memberId, cart);
        }
        return ServerResponse.success( );
    }
//=================================================================================================================
      /*  Product product = this.productMapper.getProductById(goodsId);
        if(product ==null){
            return ServerResponse.error(ResponseEnum.CARTINFO_is_null);
        }
        if(product.getStatus()==Constant.PRODUCUT_STATU_DOWN){
            return ServerResponse.error(ResponseEnum.PRODUCT_STATU_DOWN);
        }
        // 判断 redis中 是否存有购物车的信息
        String cartkey = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        if(StringUtils.isNotEmpty(cartkey)){  //如果不为空 说明有购物车的信息
            // 然后 判断该 商品在购物车中 是否存在  存在则 更新数量 小计
            Cart cart = JSONObject.parseObject(cartkey, Cart.class);
            List<CartItem> cartItemList = cart.getCartItemList( );
            CartItem cartIt=null;
            for (CartItem cartItem:cartItemList) {
                if(cartItem.getGoodsId().longValue()==goodsId.longValue()){
                    cartIt=cartItem;
                    break;
                }
            }
            if(cartIt != null){  //说明该购物车有该商品 跟新数量 小计  由于是bigdex数据类型 计算时注意一下
                cartIt.setNum(cartIt.getNum()+num);
                if(cartIt.getNum()<=0){  // 如果商品的数量为0 则直接删除该商品
                    cartItemList.remove(cartIt);
                }
                BigDecimal subPrice = BigectomUtils.muil(cartIt.getNum( ) + "", cartIt.getPrice( ).toString( ));
                cartIt.setSubPrice(subPrice);
                // 跟新 购物车中 的 总数量 总价格
                int totalNum=0;
                BigDecimal totalPrice=new BigDecimal(0);
                for (CartItem cartItem:cartItemList) {
                    totalNum+=cartItem.getNum();
                    totalPrice=BigectomUtils.jia(totalPrice.toString(),cartItem.getSubPrice().toString());
                }
                cart.setTotalPrice(totalPrice);
                cart.setTotalNum(totalNum);
                if(cart.getCartItemList().size()==0){
                    RedisUtils.delKey(KeyUtils.cartKey(memberId));
                }else{
                    String cartJson = JSONObject.toJSONString(cart);
                    RedisUtils.setKey(KeyUtils.cartKey(memberId),cartJson);
                }
            }else{
                if(num<=0){
                    return ServerResponse.error(ResponseEnum.CATEITMP_NUM);
                }
                Cart cart1 = CartUtils.getCart(cart, product, num);
                if(cart1!=null){
                    String cartJson = JSONObject.toJSONString(cart1);
                    RedisUtils.setKey(KeyUtils.cartKey(memberId),cartJson);
                }
            }

        }else{
            if(num<=0){
                return ServerResponse.error(ResponseEnum.CATE_NUM);
            }
            Cart cart = new Cart( );
            Cart cart1 = CartUtils.getCart(cart, product, num);

            String cartJson = JSONObject.toJSONString(cart1);
            RedisUtils.setKey(KeyUtils.cartKey(memberId),cartJson);
        }
        return ServerResponse.success( );
    }*/

    /**
     * 查询指定会员的购物车信息
     *
     * @param id
     * @return
     */
    @Override
    public ServerResponse queryMemberCartList(Long id) {
        //从redis中获取购物车信息
        String key = RedisUtils.getKey(KeyUtils.cartKey(id));
        Cart cart = null;
        if (StringUtils.isNotEmpty(key)) {
            cart = JSONObject.parseObject(key, Cart.class);
        }
        return ServerResponse.success(cart);
    }

    /**
     * 删除购物车中的指定商品
     *
     * @param memberId
     * @param productId
     * @return
     */
    @Override
    public ServerResponse deleteCartProduct(Long memberId, Long productId) {
        String cartJson = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> cartItemList = cart.getCartItemList( );
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getGoodsId( ).longValue( ) == productId.longValue( )) {
                cartItemList.remove(cartItem);
                break;
            }
        }
        // 最后 跟新 redis 中的数据
        CartUtils.updateCart(memberId, cart);
        return ServerResponse.success( );
    }

    /**
     * 删除 购物车 选中的商品
     *
     * @param memberId
     * @param ids
     * @return
     */
    @Override
    public ServerResponse deleteCheck(Long memberId, List<Long> ids) {
        String cartJson = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> cartItemList = cart.getCartItemList( );
        Iterator<CartItem> iterator = cartItemList.iterator( );
        while (iterator.hasNext( )) {
            CartItem next = iterator.next( );
            for (Long id : ids) {
                if (next.getGoodsId( ).longValue( ) == id.longValue( )) {
                    iterator.remove( );
                }
            }
        }
        // 最后 跟新 redis 中的数据
        CartUtils.updateCart(memberId, cart);
        return ServerResponse.success( );
    }

    @Override
    public ServerResponse queryGuanZhuProduct(Long memberId) {
        String cartJson = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> cartItemList = cart.getCartItemList( );
        List<CartItem> newList = new ArrayList<CartItem>( );
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getAttention( ) == 1) {
                newList.add(cartItem);
            }
        }
        return ServerResponse.success(newList);
    }

    // 改变 关注的 状态
    @Override
    public ServerResponse changeAttention(Long memberId, Long goodsId) {
        String cartJson = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> cartItemList = cart.getCartItemList( );
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getGoodsId( ).longValue( ) == goodsId.longValue( )) {
                if (cartItem.getAttention( ) != 1) {
                    cartItem.setAttention(1);
                    break;
                }
            }
        }
        // 最后 跟新 redis 中的数据
        CartUtils.updateCart(memberId, cart);
        return ServerResponse.success( );
    }

    /*
           查询购物车商品的总数
     */
    @Override
    public ServerResponse queryCartSumNum(Long memberId) {
        String cartJson = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        int totalNum = 0;
        if (cart != null) {
            totalNum = cart.getTotalNum( );
        }
        return ServerResponse.success(totalNum);
    }

    //获取被选中的商品的信息
    @Override
    public ServerResponse checkProduct(Long memberId, List<Long> ids) {
        String cartJson = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> list = null;
        if (cart != null) {
            list = new ArrayList<CartItem>( );
            List<CartItem> cartItemList = cart.getCartItemList( );

            Iterator<CartItem> iterator = cartItemList.iterator( );
            while (iterator.hasNext( )) {
                CartItem next = iterator.next( );
                for (Long id : ids) {
                    if (next.getGoodsId( ).longValue( ) == id.longValue( )) {
                        list.add(next);
                        //   iterator.remove();
                    }
                }
            }
            if (cartItemList.size( ) == 0) {
                RedisUtils.delKey(KeyUtils.cartKey(memberId));
            }
        }

        // 最后 跟新 redis 中的数据
        CartUtils.updateCart(memberId, cart);
        return ServerResponse.success(list);
    }


}
