package com.prajyot.report.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajyot.report.model.JsonObject;

@RestController
@RequestMapping("/listner")
public class KafkaConsumerController {

	Logger logger = LoggerFactory.getLogger(KafkaConsumerController.class);
	
	List<String> topicData = new ArrayList<>();
	JsonObject jsonObject = null;
	
	@GetMapping("/get-Messages")
	public List<String> getTopicData(){
		return topicData;
	}
	
	@GetMapping("/get-Json")
	public JsonObject getJsonData() {
		return jsonObject;
	}
	
	@KafkaListener(groupId = "kafka-group-id-1", topics = "NewTopic", containerFactory = "kafkaListenerContainerFactory")
	public List<String> getMsgFromTopic(String data){
		topicData.add(data);
		System.out.print("List Data is ");
		topicData.stream().forEach(i ->System.out.print(i+" "));
		logger.info("Data recieved from topic is - "+data);
		return topicData;
	}
	
	@KafkaListener(groupId = "kafka-group-id-2", topics = "NewJsonTopic", containerFactory = "userKafkaListenerContainerFactory")
	public JsonObject getJsonMsgFromTopic(JsonObject data){
		jsonObject =  data;
		logger.info("Data recieved from topic is - "+data);
		return jsonObject;
	}
}
