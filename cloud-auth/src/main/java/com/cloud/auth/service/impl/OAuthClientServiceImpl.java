//package com.cloud.auth.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.cloud.auth.entity.OAuthClient;
//import com.cloud.auth.mapper.OAuthClientMapper;
//import com.cloud.auth.service.OAuthClientService;
//import com.cloud.common.result.Result;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * OAuth客户端服务实现类
// */
//@Service
//public class OAuthClientServiceImpl  implements OAuthClientService {
//
//    @Override
//    public OAuthClient getByClientId(String clientId) {
//        return baseMapper.selectOne(
//            new LambdaQueryWrapper<OAuthClient>().eq(OAuthClient::getClientId, clientId)
//        );
//    }
//
//    @Override
//    public Result<List<OAuthClient>> getAllEnabledClients() {
//        List<OAuthClient> clients = baseMapper.selectList(
//            new LambdaQueryWrapper<OAuthClient>().eq(OAuthClient::getStatus, 1)
//        );
//        return Result.success(clients);
//    }
//
//    @Override
//    public boolean validateClientSecret(String clientId, String clientSecret) {
//        OAuthClient client = getByClientId(clientId);
//        return client != null && client.getStatus() == 1 && client.getClientSecret().equals(clientSecret);
//    }
//}