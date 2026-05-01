package com.acme.cargotrak.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.acme.cargotrak.util.Constants;

public class AdminLogsAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest req, HttpServletResponse res) throws Exception {
        File dir = new File(Constants.LOGS_DIR);
        File[] files = dir.exists() ? dir.listFiles() : null;
        List names = new ArrayList();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) names.add(files[i].getName());
            }
        }
        req.setAttribute("logFiles", names);
        return mapping.findForward("success");
    }
}
