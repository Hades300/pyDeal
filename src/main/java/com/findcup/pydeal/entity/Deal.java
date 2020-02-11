package com.findcup.pydeal.entity;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;

public class Deal {
    /**
     * Post id
     */
    private int pid;

    /**
     * Deal标题
     */
    private String title;

    /**
     * Deal内容
     */
    private String context;

    /**
     * Deal终结者
     */
    private long terminatorId;

    /**
     * Deal创建时间
     */
    private Timestamp createdAt;

    /**
     * Deal的过期时间
     */
    private Timestamp ddl;

    /**
     * Deal的发起者
     */
    private long uid;

    /**
     * Deal的状态
     */
    private String state;

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setTerminatorId(int terminatorId) {
        this.terminatorId = terminatorId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setDdl(Timestamp ddl) {
        this.ddl = ddl;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPid() {
        return pid;
    }

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public long getTerminatorId() {
        return terminatorId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getDdl() {
        return ddl;
    }

    public long getUid() {
        return uid;
    }

    public String getState() {
        return state;
    }

    /**
     * JSON TYPE
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pid\":")
                .append(pid);
        sb.append(",\"title\":\"")
                .append(title).append('\"');
        sb.append(",\"context\":\"")
                .append(context).append('\"');
        sb.append(",\"terminatorId\":")
                .append(terminatorId);
        sb.append(",\"createdAt\":\"")
                .append(createdAt).append('\"');
        sb.append(",\"ddl\":\"")
                .append(ddl).append('\"');
        sb.append(",\"uid\":")
                .append(uid);
        sb.append(",\"state\":")
                .append(state);
        sb.append('}');
        return sb.toString();
    }
}
