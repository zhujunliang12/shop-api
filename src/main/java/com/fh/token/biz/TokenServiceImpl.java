package com.fh.token.biz;

import com.fh.common.ServerResponse;
import com.fh.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service("tokenService")
public class TokenServiceImpl implements ITokenService {


    @Override
    public ServerResponse createToken() {
        //根据uuid生成token 唯一标识
        String token = UUID.randomUUID( ).toString( ).replace("-", "");
        //放入到 redis 缓存中
        RedisUtils.setKey(token,token);
        return ServerResponse.success(token);
    }
}
