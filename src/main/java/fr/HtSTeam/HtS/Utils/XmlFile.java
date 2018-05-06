package fr.HtSTeam.HtS.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlFile {

	private File file;
	private Document doc;

	public XmlFile(JavaPlugin plugin, String fileName) {
		file = new File(plugin.getDataFolder(), fileName + ".xml");
		if (file.exists()) {
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		} else {
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public void save() {
		if (!file.exists())
			file.getParentFile().mkdirs();
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}
	}

	public NodeList getNodeList(final String tagname) {
		return doc.getElementsByTagName(tagname);
	}

	public String getAttributeValue(final Node node, final String attribute) {
		return ((Element) node).getAttribute(attribute);
	}

	public void set(final String node_name, final Map<String, String> attributes, final String node_value) {
		Element root = doc.createElement(node_name);
		doc.appendChild(root);
		for (String atrr_name : attributes.keySet()) {
			Attr attr = doc.createAttribute(atrr_name);
			attr.setValue(attributes.get(atrr_name));
			root.setAttributeNode(attr);
		}
	}

	public void set(final String node_name, final Map<String, String> attributes, final String node_value,
			final String parent_name, final String parent_attr, final String parent_attrvalue) {
		if (doc.getDocumentElement().getNodeName() == null || parent_name == null)
			return;

		Element node = doc.createElement(node_name);
		node.appendChild(doc.createTextNode(node_value));
		for (String atrr_name : attributes.keySet()) {
			Attr attr = doc.createAttribute(atrr_name);
			attr.setValue(attributes.get(atrr_name));
			node.setAttributeNode(attr);
		}
		NodeList parents = doc.getElementsByTagName(parent_name);
		for (int i = 0; i < parents.getLength(); i++)
			if (parents.item(i).getNodeType() == Node.ELEMENT_NODE)
				if (((Element) parents.item(i)).getAttribute(parent_attr) == parent_attrvalue)
					((Element) parents.item(i)).appendChild(node);
	}
}
