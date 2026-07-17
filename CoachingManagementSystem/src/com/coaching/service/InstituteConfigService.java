package com.coaching.service;

import com.coaching.dao.InstituteConfigDAO;
import com.coaching.model.InstituteConfig;
import java.sql.*;

public class InstituteConfigService {
    private final InstituteConfigDAO dao = new InstituteConfigDAO();

    public InstituteConfig getConfig() throws SQLException { return dao.getConfig(); }
    public void updateConfig(InstituteConfig c) throws SQLException { dao.updateConfig(c); }
    public void updateLogo(int id, String logoPath) throws SQLException { dao.updateLogo(id, logoPath); }
}
