package com.fh.pay.biz;

import com.fh.common.ServerResponse;
import com.fh.pay.po.PayLog;

public interface IPayService {
    ServerResponse createNative(PayLog payLog);

    ServerResponse queryPayStatu(PayLog payLog);


}
