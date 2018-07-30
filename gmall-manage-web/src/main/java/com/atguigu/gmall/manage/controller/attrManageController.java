package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.*;
import com.atguigu.gmall.inter.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class attrManageController {
    @Reference
    ManageService manageService;


    @RequestMapping("getCatalog1")
    @ResponseBody
    public List<BaseCatalog1> getCatalog1(){
        List<BaseCatalog1> catalog1 = manageService.getCatalog1();
        return catalog1;
    }


    @RequestMapping("getCatalog2")
    @ResponseBody
    public List<BaseCatalog2> getCatalog2(@RequestParam Map<String,String> map){
        String catalog1Id =   map.get("catalog1Id") ;
        List<BaseCatalog2> catalog2 = manageService.getCatalog2(catalog1Id);
        return catalog2;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody
    public List<BaseCatalog3> getCatalog3(@RequestParam Map<String,String> map){
        String catalog2Id = map.get("catalog2Id");
        List<BaseCatalog3> catalog3 = manageService.getCatalog3(catalog2Id);
        return catalog3;
    }

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<BaseAttrInfo> getAttrInfoList(@RequestParam Map<String,String> map){
        String catalog3Id =   map.get("catalog3Id") ;
        List<BaseAttrInfo> attrList = manageService.getAttrList(catalog3Id);
        return attrList;

    }

    @RequestMapping(value = "saveAttrInfo",method = RequestMethod.POST)
    @ResponseBody
    public String saveAttrInfo(BaseAttrInfo baseAttrInfo){
        manageService.saveAttrInfo(baseAttrInfo);
        return "success";
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValueList(String attrId){
        BaseAttrInfo attrInfo = manageService.getAttrInfo(attrId);
        return attrInfo.getAttrValueList();
    }
    @RequestMapping("spuSaleAttrList")
    @ResponseBody
    public List<SpuSaleAttr> getSpuSaleAttrList(HttpServletRequest httpServletRequest){
        String spuId = httpServletRequest.getParameter("spuId");
        List<SpuSaleAttr> spuSaleAttrList = manageService.getSpuSaleAttrList(spuId);

        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            Map map=new HashMap();
            map.put("total",spuSaleAttrValueList.size());
            map.put("rows",spuSaleAttrValueList);
            // String spuSaleAttrValueJson = JSON.toJSONString(map);
            spuSaleAttr.setSpuSaleAttrValueJson(map);
        }
        return spuSaleAttrList;
    }

}
