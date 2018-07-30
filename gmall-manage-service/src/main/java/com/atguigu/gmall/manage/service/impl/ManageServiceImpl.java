package com.atguigu.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.*;
import com.atguigu.gmall.inter.ManageService;
import com.atguigu.gmall.manage.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class ManageServiceImpl implements ManageService {
    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    SpuInfoMapper spuInfoMapper;

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private  SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private  SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuImageMapper skuImageMapper;
    @Autowired
    SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public List<BaseCatalog1> getCatalog1() {
        List<BaseCatalog1> baseCatalog1s = baseCatalog1Mapper.selectAll();
        return baseCatalog1s;
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {
        BaseCatalog2 baseCatalog2=new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);
        List<BaseCatalog2> baseCatalog2s = baseCatalog2Mapper.select(baseCatalog2);
        return baseCatalog2s;
    }

    @Override
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        BaseCatalog3 baseCatalog3=new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);

        List<BaseCatalog3> baseCatalog3s = baseCatalog3Mapper.select(baseCatalog3);

        return baseCatalog3s;
    }

    @Override
    public List<BaseAttrInfo> getAttrList(String catalog3_id) {
        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.selectAttrInfoList(Long.parseLong(catalog3_id));
        return baseAttrInfoList;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {

        if(baseAttrInfo.getId()!=null && baseAttrInfo.getId().length()>0){
            baseAttrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        }else{
            if(baseAttrInfo.getId()==null || baseAttrInfo.getId().length()==0){
                baseAttrInfo.setId(null);
            }
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValue);

        if(baseAttrInfo.getAttrValueList()!=null&&baseAttrInfo.getAttrValueList().size()>0) {
            for (BaseAttrValue attrValue : baseAttrInfo.getAttrValueList()) {
                //防止主键被赋上一个空字符串
                if (attrValue.getId().length() == 0) {
                    attrValue.setId(null);
                }
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insertSelective(attrValue);
            }

        }
    }

    @Override
    public BaseAttrInfo getAttrInfo(String attrId) {
        //平台属性名
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);

        //平台属性值
        BaseAttrValue baseAttrValue=new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        List<BaseAttrValue> attrValueList = baseAttrValueMapper.select(baseAttrValue);
        baseAttrInfo.setAttrValueList(attrValueList);
        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {
        List<SpuInfo> spuInfoList = spuInfoMapper.select(spuInfo);
        return spuInfoList;
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo){
        //保存主表 通过主键存在判断是修改 还是新增
        if(spuInfo.getId()==null||spuInfo.getId().length()==0){
            spuInfo.setId(null);
            spuInfoMapper.insertSelective(spuInfo);
        }else{
            spuInfoMapper.updateByPrimaryKey(spuInfo);
        }

        //保存图片信息 先删除 再插入
        Example spuImageExample=new Example(SpuImage.class);
        spuImageExample.createCriteria().andEqualTo("spuId",spuInfo.getId());
        spuImageMapper.deleteByExample(spuImageExample);

        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if(spuImageList!=null) {
            for (SpuImage spuImage : spuImageList) {
                if(spuImage.getId()!=null&&spuImage.getId().length()==0){
                    spuImage.setId(null);
                }
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }

        //保存销售属性信息  先删除 再插入
        Example spuSaleAttrExample=new Example(SpuSaleAttr.class);
        spuSaleAttrExample.createCriteria().andEqualTo("spuId",spuInfo.getId());
        spuSaleAttrMapper.deleteByExample(spuSaleAttrExample);

        //保存销售属性值信息  先删除 再插入
        Example spuSaleAttrValueExample=new Example(SpuSaleAttrValue.class);
        spuSaleAttrValueExample.createCriteria().andEqualTo("spuId",spuInfo.getId());
        spuSaleAttrValueMapper.deleteByExample(spuSaleAttrValueExample);

        //保存图片信息 先删除 再插入
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList!=null) {
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                if(spuSaleAttr.getId()!=null&&spuSaleAttr.getId().length()==0){
                    spuSaleAttr.setId(null);
                }
                spuSaleAttr.setSpuId(spuInfo.getId());
                spuSaleAttrMapper.insertSelective(spuSaleAttr);
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                    if(spuSaleAttrValue.getId()!=null&&spuSaleAttrValue.getId().length()==0){
                        spuSaleAttrValue.setId(null);
                    }
                    spuSaleAttrValue.setSpuId(spuInfo.getId());
                    spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                }
            }

        }
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.selectSpuSaleAttrList(Long.parseLong(spuId));
        return spuSaleAttrList;
    }

    @Override
    public List<SpuImage> getSpuImageList(String spuId) {
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);
        return spuImageMapper.select(spuImage);
    }

    @Override
    public void saveSkuInfo(SkuInfo skuInfo){
        if(skuInfo.getId()==null||skuInfo.getId().length()==0){
            skuInfo.setId(null);
            skuInfoMapper.insertSelective(skuInfo);
        }else {
            skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
        }


        Example example=new Example(SkuImage.class);
        example.createCriteria().andEqualTo("skuId",skuInfo.getId());
        skuImageMapper.deleteByExample(example);

        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuInfo.getId());
            if(skuImage.getId()!=null&&skuImage.getId().length()==0) {
                skuImage.setId(null);
            }
            skuImageMapper.insertSelective(skuImage);
        }


        Example skuAttrValueExample=new Example(SkuAttrValue.class);
        skuAttrValueExample.createCriteria().andEqualTo("skuId",skuInfo.getId());
        skuAttrValueMapper.deleteByExample(skuAttrValueExample);

        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuInfo.getId());
            if(skuAttrValue.getId()!=null&&skuAttrValue.getId().length()==0) {
                skuAttrValue.setId(null);
            }
            skuAttrValueMapper.insertSelective(skuAttrValue);
        }


        Example skuSaleAttrValueExample=new Example(SkuSaleAttrValue.class);
        skuSaleAttrValueExample.createCriteria().andEqualTo("skuId",skuInfo.getId());
        skuSaleAttrValueMapper.deleteByExample(skuSaleAttrValueExample);

        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            skuSaleAttrValue.setId(null);
            skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
        }

    }



}
