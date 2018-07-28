package com.atguigu.gmall.inter;

import com.atguigu.gmall.UserAddress;

import java.util.List;

public interface UserAddressService {
    public List<UserAddress> getUserAddressList(String userId);
}
