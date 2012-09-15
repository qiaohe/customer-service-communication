package com.threeti.ics.server.domain.socketserver.server;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import static org.apache.mina.filter.codec.textline.LineDelimiter.WINDOWS;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 05/09/12
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "socketServer")
public final class Server {
    private static final int PORT = 8888;
    private static final String CODEC_FILTER_NAME = "codec";
    private static final String LOGGING_FILTER_NAME = "logging";
    private static final String DEFAULT_CHART_SET = "UTF-8";
    private static final int TEXT_LINE_CODEC_MAX_LENGTH = 2048 * 4;

    private Server() {
    }

    private ProtocolCodecFactory creteProtocolCodecFactory() {
        TextLineCodecFactory result = new TextLineCodecFactory(Charset.forName(DEFAULT_CHART_SET), WINDOWS.getValue(), WINDOWS.getValue());
        result.setDecoderMaxLineLength(TEXT_LINE_CODEC_MAX_LENGTH);
        result.setEncoderMaxLineLength(TEXT_LINE_CODEC_MAX_LENGTH);
        return result;
    }

    @PostConstruct
    public void init() throws IOException {
        SocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast(CODEC_FILTER_NAME, new ProtocolCodecFilter(creteProtocolCodecFactory()));
        acceptor.getSessionConfig().setKeepAlive(true);
        acceptor.getSessionConfig().setTcpNoDelay(false);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.setHandler(new ServerHandler());
        acceptor.bind(new InetSocketAddress(PORT));
    }
}
