package org.jiumao.wechatMall.api.resource;


import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.ArrayUtils;
import org.jiumao.common.constants.LoggerName;
import org.jiumao.common.domain.ErrorCode;
import org.jiumao.common.utils.JsonUtil;
import org.jiumao.mall.OAuth2.domain.Authorize;
import org.jiumao.mall.domain.Authentication;
import org.jiumao.remote.client.NettyRemotingClient;
import org.jiumao.remote.exception.RemotingConnectException;
import org.jiumao.remote.exception.RemotingSendRequestException;
import org.jiumao.remote.exception.RemotingTimeoutException;
import org.jiumao.remote.service.RemotingCommand;
import org.jiumao.service.RPCServices;
import org.jiumao.wechatMall.common.domain.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Path("/oauth2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    private static final Logger log = LoggerFactory.getLogger(LoggerName.Status);


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(@QueryParam("msg") @DefaultValue("hello world") String msg) {
        return msg;
    }


    @GET
    @Path("authorize")
    public Response authorize(Authorize auth) throws RemotingConnectException, RemotingSendRequestException,
            RemotingTimeoutException, InterruptedException {
        String entity = ErrorCode.NOT_AUTHED.toString();

        NettyRemotingClient client = RPCServices.getAuthService();
        RemotingCommand request = RemotingCommand.createRequestCommand();
        byte[] body = JsonUtil.toBytes(auth);
        request.setBody(body);
        request.getExtFields().put("action", "authorize");
        RemotingCommand responce = client.invokeSync(request);
        if (ArrayUtils.isNotEmpty(responce.getBody())) {
            entity = JsonUtil.toJson(responce.getBody());
        }
        return ResponseUtil.defaultRes(entity);
    }


    @POST
    @Path("token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response token(Authentication auth) throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, InterruptedException {
        String entity = ErrorCode.NOT_AUTHED.toString();
        NettyRemotingClient client = RPCServices.getAuthService();
        RemotingCommand request = RemotingCommand.createRequestCommand();
        byte[] body = JsonUtil.toBytes(auth);
        request.setBody(body);
        request.getExtFields().put("action", "token");
        RemotingCommand responce = client.invokeSync(request);
        if (ArrayUtils.isNotEmpty(responce.getBody())) {
            entity = JsonUtil.toJson(responce.getBody());
        }
        return ResponseUtil.defaultRes(entity);

    }

}
