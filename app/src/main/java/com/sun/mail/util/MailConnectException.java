package com.sun.mail.util;

import javax.mail.MessagingException;

/* JADX INFO: loaded from: classes2.dex */
public class MailConnectException extends MessagingException {
    private static final long serialVersionUID = -3818807731125317729L;
    private int cto;
    private String host;
    private int port;

    public MailConnectException(SocketConnectException socketConnectException) {
        super("Couldn't connect to host, port: " + socketConnectException.getHost() + ", " + socketConnectException.getPort() + "; timeout " + socketConnectException.getConnectionTimeout() + (socketConnectException.getMessage() != null ? "; " + socketConnectException.getMessage() : ""));
        this.host = socketConnectException.getHost();
        this.port = socketConnectException.getPort();
        this.cto = socketConnectException.getConnectionTimeout();
        setNextException(socketConnectException.getException());
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public int getConnectionTimeout() {
        return this.cto;
    }
}
