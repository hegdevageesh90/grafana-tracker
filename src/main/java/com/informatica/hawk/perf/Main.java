package com.informatica.hawk.perf;

import java.io.*;

import com.informatica.hawk.perf.service.PanelExporterService;

public class Main {
    public static void main(String[] args) throws IOException {
        PanelExporterService panelExporterService = new PanelExporterService();
        String from = "now-12h";
        String to = "now";
        String dashboardId = "e00bb90a-054b-4604-a12c-1b48cbf6db74";
        String panelId = "1";
        panelExporterService.exportAndSave(dashboardId, panelId, from, to);
    }
}