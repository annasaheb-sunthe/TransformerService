package com.scb.serviceImpl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.scb.model.CustomerRequestData;
import com.scb.model.CustomerResponse;
import com.scb.model.RequestData;
import com.scb.model.TransformRule;
import com.scb.repository.TransformRulesRepo;
import com.scb.service.CustomerRequestService;
import com.scb.util.SCBCommonMethods;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerRequestServiceImpl implements CustomerRequestService {
	@Autowired
	private TransformRulesRepo transformRulesRepo;

	String xlst = "<?xml version=\"1.0\"?>\r\n"
			+ "<outwClrg:sendFastCustomerCreditTransferRequest xmlns:ns=\"http://www.sc.com/SCBML-1\" xmlns:outwClrg=\"http://www.sc.com/cash/v2/outwClrg\" xmlns:head=\"urn:iso:std:iso:20022:tech:xsd:head_pacs008.001.001.01\" xmlns:tns=\"urn:cash:std:xsd:FIToFICstmrCdtTrf01\" xmlns:pt_supp=\"urn:cash:std:xsd:supp.pacs.008.v2\" xmlns:pt=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.04\" xmlns:doc=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.06\" xmlns:ah=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.01\">\r\n"
			+ "  <ns:header>\r\n" + "    <ns:messageDetails>\r\n"
			+ "      <ns:messageVersion>2.0</ns:messageVersion>\r\n" + "      <ns:messageType>\r\n"
			+ "        <ns:typeName>Cash:OutwardCustomerCreditClearing</ns:typeName>\r\n"
			+ "        <ns:subType subTypeScheme=\"http://tempuri.org\">\r\n"
			+ "          <ns:subTypeName>SendFASTCustomerCreditTransfer or NRT or EBPP</ns:subTypeName>\r\n"
			+ "        </ns:subType>\r\n" + "      </ns:messageType>\r\n" + "    </ns:messageDetails>\r\n"
			+ "    <ns:originationDetails>\r\n" + "      <ns:messageSender>\r\n"
			+ "        <ns:messageSender systemScheme=\"http://tempuri.org\">iBanking</ns:messageSender>\r\n"
			+ "        <ns:senderDomain>\r\n"
			+ "          <ns:domainName domainNameScheme=\"http://tempuri.org\">Cash</ns:domainName>\r\n"
			+ "          <ns:subDomainName subdomainNameScheme=\"http://tempuri.org\">\r\n"
			+ "            <ns:subDomainType>Payments</ns:subDomainType>\r\n" + "          </ns:subDomainName>\r\n"
			+ "        </ns:senderDomain>\r\n" + "        <ns:countryCode>MY</ns:countryCode>\r\n"
			+ "      </ns:messageSender>\r\n"
			+ "      <ns:messageTimestamp>2018-10-16T15:41:29</ns:messageTimestamp>\r\n"
			+ "      <ns:initiatedTimestamp>2018-10-16T15:41:29</ns:initiatedTimestamp>\r\n"
			+ "      <ns:trackingId>20181016SCBLMYKX51000004133</ns:trackingId>\r\n"
			+ "      <ns:correlationID>20181016SCBLMYKX51000004133</ns:correlationID>\r\n"
			+ "      <ns:possibleDuplicate>false</ns:possibleDuplicate>\r\n" + "    </ns:originationDetails>\r\n"
			+ "    <ns:captureSystem>iBanking</ns:captureSystem>\r\n" + "    <ns:process>\r\n"
			+ "      <ns:processName></ns:processName>\r\n" + "      <ns:eventType></ns:eventType>\r\n"
			+ "      <ns:lifecycleState></ns:lifecycleState>\r\n" + "      <ns:workflowState></ns:workflowState>\r\n"
			+ "      <ns:action></ns:action>\r\n" + "    </ns:process>\r\n" + "  </ns:header>\r\n"
			+ "  <outwClrg:sendFastCustomerCreditTransferReqPayload>\r\n"
			+ "    <ns:payloadFormat>XML</ns:payloadFormat>\r\n" + "    <ns:payloadVersion>2.0</ns:payloadVersion>\r\n"
			+ "    <outwClrg:sendFastCustomerCreditTransferReq>\r\n" + "      <tns:FIToFICstmrCdtTrf01>\r\n"
			+ "        <tns:Header>\r\n" + "          <head:Fr>\r\n" + "            <head:FIId>\r\n"
			+ "              <head:FinInstnId>\r\n" + "                <head:PstlAdr>\r\n"
			+ "                  <head:Ctry>MY</head:Ctry>\r\n" + "                </head:PstlAdr>\r\n"
			+ "                <head:Othr>\r\n" + "                  <head:Id>SCBLMYKX</head:Id>\r\n"
			+ "                </head:Othr>\r\n" + "              </head:FinInstnId>\r\n"
			+ "            </head:FIId>\r\n" + "          </head:Fr>\r\n" + "          <head:To>\r\n"
			+ "            <head:FIId>\r\n" + "              <head:FinInstnId>\r\n"
			+ "                <head:PstlAdr>\r\n" + "                  <head:Ctry>MY</head:Ctry>\r\n"
			+ "                </head:PstlAdr>\r\n" + "                <head:Othr>\r\n"
			+ "                  <head:Id>RPPEMYKL</head:Id>\r\n" + "                </head:Othr>\r\n"
			+ "              </head:FinInstnId>\r\n" + "            </head:FIId>\r\n" + "          </head:To>\r\n"
			+ "          <head:BizMsgIdr>20181016SCBLMYKX510ORB00004133</head:BizMsgIdr>\r\n"
			+ "          <head:MsgDefIdr>pacs.008.001.04</head:MsgDefIdr>\r\n"
			+ "          <head:BizSvc>RPP</head:BizSvc>\r\n"
			+ "          <head:CreDt>2018-10-16T07:41:29Z</head:CreDt>\r\n"
			+ "          <head:PssblDplct>false</head:PssblDplct>\r\n" + "        </tns:Header>\r\n"
			+ "        <tns:Document>\r\n" + "          <tns:FIToFICstmrCdtTrf>\r\n" + "            <tns:GrpHdr>\r\n"
			+ "              <pt:MsgId>20181016SCBLMYKX51000004133</pt:MsgId>\r\n"
			+ "              <pt:CreDtTm>2018-10-16T15:41:29.326</pt:CreDtTm>\r\n"
			+ "              <pt:NbOfTxs>1</pt:NbOfTxs>\r\n" + "              <pt:SttlmInf>\r\n"
			+ "                <pt:SttlmMtd>CLRG</pt:SttlmMtd>\r\n" + "              </pt:SttlmInf>\r\n"
			+ "            </tns:GrpHdr>\r\n" + "            <tns:CdtTrfTxInf>\r\n" + "              <tns:PmtId>\r\n"
			+ "                <pt:EndToEndId>20181016SCBLMYKX510ORB00004133</pt:EndToEndId>\r\n"
			+ "                <pt:TxId>20181016SCBLMYKX51000004133</pt:TxId>\r\n" + "              </tns:PmtId>\r\n"
			+ "              <tns:PmtTpInf>\r\n" + "                <pt:CtgyPurp>\r\n"
			+ "                  <pt:Prtry>CASH</pt:Prtry>\r\n" + "                </pt:CtgyPurp>\r\n"
			+ "              </tns:PmtTpInf>\r\n"
			+ "              <tns:IntrBkSttlmAmt Ccy=\"MYR\">10.0</tns:IntrBkSttlmAmt>\r\n"
			+ "              <tns:IntrBkSttlmDt>2018-10-16</tns:IntrBkSttlmDt>\r\n"
			+ "              <tns:ChrgBr>DEBT</tns:ChrgBr>\r\n" + "              <tns:InstgAgt>\r\n"
			+ "                <pt:FinInstnId>\r\n" + "                  <pt:Othr>\r\n"
			+ "                    <pt:Id>SCBLMYKX</pt:Id>\r\n" + "                  </pt:Othr>\r\n"
			+ "                  <pt:PstlAdr>\r\n" + "                    <pt:Ctry>MY</pt:Ctry>\r\n"
			+ "                  </pt:PstlAdr>\r\n" + "                </pt:FinInstnId>\r\n"
			+ "              </tns:InstgAgt>\r\n" + "              <tns:Dbtr>\r\n"
			+ "                <pt:Nm>NG SIEW GUO</pt:Nm>\r\n" + "              </tns:Dbtr>\r\n"
			+ "              <tns:DbtrAcct>\r\n" + "                <pt:Id>\r\n" + "                  <pt:Othr>\r\n"
			+ "                    <pt:Id>388120692978</pt:Id>\r\n" + "                  </pt:Othr>\r\n"
			+ "                </pt:Id>\r\n" + "                <pt:Tp>\r\n"
			+ "                  <pt:Prtry>SVGS</pt:Prtry>\r\n" + "                </pt:Tp>\r\n"
			+ "              </tns:DbtrAcct>\r\n" + "              <tns:DbtrAgt>\r\n"
			+ "                <pt:FinInstnId>\r\n" + "                  <pt:Othr>\r\n"
			+ "                    <pt:Id>SCBLMYKX</pt:Id>\r\n" + "                  </pt:Othr>\r\n"
			+ "                </pt:FinInstnId>\r\n" + "              </tns:DbtrAgt>\r\n"
			+ "              <tns:CdtrAgt>\r\n" + "                <pt:FinInstnId>\r\n"
			+ "                  <pt:Othr>\r\n" + "                    <pt:Id>HBMBMYKL</pt:Id>\r\n"
			+ "                  </pt:Othr>\r\n" + "                </pt:FinInstnId>\r\n"
			+ "              </tns:CdtrAgt>\r\n" + "              <tns:Cdtr>\r\n"
			+ "                <pt:Nm>payeeName</pt:Nm>\r\n" + "              </tns:Cdtr>\r\n"
			+ "              <tns:CdtrAcct>\r\n" + "                <pt:Id>\r\n" + "                  <pt:Othr>\r\n"
			+ "                    <pt:Id>101026300101</pt:Id>\r\n" + "                  </pt:Othr>\r\n"
			+ "                </pt:Id>\r\n" + "                <pt:Tp>\r\n"
			+ "                  <pt:Prtry>DFLT</pt:Prtry>\r\n" + "                </pt:Tp>\r\n"
			+ "              </tns:CdtrAcct>\r\n" + "              <tns:SplmtryData>\r\n"
			+ "                <pt_supp:Envlp>\r\n" + "                  <pt_supp:Document>\r\n"
			+ "                    <pt_supp:FIToFICstmrCdtTrfXtnsn>\r\n"
			+ "                      <pt_supp:CdtrTrfTxInfXtnsn>\r\n"
			+ "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>RecptRef</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>3rd Party Transfer</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>PaymntDesc</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>DuitNow</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>ScndValInd</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>N</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>IdTp</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>01</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>Id</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>IdTp</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>RsdntSts</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>1</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>PrdTp</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>C</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>ShariaCmpl</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>N</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>Dtls</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>1</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>IPAddr</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>10.21.195.182</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>DbtrId</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>SCBLMYKX</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>URL</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>URL</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n" + "                        <pt_supp:AddnlInfo>\r\n"
			+ "                          <pt_supp:Tp>PrxyLkUpRef</pt_supp:Tp>\r\n"
			+ "                          <pt_supp:Value>20181016SCBLMYKX510ORB00004133</pt_supp:Value>\r\n"
			+ "                        </pt_supp:AddnlInfo>\r\n"
			+ "                      </pt_supp:CdtrTrfTxInfXtnsn>\r\n"
			+ "                    </pt_supp:FIToFICstmrCdtTrfXtnsn>\r\n" + "                  </pt_supp:Document>\r\n"
			+ "                </pt_supp:Envlp>\r\n" + "              </tns:SplmtryData>\r\n"
			+ "            </tns:CdtTrfTxInf>\r\n" + "          </tns:FIToFICstmrCdtTrf>\r\n"
			+ "        </tns:Document>\r\n" + "      </tns:FIToFICstmrCdtTrf01>\r\n"
			+ "    </outwClrg:sendFastCustomerCreditTransferReq>\r\n"
			+ "  </outwClrg:sendFastCustomerCreditTransferReqPayload>\r\n"
			+ "</outwClrg:sendFastCustomerCreditTransferRequest>";

	@Autowired
	private SCBCommonMethods commonMethods;

	@Override
	@Transactional
	public CustomerResponse customerRequestHandleService(CustomerRequestData customerRequestData) {

		// we need to write transform logic here

		return null;
	}

	@Override
	public RequestData getParseRequestData(RequestData requestData) {
		requestData.setPayload(xlst);
		TransformRule dbTransformRule = transformRulesRepo.findByTransactionTypeAndTransactionSubType(
				requestData.getTransactionType(), requestData.getTransactionSubType());
		String parseOutput = null;
		try {
			if (dbTransformRule.getTargetMessageType().equalsIgnoreCase("JSON")) {
				parseOutput = xmlToJson(requestData.getPayload());
			} else if (dbTransformRule.getTargetMessageType().equalsIgnoreCase("XML")) {
				parseOutput = jsonToXml(xmlToJson(requestData.getPayload()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		requestData.setPayload(parseOutput);
		return requestData;
	}

	public String jaxbObjectToXML(Object employee)

	{
		// System.out.println( employee.toString() );
		String xmlContent = null;
		try {
			// Create JAXB Context
			JAXBContext jaxbContext = JAXBContext.newInstance(employee.getClass());

			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Print XML String to Console
			StringWriter sw = new StringWriter();

			// Write XML to StringWriter
			jaxbMarshaller.marshal(employee, sw);

			// Verify XML Content
			xmlContent = sw.toString();
			System.out.println("XML contents" + xmlContent);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xmlContent;
	}

	public String jaxbObjectToJSON(Object crd) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonContent = null;
		try {

			// Convert object to JSON string
			jsonContent = mapper.writeValueAsString(crd);
			System.out.println(jsonContent);

			// Convert object to JSON string and pretty print
			// jsonContent =
			// mapper.writerWithDefaultPrettyPrinter().writeValueAsString(crd);
			System.out.println(jsonContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonContent;
	}

	public String xmlToJson(String data) throws JsonProcessingException, IOException {
		// Create a new XmlMapper to read XML tags
		XmlMapper xmlMapper = new XmlMapper();

		// Reading the XML
		JsonNode jsonNode = xmlMapper.readTree(data.getBytes());

		// Create a new ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		// Get JSON as a string
		String value = objectMapper.writeValueAsString(jsonNode);

		// System.out.println("*** Converting XML to JSON ***");
		 System.out.println(value);
		
		return value.replaceAll("\\\\", "");

	}

	public String jsonToXml(String data) {
		JSONObject json = new JSONObject(data);
		String xml = XML.toString(json);

		System.out.println(xml);
		return xml;
	}

}
