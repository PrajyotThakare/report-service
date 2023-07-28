package com.prajyot.report.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;

@Service
public class ReportService {

	@Value("${ReportLocation}")
	private String path;
	
	public void generateReport(String reportFormat,Object list) throws JRException, FileNotFoundException {

		LocalDateTime today = LocalDateTime.now();
		String year = StringUtils.substring(today.toString(), 0 , 4);
		String month = StringUtils.substring(today.toString(), 5 , 7);
		String day = StringUtils.substring(today.toString(), 8 , 10);
		String time = StringUtils.substring(today.toString(), 11, 19).replace(':', '-');
		String location = path+"\\"+day+"-"+month+"-"+year;	
		File f1 = new File(location);
		f1.mkdir();
		
		File file = ResourceUtils.getFile("classpath:templates\\CityReport.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((Collection<?>) list);
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("CreatedBy", "Prajyot_Thakare");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, dataSource);
		
		if (reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, location + "\\CityReport-"+time+".html");
		} else if (reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, location + "\\CityReport-"+time+".pdf");
		} else if (reportFormat.equalsIgnoreCase("xlsx")) {
			JRXlsExporter exporter = new JRXlsExporter();
			final SimpleTextReportConfiguration txtConfiguration = new SimpleTextReportConfiguration();
			txtConfiguration.setCharHeight(20f);
			txtConfiguration.setCharWidth(10f);
//			exporter.setConfiguration(txtConfiguration);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.exportReport();
			
			
		}
		
	}
	
}
