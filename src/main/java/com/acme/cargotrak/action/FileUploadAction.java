package com.acme.cargotrak.action;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.acme.cargotrak.domain.User;
import com.acme.cargotrak.form.UploadForm;
import com.acme.cargotrak.ingestion.IngestResult;
import com.acme.cargotrak.service.IngestionService;
import com.acme.cargotrak.util.Constants;
import com.acme.cargotrak.util.FileUtil;
import com.acme.cargotrak.util.SpringContextHolder;

public class FileUploadAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        UploadForm uf = (UploadForm) form;
        FormFile file = uf.getFile();
        if (file == null || file.getFileSize() == 0) {
            req.setAttribute("error", "no file");
            return mapping.findForward("success");
        }
        FileUtil.ensureDir(Constants.UPLOADS_DIR);
        File dest = new File(Constants.UPLOADS_DIR, System.currentTimeMillis() + "-" + file.getFileName());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dest);
            fos.write(file.getFileData());
        } finally {
            try { if (fos != null) fos.close(); } catch (Exception ex) {}
            file.destroy();
        }

        IngestionService ing = (IngestionService) SpringContextHolder.getBean("ingestionService");
        User u = currentUser(req);
        String email = u == null ? null : u.getEmail();
        IngestResult result;
        if ("EDI204".equalsIgnoreCase(uf.getFileType())) {
            result = ing.ingestEdi204(dest, email);
        } else if ("EDI214".equalsIgnoreCase(uf.getFileType())) {
            result = ing.ingestEdi214(dest, email);
        } else {
            result = ing.ingestCsvManifest(dest, email);
        }
        req.setAttribute("result", result);
        req.setAttribute("uploaded", dest.getName());
        cargo().auditAction(currentUsername(req), "UPLOAD", "File", dest.getName(),
                            "type=" + uf.getFileType() + " accepted=" + result.getAccepted() + " rejected=" + result.getRejected());
        return mapping.findForward("success");
    }
}
