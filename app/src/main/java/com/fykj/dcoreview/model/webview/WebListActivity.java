package com.fykj.dcoreview.model.webview;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fykj.dcoreview.base.BaseListShowActivity;
import com.fykj.dcoreview.bean.ClazzBean;
import com.fykj.dcoreview.bean.ParcelableBean;
import com.fykj.dcoreview.utils.ToastUtils;

/**
 * Created by dengzh on 2018/4/18.
 */

public class WebListActivity extends BaseListShowActivity{

  //  private String webData = "<p style=\"line-height: 2em;\"><strong><span style=\"font-family:宋体\">牛犊</span></strong></p><p style=\"line-height: 2em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style=\"font-family:宋体\">犊牛是指</span>3<span style=\"font-family:宋体\">～</span>6<span style=\"font-family:宋体\">月龄之内以乳汁为主要营养来源的初生小牛。由于其消化特点和成牛有显著不同，因此，在饲养管理上也应有自己的特点，主要突出u201c三早u201d，把好u201c三关u201d，防好u201c两病u201d。</span></p><p style=\"line-height: 2em;\">(<span style=\"font-family:宋体\">一</span>)<span style=\"font-family:宋体\">早吃初乳母牛分娩后一周内所产的乳叫初乳。初乳中含有十分丰富的养分、各种抗体和溶菌酶，对小牛的健康成长十分有益。因此，小牛出生后，要在</span>30<span style=\"font-family:宋体\">分钟内让其吃到初乳，以确保小牛的健康生长。</span></p><p style=\"line-height: 2em;\">(<span style=\"font-family:宋体\">二</span>)<span style=\"font-family:宋体\">早期断奶自然哺乳一般让犊牛吃六个月的初乳，人工哺乳也要吃三个月的母乳。早期断奶一般指</span>1<span style=\"font-family:宋体\">～</span>2<span style=\"font-family:宋体\">月龄断奶。促进犊牛瘤胃的早期发育，从而提高犊牛的培育质量，提高犊牛的成活率，减少死亡损失。</span></p><p style=\"line-height: 2em;\">(<span style=\"font-family:宋体\">三</span>)<span style=\"font-family:宋体\">及早开食就是让犊牛尽量早一点吃上草料。犊牛一般在</span>10<span style=\"font-family:宋体\">日龄时出现反刍，</span>15<span style=\"font-family:宋体\">日龄就可以采食一点柔软的干草，</span>30<span style=\"font-family:宋体\">日龄时其胃肠机能已基本发育健全。生产中为促进犊牛的胃肠发育和机能健全，一般于</span>10<span style=\"font-family:宋体\">日龄前，就开始喂给易消化的麦麸、玉米粉。</span></p><p><br/></p>";
    private String url = "https://www.baidu.com/";

   // private String webData ="<html><head><meta http-equiv='Content-Type' content='text/html;charset=utf-8'/></head><body><form id ='pay_form' name='pay_form' action='https://gateway.95516.com/gateway/api/frontTransReq.do' method='post' accept-charset='UTF-8'><input type='hidden' name = 'bizType' id='bizType' value='000201'/><input type='hidden' name = 'backUrl' id='backUrl' value='ZX|http://144.255.17.145:8288/request/upacInternetBankNotify'/><input type='hidden' name = 'orderId' id='orderId' value='UPAC201808170930239000000000000000027323'/><input type='hidden' name = 'signature' id='signature' value='wkdytD3YfRcurrjJSYDVcH2al8481HX2CqIQjuoV+5jnW6vpDn2I3YjA9gCHJgSHvabSBwr1Lq43jUPlc/OHDdjeAdh0SbyGHXzM8iNlJP6uuyl3K79PNHg4bnitqAGHIvzJTpvHEWBafYa7+Ry6djCHvjN0BJrnb7X0kenEOjUnY2mYJHyMjdAkS+Hr3WNveDHmH2geqS34rHPIwV7Zr9e/472LrK/VAJJ9gbj30It7D6M5XttiN0dDc7l6K5FkE7z4lsj/Gb7eiAHlndxBESNf+bJ8+4/cXLRZzQkbiw3im+jI5aTniOv4PloWUIUPOkxhTile6ovzLhaOZben/Q=='/><input type='hidden' name = 'txnSubType' id='txnSubType' value='01'/><input type='hidden' name = 'merName' id='merName' value='湖南乾润华创电子商务有限公司'/><input type='hidden' name = 'txnType' id='txnType' value='01'/><input type='hidden' name = 'channelType' id='channelType' value='07'/><input type='hidden' name = 'frontUrl' id='frontUrl' value='https://cashier.sandpay.com.cn/gateway/api/order/notice/allChannelPay'/><input type='hidden' name = 'certId' id='certId' value='69525006230'/><input type='hidden' name = 'encoding' id='encoding' value='UTF-8'/><input type='hidden' name = 'acqInsCode' id='acqInsCode' value='48273320'/><input type='hidden' name = 'version' id='version' value='5.0.0'/><input type='hidden' name = 'merAbbr' id='merAbbr' value='湖南乾润华创电子'/><input type='hidden' name = 'accessType' id='accessType' value='1'/><input type='hidden' name = 'reqReserved' id='reqReserved' value='00000930'/><input type='hidden' name = 'txnTime' id='txnTime' value='20180817143948'/><input type='hidden' name = 'merId' id='merId' value='82755105699B005'/><input type='hidden' name = 'merCatCode' id='merCatCode' value='8999'/><input type='hidden' name = 'currencyCode' id='currencyCode' value='156'/><input type='hidden' name = 'signMethod' id='signMethod' value='01'/><input type='hidden' name = 'txnAmt' id='txnAmt' value='1'/></form></body><script type='text/javascript'>document.pay_form.submit() </script></html>";

    String webData = "<html><head><meta http-equiv='Content-Type' content='text/html;charset=utf-8'/></head><body><form id ='pay_form' name='pay_form' action='https://gateway.95516.com/gateway/api/frontTransReq.do' method='post' accept-charset='UTF-8'><input type='hidden' name = 'bizType' id='bizType' value='000201'/><input type='hidden' name = 'backUrl' id='backUrl' value='ZX|http://144.255.17.145:8288/request/upacInternetBankNotify'/><input type='hidden' name = 'orderId' id='orderId' value='UPAC201808170930345000000000000000028324'/><input type='hidden' name = 'signature' id='signature' value='gpM3HlrKFoGRQfK+/Rf2J99xP+s3B9HAW7T/TnlZv3Y52iB80ZQ3qDypitW+EoCJqNTQ2O63M6AhebeIljkLZd/e4Ene+0JkpGzGcw0JkiACldbyOU7iknYxO9NoUgtd1iKDF5SjtNsaQQjpSUVSu9XyLB/RArPCEpUqLJQzvVbQUcxs5MHNqaEv/SjYGTGt977YeEnNvQCCj8U8Ug1b6G5bioK6fuJbLD4FFBse5FvlzDQAK1ZVNIOrwoNqvVXNMCU5OdWd7Drsepp4AW9T8W3axjjKubtQ6UoGU46MZm4GJOk2LO3qTtD7HH3vm/7j7aGSlqkwM5HmSXsv9KslzQ=='/><input type='hidden' name = 'txnSubType' id='txnSubType' value='01'/><input type='hidden' name = 'merName' id='merName' value='湖南乾润华创电子商务有限公司'/><input type='hidden' name = 'txnType' id='txnType' value='01'/><input type='hidden' name = 'channelType' id='channelType' value='07'/><input type='hidden' name = 'frontUrl' id='frontUrl' value='https://cashier.sandpay.com.cn/gateway/api/order/notice/allChannelPay'/><input type='hidden' name = 'certId' id='certId' value='69525006230'/><input type='hidden' name = 'encoding' id='encoding' value='UTF-8'/><input type='hidden' name = 'acqInsCode' id='acqInsCode' value='48273320'/><input type='hidden' name = 'version' id='version' value='5.0.0'/><input type='hidden' name = 'merAbbr' id='merAbbr' value='湖南乾润华创电子'/><input type='hidden' name = 'accessType' id='accessType' value='1'/><input type='hidden' name = 'reqReserved' id='reqReserved' value='00000930'/><input type='hidden' name = 'txnTime' id='txnTime' value='20180817150545'/><input type='hidden' name = 'merId' id='merId' value='82755105699B005'/><input type='hidden' name = 'merCatCode' id='merCatCode' value='8999'/><input type='hidden' name = 'currencyCode' id='currencyCode' value='156'/><input type='hidden' name = 'signMethod' id='signMethod' value='01'/><input type='hidden' name = 'txnAmt' id='txnAmt' value='1'/></form></body><script type='text/javascript'>document.pay_form.submit() </script></html>";


    @Override
    protected void initUI() {
        tvTitle.setText("WebView示例");
    }

    @Override
    protected void initData() {

        addClazzBean("WebView Url",WebViewActivity.class);
        addClazzBean("WebView Data",WebViewActivity.class);
        addClazzBean("AgentWeb",AgentWebActivity.class);
        addClazzBean("AgentWeb JS交互",AgentWebActivity.class);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        WebViewActivity.goWebViewActivity(WebListActivity.this,"webView",url,"");
                        break;
                    case 1:
                        WebViewActivity.goWebViewActivity(WebListActivity.this,"webView","",webData);
                        break;
                    case 2:
                        AgentWebActivity.goAgentWebActivity(WebListActivity.this,"AgentWeb",url);
                        break;
                    case 3:
                        ToastUtils.showToast("进入此页面，将无法退出");

                        //AgentWebJSActivity.goAgentWebActivity(WebListActivity.this,"AgentWeb JS交互",url);
                        break;
                }
            }
        });

        mAdapter.notifyDataSetChanged();
    }
}
