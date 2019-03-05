//package com.starsgroupchina.credit.server.client;
//
//import feign.codec.Encoder;
//import feign.form.spring.SpringFormEncoder;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Scope;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@FeignClient(name = "file-server", url = "${file.server:}", configuration = FileClient.MultipartSupportConfig.class)
//public interface FileClient {
//    /**
//     * 上传图片至TFS
//     */
//    @RequestMapping(value = "/file", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    String upload(@RequestPart("file") MultipartFile file, @RequestParam("appKey") String appKey, @RequestHeader(value = "Content-Type") String contentType);
//
//    @RequestMapping(value = "/file/byte", method = RequestMethod.POST, consumes =MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    String uploadByByte(@RequestBody byte[] buff,@RequestParam("fileName") String fileName, @RequestParam("appKey") String appKey);
//
//    @RequestMapping(value = "/file/{fileKey}", method = RequestMethod.DELETE)
//    String delete(@PathVariable(value = "fileKey") String fileKey);
//
////    @RequestMapping(value = "/file/{fileKey}/{appKey}", method = RequestMethod.GET)
////    ResponseEntity<byte[]> get(@PathVariable(value = "fileKey") String fileKey,@PathVariable(value = "appKey") String appKey);
//
//    class MultipartSupportConfig {
//        @Bean
//        @Scope("prototype")
//        public Encoder feignFormEncoder() {
//            return new SpringFormEncoder();
//        }
//    }
//
//}
