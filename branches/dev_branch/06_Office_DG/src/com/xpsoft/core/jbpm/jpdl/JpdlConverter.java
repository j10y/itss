package com.xpsoft.core.jbpm.jpdl;

import com.xpsoft.core.util.XmlUtil;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class JpdlConverter {
	private static Log logger = LogFactory.getLog(JpdlConverter.class);

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			/* 37 */Long uuid = Long.valueOf(Math.abs(UUID.randomUUID()
					.getLeastSignificantBits()));
			/* 38 */System.out.println("uid:" + uuid.toString());
		}
	}

	public static String JpdlGen(String drawXml, String processName) {
		/* 51 */if (logger.isDebugEnabled()) {
			/* 52 */logger.debug("drawXml:" + drawXml);
		}

		/* 55 */Document jpdlDoc = DocumentHelper.createDocument();

		/* 57 */jpdlDoc.setXMLEncoding(System.getProperty("file.encoding"));

		/* 59 */Element processEl = jpdlDoc.addElement("process");

		/* 61 */processEl.addAttribute("name", processName);

		/* 63 */Document drawDoc = XmlUtil.stringToDocument(drawXml);
		/* 64 */Element orgRootEl = drawDoc.getRootElement();

		/* 67 */Set transitionSet = new HashSet();

		/* 69 */Map nodeMap = parseDrawXml(transitionSet, orgRootEl);

		/* 71 */Map newNodeMap = new LinkedHashMap();
		/* 72 */Iterator ids = nodeMap.keySet().iterator();

		/* 74 */while (ids.hasNext()) {
			/* 75 */String id = (String) ids.next();
			/* 76 */Element pNode = (Element) nodeMap.get(id);
			/* 77 */Element curNewNode = processEl.addElement(pNode
					.getQualifiedName());
			/* 78 */String x = pNode.attributeValue("x");
			/* 79 */String y = pNode.attributeValue("y");

			/* 81 */String width = pNode.attributeValue("w");
			/* 82 */Integer intWidth = Integer.valueOf(new Integer(width)
					.intValue() + 10);

			/* 84 */String height = pNode.attributeValue("h");
			/* 85 */Integer intHeight = Integer.valueOf(new Integer(height)
					.intValue() + 10);

			/* 87 */curNewNode.addAttribute("name",
					pNode.attributeValue("name"));
			/* 88 */curNewNode.addAttribute("g", x + "," + y + "," + intWidth
					+ "," + intHeight);

			/* 90 */newNodeMap.put(id, curNewNode);
		}

		/* 93 */Iterator tranIt = transitionSet.iterator();

		/* 95 */while (tranIt.hasNext()) {
			/* 96 */Element tranEl = (Element) tranIt.next();
			/* 97 */String g = tranEl.attributeValue("g");
			/* 98 */String name = tranEl.attributeValue("name");

			/* 101 */Element startNode = (Element) tranEl
					.selectSingleNode("./startConnector/rConnector/Owner/*");
			/* 102 */Element endNode = (Element) tranEl
					.selectSingleNode("./endConnector/rConnector/Owner/*");

			/* 104 */if ((startNode != null) && (endNode != null)) {
				/* 105 */String startRef = startNode.attributeValue("ref");
				/* 106 */String endRef = endNode.attributeValue("ref");
				Element newEndNode;
				Element newStartNode;
				/* 111 */if ((startRef != null) && (endRef != null)) {
					/* 112 */newStartNode = (Element) newNodeMap.get(startRef);
					/* 113 */newEndNode = (Element) newNodeMap.get(endRef);
				} else {
					/* 115 */String startId = startNode.attributeValue("id");
					/* 116 */String endId = startNode.attributeValue("id");
					/* 117 */newStartNode = (Element) newNodeMap.get(startId);
					/* 118 */newEndNode = (Element) newNodeMap.get(endId);
				}

				/* 121 */Element transitionEl = newStartNode
						.addElement("transition");
				/* 122 */transitionEl.addAttribute("name", name);
				/* 123 */transitionEl.addAttribute("to",
						newEndNode.attributeValue("name"));
				/* 124 */transitionEl.addAttribute("g", g);

				/* 126 */if ("decision".equals(newStartNode.getQualifiedName())) {
					/* 127 */Element conditionEl = (Element) orgRootEl
							.selectSingleNode("/drawing/figures//decision/conditions/condition[@to='"
									+ name + "']");
					/* 128 */if (conditionEl != null) {
						/* 129 */Element newConditionEl = transitionEl
								.addElement("condition");
						/* 130 */newConditionEl.addAttribute("expr",
								conditionEl.attributeValue("expr"));
					}
				}
			}
		}

		/* 136 */if (logger.isDebugEnabled()) {
			/* 137 */logger
					.debug("after convter jbpm xml:" + processEl.asXML());
		}

		/* 140 */return jpdlDoc.asXML();
	}

	private static Map<String, Element> parseDrawXml(Set transitionSet,
			Element rootEl) {
		/* 146 */Map map = new LinkedHashMap();

		/* 148 */List<Element> figures = rootEl
				.selectNodes("/drawing/figures/*");

		/* 150 */for (Element el : figures) {
			/* 151 */String id = el.attributeValue("id");
			/* 152 */String ref = el.attributeValue("ref");

			/* 154 */if ("transition".equals(el.getQualifiedName())) {
				/* 155 */transitionSet.add(el);
			}
			/* 157 */else if (id != null) {
				/* 158 */map.put(id, el);
				/* 159 */} else if (ref != null) {
				/* 160 */Node figureNode = rootEl
						.selectSingleNode("/drawing/figures//*[@id='" + ref
								+ "']");
				/* 161 */map.put(ref, (Element) figureNode);
			}

		}

		/* 166 */return map;
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.core.jbpm.jpdl.JpdlConverter JD-Core Version: 0.6.0
 */