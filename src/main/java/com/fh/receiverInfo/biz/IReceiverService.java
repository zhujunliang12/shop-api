package com.fh.receiverInfo.biz;

import com.fh.common.ServerResponse;
import com.fh.receiverInfo.po.ReceiverInfo;

public interface IReceiverService {
    ServerResponse queryOrder(Long memberId);

    ServerResponse addOrderData(Long memberId, ReceiverInfo orderInfo);

    ServerResponse getOrderById(Long memberId, ReceiverInfo orderInfo);

    ServerResponse deleteOrder(Long memberId, ReceiverInfo orderInfo);

    ServerResponse changeStatu(Long memberId, Integer receiverId, Integer statu);
}
