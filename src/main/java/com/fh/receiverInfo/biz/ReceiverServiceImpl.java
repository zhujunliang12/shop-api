package com.fh.receiverInfo.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.receiverInfo.mapper.IReceiverMapper;
import com.fh.receiverInfo.po.ReceiverInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("receiverService")
public class ReceiverServiceImpl implements IReceiverService {
    @Resource
    private IReceiverMapper receiverMapper;


    @Override
    public ServerResponse queryOrder(Long memberId) {
        List<ReceiverInfo> queryOrderList = receiverMapper.selectList(new QueryWrapper<ReceiverInfo>( ).eq("id", memberId));
        return ServerResponse.success(queryOrderList);
    }

    @Override
    public ServerResponse addOrderData(Long memberId, ReceiverInfo orderInfo) {
        orderInfo.setId(memberId);
        if (orderInfo.getReceiverId( ) == null) {
            receiverMapper.insert(orderInfo);
        } else {
            receiverMapper.update(orderInfo, new QueryWrapper<ReceiverInfo>( ).eq("receiverId", orderInfo.getReceiverId( )));
        }
        return ServerResponse.success( );
    }

    @Override
    public ServerResponse getOrderById(Long memberId, ReceiverInfo orderInfo) {
        orderInfo.setId(memberId);
        ReceiverInfo order = receiverMapper.selectOne(new QueryWrapper<ReceiverInfo>( ).eq("receiverId", orderInfo.getReceiverId( )));
        return ServerResponse.success(order);
    }

    @Override
    public ServerResponse deleteOrder(Long memberId, ReceiverInfo orderInfo) {
        orderInfo.setId(memberId);
        receiverMapper.delete(new QueryWrapper<ReceiverInfo>( ).eq("receiverId", orderInfo.getReceiverId( )));
        return ServerResponse.success( );
    }

    @Override
    public ServerResponse changeStatu(Long memberId, Integer receiverId, Integer statu) {

        ReceiverInfo orderInfo = receiverMapper.selectOne(new QueryWrapper<ReceiverInfo>( ).eq("statu", statu));
        if (orderInfo != null) {
            orderInfo.setStatu(1);
        }
        receiverMapper.update(orderInfo, new QueryWrapper<ReceiverInfo>( ).eq("receiverId", orderInfo.getReceiverId( )));

        ReceiverInfo order = receiverMapper.selectOne(new QueryWrapper<ReceiverInfo>( ).eq("receiverId", receiverId));
        if (order.getStatu( ) != 0) {
            order.setStatu(0);
        }
        receiverMapper.update(order, new QueryWrapper<ReceiverInfo>( ).eq("receiverId", order.getReceiverId( )));

        return ServerResponse.success( );
    }

}
