package ru.kck.usb.zags.fataloiv.transform;

import ru.kck.usb.zags.fataloiv.utils.Translate;
import ru.usb.commons.utils.UsbXmlUtil;
import ru.usb.scenario.smev3.standard.producer.SmevToStandardProducerTransformation;
import ru.usb.smev3.message.getRequest.GetRequestIncomeBusinessMessage;
import ru.usb.standard.message.outcome.StandardClientMessageData;
import org.w3c.dom.*;

/**
 * @author Ilya V. Kupriyanov
 * @version 02/02/2020
 */

public class ZagsFatalOivSmev2StandartTransformation extends SmevToStandardProducerTransformation {

    @Override
    public StandardClientMessageData transform(GetRequestIncomeBusinessMessage smevData) {
        Document doc = UsbXmlUtil.convertStringToDocument(UsbXmlUtil.convertDomTreeToString(smevData.getBody(), false, false));
        Document xmlDoc = UsbXmlUtil.createEmptyDocument();
        getNodeValue(doc.getChildNodes(), xmlDoc, null);
        return new StandardClientMessageData(xmlDoc);
    }

    private void getNodeValue(NodeList nodeList, Document xmlDoc, Node newNode) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element newElement = xmlDoc.createElement(getLatName(node));
                Element elem = (Element) node;
                if (elem.hasAttributes()) {
                    NamedNodeMap attributes = elem.getAttributes();
                    for (int a = 0; a < attributes.getLength(); a++) {
                        if (attributes.item(a) instanceof Attr) {
                            Attr attr = (Attr) attributes.item(a);
                            if (!attr.getName().startsWith("xmlns")) {
                                Node item;
                                item = xmlDoc.createElement(getLatName(attr));
                                item.appendChild(xmlDoc.createTextNode(attr.getValue()));
                                newElement.appendChild(item);
                            }
                        }
                    }
                }
                if (node.hasChildNodes()) {
                    getNodeValue(node.getChildNodes(), xmlDoc, newElement);
                }
                if (newNode == null) {
                    xmlDoc.appendChild(newElement);
                } else {
                    newNode.appendChild(newElement);
                }
            } else {
                if (newNode == null) {
                    xmlDoc.appendChild(xmlDoc.importNode(node, true));
                } else {
                    newNode.appendChild(xmlDoc.importNode(node, true));
                }
            }
        }
    }

    private void setValueNode(Node parentNode, Node childNode, Document xmlDoc) {
        Node valueNode;
        valueNode = xmlDoc.createElement("value");
        valueNode.appendChild(xmlDoc.importNode(childNode, true));
        parentNode.appendChild(valueNode);
    }

    private String getLatName(Node node) {
        char kirArray[] = node.getLocalName().toCharArray();
        StringBuilder latName = new StringBuilder();
        for (int i = 0; i < kirArray.length; i++) {
            if (Translate.kir2lat.containsKey(kirArray[i])) {
                latName.append(Translate.kir2lat.get(kirArray[i]));
            } else {
                return node.getLocalName();
            }
        }
        return latName.toString();
    }

}