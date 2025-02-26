package com.akd.ftp;

import com.akd.common.FtpConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FTPService {

    @Value("${BASE_DIR_UPLOAD}")
    private String baseDirUpload;

    @PostConstruct
    public void startServer() {
        FtpServer ftpServer;
        int PORT = FtpConstants.FTPPort;
        String USERNAME = FtpConstants.FTPUserName;
        String PASSWORD = FtpConstants.FTPPassWord;
        String nvis_root=baseDirUpload;
        try {
            // 用于创建server
            FtpServerFactory serverFactory = new FtpServerFactory();
            // 配置信息，如设监听的端口，设置IP过滤器
            ListenerFactory listenerFactory = new ListenerFactory();
            // 设置端口
            listenerFactory.setPort(PORT);
            // 如果五分钟还没任何操作，关闭连接
            // listenerFactory.setIdleTimeout(5*60*1000);

            // 设置匿名用户 但是对于稳健权限是只读

            // 设置用户名密码
            BaseUser user = new BaseUser();
            user.setName(USERNAME);
            user.setPassword(PASSWORD);

            // 设置PC端登录后可访问的根目录
            user.setHomeDirectory(nvis_root);

            // 授予用户写权限
            List<Authority> authorities = new ArrayList<Authority>();
            authorities.add(new WritePermission());
            user.setAuthorities(authorities);
            serverFactory.getUserManager().save(user);

            // 创建并监听网络
            serverFactory.addListener("default", listenerFactory.createListener());

            // 创建服务
            ftpServer = serverFactory.createServer();

            // 开启服务
            ftpServer.start();

        } catch (Exception e) {
            log.error("ftpServer start error: ", e);
        }
    }
}
