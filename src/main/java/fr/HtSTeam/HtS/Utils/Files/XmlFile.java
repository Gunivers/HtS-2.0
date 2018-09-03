package fr.HtSTeam.HtS.Utils.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.Utils.Tag;

public class XmlFile {

	private File file;
	private Document doc;
	
	private String parent;
	private String fileName;

	public XmlFile(String parent, String fileName) {
		this.parent = parent;
		this.fileName = fileName;
		file = new File(Main.plugin.getDataFolder() + "/" + parent + "/", fileName + ".xml");
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
		else {
			file.delete();
			file = new File(Main.plugin.getDataFolder() + "/" + parent + "/", fileName + ".xml");
		}
		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty(OutputKeys.METHOD, "xml");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);

			tf.transform(source, result);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			e.printStackTrace();
		}
	}

	public void add(Tag t) {
		add(t, false);
	}

	private void add(Tag t, boolean child) {
		if (t.name.isEmpty())
			return;
		Node parent = doc.getFirstChild();
		if (parent == null) {
			parent = doc.createElement("root");
			doc.appendChild(parent);
		}
		while (parent.getLastChild() != null && parent.getLastChild().getNodeType() != Node.TEXT_NODE
				&& !parent.getLastChild().getNodeName().equals(t.name) && child) {
			parent = parent.getLastChild();
		}
		if (t.values != null && !t.values.isEmpty()) {
			Element node = doc.createElement(t.name);
			if (t.attributes != null && !t.attributes.isEmpty()) {
				for (Entry<String, String> attributes : t.attributes.entrySet()) {
					Attr attr = doc.createAttribute(attributes.getKey());
					attr.setValue(attributes.getValue());
					node.setAttributeNode(attr);
				}
			}
			parent.appendChild(node);
			for (Tag tag : t.values)
				add(tag, true);
			return;
		}
		parent.appendChild(doc.createTextNode(t.name));
	}
	
	public ArrayList<Tag> get(String attr_name, String attr_value) {
		ArrayList<Tag> tags = get();		
		if (attr_name != null && attr_value != null) {
			tags.forEach(t -> { if (!t.attributes.containsKey(attr_name) && !t.attributes.containsValue(attr_value)) tags.remove(t);});
			return tags;
		} else {
			tags.forEach(t -> { if (t.attributes != null) tags.remove(t);});
			return tags;
		}
	}
	
	public ArrayList<Tag> get() {
		Node root = doc.getFirstChild();
		if (root == null)
			return null;
		if (!root.hasChildNodes())
			return null;
		ArrayList<Tag> tags = new ArrayList<Tag>();
		NodeList nodes = root.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;
			if (!node.hasChildNodes())
				continue;
			String name = node.getNodeName();
			HashMap<String, String> attributes = new HashMap<String, String>();
			NamedNodeMap attrs = node.getAttributes();
			if (attrs != null && attrs.getLength() != 0)
				for (int y = 0; y < attrs.getLength(); y++)
					attributes.put(attrs.item(y).getNodeName(), attrs.item(y).getNodeValue());
			ArrayList<Tag> values = get(node);
			tags.add(new Tag(name, attributes, values));
		}
		return tags;
	}

	private ArrayList<Tag> get(Node parent) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		NodeList nodes = parent.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.TEXT_NODE && !node.getTextContent().contains("\r") && !node.getTextContent().contains("\n")) {
				tags.add(new Tag(node.getTextContent(), null, null));
				continue;
			}
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String name = node.getNodeName();
				HashMap<String, String> attributes = new HashMap<String, String>();
				NamedNodeMap attrs = node.getAttributes();
				if (attrs != null && attrs.getLength() != 0)
					for (int y = 0; y < attrs.getLength(); y++)
						attributes.put(attrs.item(y).getNodeName(), attrs.item(y).getNodeValue());
				ArrayList<Tag> values = get(node);
				tags.add(new Tag(name, attributes, values));
			}
		}
		return tags;
	}
}