package com.imooc.bilibili.domain;

import lombok.Data;

/**
 * 公共返回类
 * @param <T>
 */
@Data
public class JsonResponse<T>  {
        private String code;

        private String msg;

        private T data;

        public JsonResponse(String code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public JsonResponse(T data){
            this.data = data;
            msg = "成功";
            code = "0";
        }
        //返回成功的状态码 不涉及data数据
        public static JsonResponse<String> success(){
            return new JsonResponse<>(null);
        }
        //返回成功的状态码 并携带json数据
        public static JsonResponse<String> success(String data){
            return new JsonResponse<>(data);
        }
        //失败并修改为固定的状态码
        public static JsonResponse<String> fail(){
            return new JsonResponse<>("1", "失败");
        }
        // 携带错误信息
        public static JsonResponse<String> fail(String code, String msg){
            return new JsonResponse<>(code, msg);
        }



}
