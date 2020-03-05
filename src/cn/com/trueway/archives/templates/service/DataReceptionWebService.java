package cn.com.trueway.archives.templates.service;

import java.io.File;

import javax.activation.DataHandler;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

@javax.xml.ws.soap.MTOM
@WebService
public interface DataReceptionWebService {
	
	String saveDataReception(@WebParam(name = "data")String data);

	String upload(@XmlMimeType("*/*")@WebParam(name = "handler")DataHandler handler, @WebParam(name = "fileName")String fileName, @WebParam(name = "json_str")String json_str, @WebParam(name = "file64")String file64)

			throws Exception;
	
	String achiveXMLMB();
}
