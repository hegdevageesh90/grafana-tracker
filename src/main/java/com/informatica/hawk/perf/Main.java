package com.informatica.hawk.perf;

import java.io.*;

import com.informatica.hawk.perf.service.PanelExporterService;

public class Main {
    public static void main(String[] args) throws IOException {
        PanelExporterService panelExporterService = new PanelExporterService();
        String from = "now-12h";
        String to = "now";
        String dashboardId = "a2f9dc1d-0f8b-4061-bda4-fbff860bbb9e";
        String panelId = "1";
        panelExporterService.exportAndSave(dashboardId, panelId, from, to);
    }
}