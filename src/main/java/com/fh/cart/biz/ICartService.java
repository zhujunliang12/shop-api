package com.fh.cart.biz;

import com.fh.common.ServerResponse;

import java.util.List;

public interface ICartService {
    ServerResponse addCart(Long memberId, Long goodsId, int num);

    ServerResponse queryMemberCartList(Long id);

    ServerResponse deleteCartProduct(Long memberId, Long productId);

    ServerResponse deleteCheck(Long memberId, List<Long> ids);

    ServerResponse queryGuanZhuProduct(Long memberId);

    ServerResponse changeAttention(Long memberId, Long goodsId);

    ServerResponse queryCartSumNum(Long memberId);

    ServerResponse checkProduct(Long memberId, List<Long> ids);
}
