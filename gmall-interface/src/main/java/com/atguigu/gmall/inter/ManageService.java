package com.atguigu.gmall.inter;

import com.atguigu.gmall.*;

import java.util.List;

public interface ManageService {

    List<BaseCatalog1> getCatalog1();

    List<BaseCatalog2> getCatalog2(String catalog1Id);

    List<BaseCatalog3> getCatalog3(String catalog2Id);

    List<BaseAttrInfo> getAttrList(String catalog3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    BaseAttrInfo getAttrInfo(String attrId);

    List<SpuInfo> getSpuInfoList(SpuInfo spuInfo);

    List<BaseSaleAttr> getBaseSaleAttrList();

    void saveSpuInfo(SpuInfo spuInfo);

    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    List<SpuImage> getSpuImageList(String spuId);

   public void saveSkuInfo(SkuInfo skuInfo);
}
