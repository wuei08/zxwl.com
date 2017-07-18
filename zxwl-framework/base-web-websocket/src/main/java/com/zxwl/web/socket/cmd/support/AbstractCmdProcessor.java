package com.zxwl.web.socket.cmd.support;

import com.zxwl.web.bean.po.user.User;
import com.zxwl.web.core.session.HttpSessionManager;
import com.zxwl.web.socket.cmd.CmdProcessor;
import com.zxwl.web.socket.message.WebSocketMessageManager;
import com.zxwl.web.socket.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by zhouhao on 16-5-30.
 */
public abstract class AbstractCmdProcessor implements CmdProcessor {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected HttpSessionManager httpSessionManager;
    protected WebSocketMessageManager webSocketMessageManager;

    @Autowired
    public void setHttpSessionManager(HttpSessionManager httpSessionManager) {
        this.httpSessionManager = httpSessionManager;
    }

    @Autowired
    public void setWebSocketMessageManager(WebSocketMessageManager webSocketMessageManager) {
        this.webSocketMessageManager = webSocketMessageManager;
    }

    public User getUser(WebSocketSession socketSession) {
        return SessionUtils.getUser(socketSession, httpSessionManager);
    }
}
