package com.xpsoft.core.jbpm.jpdl;

import java.awt.Point;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;

import com.xpsoft.core.util.XmlUtil;

public class JpdlModel {
	/* 22 */private Set<String> activityNames = new HashSet();

	/* 24 */private Map<String, Node> nodes = new LinkedHashMap();
	public static final int RECT_OFFSET_X = -4;
	public static final int RECT_OFFSET_Y = -8;
	public static final int DEFAULT_PIC_SIZE = 48;
	/* 29 */private static final Map<String, Object> nodeInfos = new HashMap();

	/* 31 */static {
		nodeInfos.put("start", "start_event_empty.png");
		/* 32 */nodeInfos.put("end", "end_event_terminate.png");
		/* 33 */nodeInfos.put("end-cancel", "end_event_cancel.png");
		/* 34 */nodeInfos.put("end-error", "end_event_error.png");
		/* 35 */nodeInfos.put("decision", "gateway_exclusive.png");
		/* 36 */nodeInfos.put("fork", "gateway_parallel.png");
		/* 37 */nodeInfos.put("join", "gateway_parallel.png");
		/* 38 */nodeInfos.put("state", null);
		/* 39 */nodeInfos.put("hql", null);
		/* 40 */nodeInfos.put("sql", null);
		/* 41 */nodeInfos.put("java", null);
		/* 42 */nodeInfos.put("script", null);
		/* 43 */nodeInfos.put("task", null);
		/* 44 */nodeInfos.put("sub-process", null);
		/* 45 */nodeInfos.put("custom", null);
	}

	public JpdlModel(String defXml) throws Exception {
		/* 49 */this(XmlUtil.stringToDocument(defXml).getRootElement());
	}

	public JpdlModel(InputStream is) throws Exception {
		/* 53 */this(XmlUtil.load(is).getRootElement());
	}

	private JpdlModel(Element rootEl) throws Exception {
		
		List<Element> els = rootEl.elements();
		for (Element el : els) {
			String type = el.getQName().getName();
			if (!nodeInfos.containsKey(type)) {
				continue;
			}
			String name = null;
			/* 64 */if (el.attribute("name") != null) {
				/* 65 */name = el.attributeValue("name");
			}
			/* 67 */String[] location = el.attributeValue("g").split(",");
			/* 68 */int x = Integer.parseInt(location[0]);
			/* 69 */int y = Integer.parseInt(location[1]);
			/* 70 */int w = Integer.parseInt(location[2]);
			/* 71 */int h = Integer.parseInt(location[3]);

			/* 73 */if (nodeInfos.get(type) != null) {
				/* 74 */w = 48;
				/* 75 */h = 48;
			} else {
				/* 77 */x += 4;
				/* 78 */y += 8;
				/* 79 */w -= 8;
				/* 80 */h -= 16;
			}
			/* 82 */Node node = new Node(name, type, x, y, w, h);
			/* 83 */parserTransition(node, el);
			/* 84 */this.nodes.put(name, node);
		}
	}

	public Set<String> getActivityNames() {
		/* 89 */return this.activityNames;
	}

	public void setActivityNames(Set<String> activityNames) {
		/* 93 */this.activityNames = activityNames;
	}

	private void parserTransition(Node node, Element nodeEl) {
		
		List<Element> els = nodeEl.elements("transition");
		for (Element el : els) {
			/* 99 */String label = el.attributeValue("name");
			/* 100 */String to = el.attributeValue("to");
			/* 101 */Transition transition = new Transition(label, to);
			/* 102 */String g = el.attributeValue("g");
			/* 103 */if ((g != null) && (g.length() > 0)) {
				/* 104 */if (g.indexOf(":") < 0) {
					/* 105 */transition.setLabelPosition(getPoint(g));
				} else {
					/* 107 */String[] p = g.split(":");
					/* 108 */transition.setLabelPosition(getPoint(p[1]));
					/* 109 */String[] lines = p[0].split(";");
					/* 110 */for (String line : lines) {
						/* 111 */transition.addLineTrace(getPoint(line));
					}
				}
			}
			/* 115 */node.addTransition(transition);
		}
	}

	private Point getPoint(String exp) {
		/* 120 */if ((exp == null) || (exp.length() == 0)) {
			/* 121 */return null;
		}
		/* 123 */String[] p = exp.split(",");
		/* 124 */return new Point(Integer.valueOf(p[0]).intValue(), Integer
				.valueOf(p[1]).intValue());
	}

	public Map<String, Node> getNodes() {
		/* 131 */return this.nodes;
	}

	public static Map<String, Object> getNodeInfos() {
		/* 138 */return nodeInfos;
	}

}
