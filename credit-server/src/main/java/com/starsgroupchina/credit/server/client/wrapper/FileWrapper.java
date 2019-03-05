//package com.starsgroupchina.credit.server.client.wrapper;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.starsgroupchina.common.response.SimpleResponse;
//import com.starsgroupchina.credit.server.client.FileClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//@Slf4j
//@Component
//public class FileWrapper {
//
//    @Autowired
//    private FileClient fileClient;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    /**
//     * 上传文件，返回fileKey
//     */
//    public String upload(MultipartFile file, String appKey) {
//        SimpleResponse simpleResponse = null;
//        String result = null;
//        try {
//            result = fileClient.upload(file, appKey, "multipart/form-data;boundary=----WebKitFormBoundarywgvhbu666Ff666f6");
//            simpleResponse = objectMapper.readValue(result, SimpleResponse.class);
//        } catch (Exception e) {
//            log.error("文件上传失败，错误信息为:{}",e);
//            return null;
//        }
//        if (simpleResponse.getStatus() != HttpStatus.OK.value()) {
//            log.error("文件上传失败，错误信息为:{}",result);
//            return null;
//        }
//        String fileKey = simpleResponse.getData().toString();
//        return fileKey;
//    }
//
//    /**
//     * 上传文件 字节流方式，返回fileKey
//     */
//    public String uploadByByte(byte[] buff, String fileName, String appKey) {
//        SimpleResponse simpleResponse = null;
//        String result = null;
//        try {
//            result = fileClient.uploadByByte(buff,fileName, appKey);
//            simpleResponse = objectMapper.readValue(result, SimpleResponse.class);
//        } catch (Exception e) {
//            log.error("文件上传失败，错误信息为:{}",e);
//            return null;
//        }
//        if (simpleResponse.getStatus() != HttpStatus.OK.value()) {
//            log.error("文件上传失败，错误信息为:{}",result);
//            return null;
//        }
//        String fileKey = simpleResponse.getData().toString();
//        return fileKey;
//    }
//
//    public String delete(String fileKey){
//        String result = null;
//        SimpleResponse simpleResponse = null;
//        try {
//            result = fileClient.delete(fileKey);
//            simpleResponse = objectMapper.readValue(result, SimpleResponse.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (simpleResponse.getStatus() != HttpStatus.OK.value()) {
//            log.error("文件删除失败");
//        }
//        return result;
//    }
//}
