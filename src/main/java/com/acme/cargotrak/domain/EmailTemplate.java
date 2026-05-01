package com.acme.cargotrak.domain;

import java.io.Serializable;

public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer templateId;
    private String templateCode;
    private String subject;
    private String body;

    public Integer getTemplateId() { return templateId; }
    public void setTemplateId(Integer templateId) { this.templateId = templateId; }
    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
}
