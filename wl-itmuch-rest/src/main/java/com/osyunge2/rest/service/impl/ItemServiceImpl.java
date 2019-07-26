package com.osyunge2.rest.service.impl;

import com.osyunge2.dataobject.FCResult;
import com.osyunge2.mapper.TbItemDescMapper;
import com.osyunge2.mapper.TbItemMapper;
import com.osyunge2.mapper.TbItemParamItemMapper;
import com.osyunge2.pojo.*;
import com.osyunge2.rest.dao.JedisClient;
import com.osyunge2.rest.service.ItemService;
import com.osyunge2.utils.HttpClientUtil;
import com.osyunge2.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParaItemMapper;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;
    @Override
    public FCResult getBaseItemInfo(Long itemId) {
        try{
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
            //判断是否有值
            if(!StringUtils.isBlank(json)){
                //把json转换成java对象
                TbItem item= JsonUtils.jsonToPojo(json,TbItem.class);
                return FCResult.ok(item);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        TbItem item=tbItemMapper.selectByPrimaryKey(itemId);
        try{
            //把商品信息写入缓存
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base",JsonUtils.objectToJson(item));
            //设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base",REDIS_ITEM_EXPIRE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return FCResult.ok(item);
    }

    @Override
    public FCResult getItemDesc(Long itemId) {
        //添加缓存
        try{
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":desc");
            //判断是否有值
            if(!StringUtils.isBlank(json)) {
                //把json转换成java对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return FCResult.ok(itemDesc);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        //创建查询条件
        TbItemDesc itemDesc=tbItemDescMapper.selectByPrimaryKey(itemId);
        try{
            //把商品信息写入缓存
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc",JsonUtils.objectToJson(itemDesc));
            //设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc",REDIS_ITEM_EXPIRE);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return FCResult.ok(itemDesc);
    }

    @Override
    public FCResult getItemParam(Long itemId) {
        //添加缓存
        try{
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json=jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":param");
            //判断是否有值
            if(!StringUtils.isBlank(json)){
                TbItemParamItem paramItem=JsonUtils.jsonToPojo(json,TbItemParamItem.class);
                return FCResult.ok(paramItem);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //根据商品id查询规格参数
        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria=example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list=itemParaItemMapper.selectByExampleWithBLOBs(example);
        if (list!=null&&list.size()>0) {
            TbItemParamItem paramItem = list.get(0);

            try {
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
                jedisClient.expire(REDIS_ITEM_KEY+";"+itemId+":param",REDIS_ITEM_EXPIRE);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return FCResult.ok(paramItem);
        }

        return FCResult.build(400,"无此商品规格");
    }

    @Override
    public TbItem getItemById(Long itemId) {
        try{
            //调用rest的服务查询商品基本信息
            String json= HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
            if(!StringUtils.isBlank(json)){
                FCResult result=FCResult.formatToPojo(json,TbItem.class);
                if (result.getStatus()==200){
                    TbItem item= (TbItem) result.getData();
                    return item;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemDescById(Long itemId) {
       try{
           //查询商品描述
           String json=HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
           //转换成Java对象
           FCResult result=FCResult.formatToPojo(json,TbItemDesc.class);
           if (result.getStatus()==200){
               TbItemDesc itemDesc= (TbItemDesc) result.getData();
               //取商品描述信息
               String re=itemDesc.getItemDesc();
               return re;
           }
       }
       catch (Exception e){
           e.printStackTrace();
       }

        return null;
    }

    @Override
    public String getItemParamById(Long itemId) {
        try{
            String json=HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
            //把json转换成java对象
            FCResult result=FCResult.formatToPojo(json,TbItemParamItem.class);
            if (result.getStatus()==200){
                TbItemParamItem itemParamItem=(TbItemParamItem)result.getData();
                String paramData=itemParamItem.getParamData();
                //生成html
                //把规格参数json数据转换成java对象
                List<Map> jsonList=JsonUtils.jsonToList(paramData,Map.class);
                StringBuffer sb=new StringBuffer();
                sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                sb.append("    <tbody>\n");
                for(Map m1:jsonList) {
                    sb.append("        <tr>\n");
                    sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                    sb.append("        </tr>\n");
                    List<Map> list2 = (List<Map>) m1.get("params");
                    for(Map m2:list2) {
                        sb.append("        <tr>\n");
                        sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                        sb.append("            <td>"+m2.get("v")+"</td>\n");
                        sb.append("        </tr>\n");
                    }
                }
                sb.append("    </tbody>\n");
                sb.append("</table>");
                //返回html片段
                return sb.toString();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
