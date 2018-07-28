package com.atguigu.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.UserAddress;
import com.atguigu.gmall.inter.UserAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {

    //    @Autowired 改成从zk中取得
    @Reference
    private UserAddressService userAddressService;

    @RequestMapping("trade")
    @ResponseBody
    public List<UserAddress> trade(HttpServletRequest request){
        String userId = request.getParameter("userId");
//        request.setAttribute("admin","范冰冰");
        List<UserAddress> userAddressList = userAddressService.getUserAddressList(userId);
        return userAddressList;
    }

}