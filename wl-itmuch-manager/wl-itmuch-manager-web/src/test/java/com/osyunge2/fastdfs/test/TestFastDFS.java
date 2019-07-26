package com.osyunge2.fastdfs.test;

import com.osyunge2.utils.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class TestFastDFS {
    @Test
    public void testUploadFile() throws IOException, MyException {
          //D:\\mvnssmweb\\wl-itmuch-manager\\wl-itmuch-manager-web\\src\\main\\resources\\propertie\\client.conf
        ClientGlobal.init("D:\\mvnssmweb\\wl-itmuch-manager\\wl-itmuch-manager-web\\src\\main\\resources\\properties\\client.conf");
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
        String[] strings=storageClient.upload_file("F:\\BaiduNetdiskDownload\\东方project_出版物_官方文档\\出版物\\东方儚月抄　～ 月のイナバと地上の因幡\\猜猜因幡我是谁 第04话\\01.jpg","jpg",null);
       for (int i=0;i<strings.length;i++){
           System.out.println(strings[i]);
       }


     }
     @Test
        public void test() throws Exception {
         FastDFSClient client=new FastDFSClient("D:\\mvnssmweb\\wl-itmuch-manager\\wl-itmuch-manager-web\\src\\main\\resources\\properties\\client.conf");
         String jpg=client.uploadFile("F:\\BaiduNetdiskDownload\\东方project_出版物_官方文档\\出版物\\东方儚月抄　～ 月のイナバと地上の因幡\\猜猜因幡我是谁 第04话\\01.jpg","jpg",null);
         System.out.println(jpg);
    }

}
