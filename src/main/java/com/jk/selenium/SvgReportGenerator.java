package com.jk.selenium;

import java.io.FileWriter;
import java.io.IOException;

public class SvgReportGenerator {
    public static void main(String[] args) {
        String svgXml = "<svg xmlns='http://www.w3.org/2000/svg' width='200' height='200'>" +
                "<circle cx='100' cy='100' r='80' stroke='green' stroke-width='4' fill='yellow' />" +
                "</svg>";

        String htmlContent = "<html>\n" +
                "<head><title>SVG Report</title></head>\n" +
                "<body>\n" +
                "<h2>Test Result with SVG</h2>\n" +
                "<div id='svgContainer'></div>\n" +
                "<script>\n" +
                "  const svgXml = `" + svgXml + "`;\n" +
                "  const parser = new DOMParser();\n" +
                "  const svgDoc = parser.parseFromString(svgXml, 'image/svg+xml');\n" +
                "  const svgElement = svgDoc.documentElement;\n" +
                "  document.getElementById('svgContainer').appendChild(svgElement);\n" +
                "</script>\n" +
                "</body>\n</html>";

        try (FileWriter writer = new FileWriter("test-report.html")) {
            writer.write(htmlContent);
            System.out.println("✅ test-report.html created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
