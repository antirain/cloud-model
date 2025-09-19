//package com.cloud.auth.service;
//
//import com.cloud.auth.entity.OAuthClient;
//import com.cloud.common.result.Result;
//
//import java.util.List;
//
///**
// * OAuth客户端服务接口
// */
//public interface OAuthClientService {
//
//    /**
//     * 根据客户端ID获取客户端
//     */
//    OAuthClient getByClientId(String clientId);
//
//    /**
//     * 获取所有启用客户端
//     */
//    Result<List<OAuthClient>> getAllEnabledClients();
//
//    /**
//     * 验证客户端密钥
//     */
//    boolean validateClientSecret(String clientId, String clientSecret);
//}