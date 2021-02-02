package com.yuqiong.college.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LENOVO
 * @version 1.0
 * @date 2021/2/1
 */
@Data
public class ResultData {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private ResultData() {
    }

    public static ResultData ok() {
        ResultData r = new ResultData();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static ResultData error() {
        ResultData r = new ResultData();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public ResultData success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public ResultData message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResultData code(Integer code) {
        this.setCode(code);
        return this;
    }

    public ResultData data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public ResultData data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }


}
