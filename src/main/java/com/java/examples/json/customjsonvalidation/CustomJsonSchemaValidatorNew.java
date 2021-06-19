package com.java.examples.json.customjsonvalidation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class CustomJsonSchemaValidatorNew {
	
	String returnString="";
	
	private static InputStream inputStreamFromClasspath(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    public static void main(String[] args) throws Exception {
    	
    	CustomJsonSchemaValidatorNew schemadata= new CustomJsonSchemaValidatorNew();
    	
    	schemadata.validationJsonSchema("F:\\SoapUI\\Schema\\example.json", "F:\\SoapUI\\Schema\\example-schema.json");
    }
    
    public String validationJsonSchema(String jsonfile, String jsonSchema) throws IOException{
    	ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

        InputStream jsonStream=null;
        InputStream schemaStream=null;
        Set<ValidationMessage> validationResult;
        
        try {
               // InputStream jsonStream = inputStreamFromClasspath("example.json");
                //InputStream schemaStream = inputStreamFromClasspath("example-schema.json");
        		jsonStream= new FileInputStream(jsonfile);
        		 schemaStream = new FileInputStream(jsonSchema);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        {
            JsonNode json = objectMapper.readTree(jsonStream);
            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            validationResult = schema.validate(json);

            // print validation errors
            if (validationResult.isEmpty()) {
                System.out.println("no validation errors :-)");
                returnString="no validation errors :-)";
                
            } else {
                validationResult.forEach(vm -> 
                {
                	System.out.println(vm.getMessage());
                	returnString = returnString + "===" +vm.getMessage();
                }
                		);
                //returnString="validation errors";
            }
        }
		return returnString;
    	
    }

}
