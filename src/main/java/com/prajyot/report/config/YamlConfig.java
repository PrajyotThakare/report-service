package com.prajyot.report.config;

import java.io.Serializable;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.prajyot.report.model.ReportData;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "report-config")
public class YamlConfig implements Serializable{

	private static final long serialVersionUID = 3464443963861926042L;
	
	public Map<Long , ReportData> reportMap;

}
