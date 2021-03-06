package com.mbor.converterservice.printers.xml.nodelist;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.utils.CommonUtils;
import com.mbor.converterservice.utils.XmlUtils;

import java.util.Map;

public class XMLNodeListPrinter extends IndentationPrinter {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList xmlList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(XmlUtils.XML_OPEN_TAG).append(xmlList.getNodeName());
        if (!xmlList.getAttributes().isEmpty()) {
            stringBuilder
                    .append(addAttributes(xmlList));
        }
        stringBuilder.append(XmlUtils.XML_CLOSE_TAG).append(CommonUtils.NEW_LINE);
        increaseIndentation();
        for (AbstractNode currentElement : xmlList) {
            currentElement.setPrinterThreadLocal(getThreadLocal());
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            stringBuilder.append(CommonUtils.NEW_LINE);
        }
        decreaseIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(XmlUtils.XML_CLOSE_ELEMENT_TAG).append(xmlList.getNodeName()).append(XmlUtils.XML_CLOSE_TAG);
        return stringBuilder.toString();
    }


    private String addAttributes(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        String key;
        String value;
        for (Map.Entry entry : abstractNode.getAttributes().entrySet()) {
            if(entry.getKey().toString().startsWith("@")){
                key = entry.getKey().toString().replaceFirst("@", "");
            } else {
                key = entry.getKey().toString();
            }
            value = "\"" + entry.getValue().toString() + "\"";
            stringBuilder.append(" ").append(key).append("=").append(value);
        }
        return stringBuilder.toString();
    }

}
