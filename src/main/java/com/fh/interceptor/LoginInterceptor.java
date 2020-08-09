package com.fh.interceptor;

<<<<<<< HEAD

import com.fh.changLiang.ChangLiang;
import com.fh.model.Member;
import com.fh.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User user = (User) request.getSession( ).getAttribute(ChangLiang.CURRENT_USER);
        if(user !=null){
            return true;
        }else{
            response.setContentType("application/json;charset=utf-8");
            String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNotEmpty(header) && header.equals("XMLHttpRequest")){
             Map<String,Object> result= new HashMap<String,Object>();
                result.put("code",12345);
                ServletOutputStream outputStream = response.getOutputStream( );
                outputStream.println(request.toString());
            }else {
                response.sendRedirect("/userLogin.jsp");
            }
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

=======
import com.alibaba.fastjson.JSONObject;
import com.fh.annotation.Check;
import com.fh.common.Const;
import com.fh.common.Constant;
import com.fh.common.Md5Utils;
import com.fh.common.ResponseEnum;
import com.fh.exception.GlobalException;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import com.fh.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "token,content-type,idenToken");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
        //处理 options 请求
        String reqMethod = request.getMethod( );
        if ("options".equalsIgnoreCase(reqMethod)) {
            return false;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod( );
        if (!method.isAnnotationPresent(Check.class)) {
            return true;
        }
        //判断 header头信息 是否存在     可以自定义 异常
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_NULL);
        }
        // 存在不一定就正确 验证头信息是否完整  分割头信息
        String[] headArr = token.split("\\.");
        if (headArr.length != 2) {
            throw new GlobalException(ResponseEnum.HEADER_IS_All);
        }

        //将数据装换为 json数据
        // 验证数据是否被篡改  首先获取数据 获取签名  然后生成新的签名  判断这俩个签名是否一致
        String memberBase64Data = headArr[0];
        String singBase64 = headArr[1];
        String newSign = Md5Utils.sing(memberBase64Data, Const.SECRET);  //新的签名
        String newSignBase64 = Base64.getEncoder( ).encodeToString(newSign.getBytes( ));

        if (!singBase64.equals(newSignBase64)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_DATA);
        }

        // 验证 过期时间是否过期
        String base64MerberData = new String(Base64.getDecoder( ).decode(memberBase64Data), "utf-8");
        MemberVo memberVo = JSONObject.parseObject(base64MerberData, MemberVo.class);
        Long id = memberVo.getId( );
        String uuid = memberVo.getUuid( );
        boolean exist = RedisUtils.exist(KeyUtils.memberKey(id, uuid));
        if (!exist) {
            throw new GlobalException(ResponseEnum.EXIST_IS_NULL);
        }
        //如果过期时间不过期则 重新续命  意思是指 从新设置过期时间
        RedisUtils.setEx(KeyUtils.memberKey(id, uuid), "1", KeyUtils.LOG_EXPIRE);
        // 最后验证都验证成功后 放到 request中
        request.setAttribute(Constant.LOGIN_MERMER_INFO, memberVo);
        return true;
>>>>>>> add shop-api
    }
}
