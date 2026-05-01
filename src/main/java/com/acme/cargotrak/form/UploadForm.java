package com.acme.cargotrak.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UploadForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private FormFile file;
    private String fileType; // CSV, EDI204, EDI214

    public FormFile getFile() { return file; }
    public void setFile(FormFile file) { this.file = file; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
}
