package com.egen.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.egen.models.Alert;
import com.egen.rules.OverWeightRule;

public class AlertControllerTest {


    @Autowired
    private MockMvc mvc;
    
    @Mock 
    AlertController controller = new AlertController();

    @Before
    public void setup()
    {

		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(controller)
				.build();	
    }
    
    @Test
    public void testRead() throws Exception {
        List<Alert> list = new ArrayList<Alert>();

    	Alert alert = new Alert();
    	alert.setTimeStamp(0);
    	alert.setType(OverWeightRule.TYPE);
    	alert.setValue(120);
    	list.add(alert);
    	alert = new Alert();
    	alert.setTimeStamp(4);
    	alert.setType(OverWeightRule.TYPE);
    	alert.setValue(140);
    	list.add(alert);
    	given(controller.read()).willReturn(list);
    	this.mvc.perform(get("/alerts/read").accept("application/json"))
        .andExpect(status().isOk()).andExpect(content().string("[{\"timeStamp\":0,\"type\":\"Over Weight\",\"value\":120},{\"timeStamp\":4,\"type\":\"Over Weight\",\"value\":140}]"));
    	
    }
    
    @Test
    public void testReadWithRange() throws Exception {
        List<Alert> list = new ArrayList<Alert>();

    	Alert alert = new Alert();
    	alert.setTimeStamp(7);
    	alert.setType(OverWeightRule.TYPE);
    	alert.setValue(150);
    	list.add(alert);
    	alert = new Alert();
    	alert.setTimeStamp(4);
    	alert.setType(OverWeightRule.TYPE);
    	alert.setValue(140);
    	list.add(alert);
    	
    	given(controller.readByTimeRange(3, 10)).willReturn(list);
    	this.mvc.perform(get("/alerts/readByTimeRange/3/10").accept("application/json"))
        .andExpect(status().isOk()).andExpect(content().string("[{\"timeStamp\":7,\"type\":\"Over Weight\",\"value\":150},{\"timeStamp\":4,\"type\":\"Over Weight\",\"value\":140}]"));
    	
    }
    
    @Test
    public void testReadWithRangeNoMatch() throws Exception {
        List<Alert> list = new ArrayList<Alert>();
    	given(controller.readByTimeRange(40,100)).willReturn(list);
    	this.mvc.perform(get("/alerts/readByTimeRange/40/100").accept("application/json"))
        .andExpect(status().isOk()).andExpect(content().string("[]"));
    	
    }
    
    
    
    
    
    
}
