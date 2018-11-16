package com.yunboot.common.log;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LogInfo
{
    // 失败
    public static final int FAILED = 1;

    // 类名
    private String clsName;
    // 方法名
    private String methodName;
    // 请求参数
    private String params;
    // 返回值
    private String result;
    // 调用花费时间 ms
    private Long costTime;
    // 其他信息
    private String remark;

    // 成功状态。0成功，1失败
    private int status;
    // 错误码
    private int errorCode;
    // 错误消息
    private String errorMsg;

    // 请求时间
    private String requestTime;

    // 响应时间
    private String responseTime;
    // 网络请求信息
    private String serverIp;
    private String serverHost;
    private int serverPort;

    private String clientIp;
    private String clientHost;
    private int clientPort;
    private String requestUri;
}
