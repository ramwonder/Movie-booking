//package com.moviebookingapp.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Producer {
////	@Autowired
////	KafkaTemplate<String,String> template;
////	@Value("${kafka.topic}")
////	String topic;
////	public void sendMessage(String message) {
////		template.send(topic,message);
////	}
//	
//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
//	
//	String kafkaTopic = "java_in_use_topic";
//	
//	public void send(String data) {
//	    
//	    kafkaTemplate.send(kafkaTopic, data);
//	}
//	
//
//}
