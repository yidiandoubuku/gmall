package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.BaseSaleAttr;
import com.atguigu.gmall.SpuInfo;
import com.atguigu.gmall.inter.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class ManageController {

    @Reference
    ManageService manageService;

    @RequestMapping("index")
    public String manage(){
        return "index";
    }

    @RequestMapping("attrListPage")
    public String attrListPage(){
        return "attrListPage";
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> getBaseSaleAttrList(){
        return   manageService.getBaseSaleAttrList();
    }

    @RequestMapping(value = "saveSpuInfo",method = RequestMethod.POST)
    @ResponseBody
    public  String saveSpuInfo(SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);
        return  "success";
    }



}
