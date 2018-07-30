package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.SkuInfo;
import com.atguigu.gmall.SpuImage;
import com.atguigu.gmall.inter.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class skuManageController  {
    @Reference
    private ManageService manageService;

    @RequestMapping("spuImageList")
    @ResponseBody
    public List<SpuImage> spuImageList(String spuId){
        return   manageService.getSpuImageList(spuId);
    }

    @RequestMapping(value = "saveSku",method = RequestMethod.POST)
    @ResponseBody
    public String saveSkuInfo(SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
        return "success";
    }
}
