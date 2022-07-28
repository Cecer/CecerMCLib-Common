package com.cecer1.projects.mc.cecermclib.common.modules.text;

import com.cecer1.projects.mc.cecermclib.common.CecerMCLib;
import com.cecer1.projects.mc.cecermclib.common.modules.logger.LoggerModule;
import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.Click;
import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.Hover;
import com.cecer1.projects.mc.cecermclib.common.modules.text.parts.components.AbstractXmlTextComponent;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLTextContext {
    protected static final Pattern clickUriPattern = Pattern.compile("^(.+?):(.*)$");

    private final Map<String, String> placeholderValues;

    public XMLTextContext() {
        this.placeholderValues = new HashMap<>();
    }

    public XMLTextContext setPlaceholderTextValue(String key, String value) {
        return this.setPlaceholderXmlValue(key, escapeXml(value));
    }
    public XMLTextContext setPlaceholderXmlValue(String key, String value) {
        this.placeholderValues.put(key, value);
        return this;
    }
    String getPlaceholderValue(String key) {
        return this.placeholderValues.get(key);
    }

    public Collection<AbstractXmlTextComponent> parse(String xmlString) throws XMLStreamException {
        String preparedXmlString = this.prepareXmlString(xmlString);

        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        XMLEventReader reader = factory.createXMLEventReader(new StringReader(preparedXmlString));

        XMLTextParser parser = new XMLTextParser();
        parser.parse(reader);
        return parser.getOut();
    }

    private String prepareXmlString(String xmlString) {
        xmlString = StringUtils.replaceEachRepeatedly(
                xmlString,
                this.placeholderValues.keySet().stream().map(s -> "&:" + s + ";").toArray(String[]::new),
                this.placeholderValues.values().toArray(new String[0]));

        return "<root>" + xmlString + "</root>";
    }

    private static String escapeXml(String str) {
        return str
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("%", "&#37;");
    }
}


class XMLTextParser {

    private TextState parsingTextState;
    private final StringBuilder parsingText;

    private final List<AbstractXmlTextComponent> out;
    private final Map<String, List<AbstractXmlTextComponent>> segmentsOut;
    public List<AbstractXmlTextComponent> getOut() {
        return this.out;
    }
    public Map<String, List<AbstractXmlTextComponent>> getSegmentsOut() {
        return this.segmentsOut;
    }

    public XMLTextParser() {
        this.parsingTextState = TextState.newRootState();
        this.parsingText = new StringBuilder();
        this.out = new ArrayList<>();
        this.segmentsOut = new HashMap<>();
    }
    
    public void parse(XMLEventReader reader) throws XMLStreamException {
        while(reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            switch(event.getEventType()) {
                case XMLEvent.START_ELEMENT:
                    this.startElement((StartElement)event);
                    break;
                case XMLEvent.CHARACTERS:
                    this.characters((Characters)event);
                    break;
                case XMLEvent.END_ELEMENT:
                    this.endElement((EndElement)event);
                    break;
            }
        }
    }

    private void outputParts(Collection<? extends AbstractXmlTextComponent> parts) {
        String segmentId = this.parsingTextState.getSegmentId();
        List<AbstractXmlTextComponent> outputList = segmentId == null ? this.out : this.segmentsOut.get(segmentId);
        outputList.addAll(parts);
    }

    private void processContents() {
        this.outputParts(AbstractXmlTextComponent.fromLegacyText(this.parsingTextState, this.parsingText.toString()));
        this.parsingText.setLength(0);
    }

    private void startElement(StartElement event) {
        String name = event.getName().getLocalPart().toLowerCase();

        if (this.parsingText.length() > 0) {
            this.processContents();
        }
        switch (name) {
            case "root": {
                break;
            }

            case "segment": {
                String segmentId = event.getAttributeByName(new QName("id")).getValue();
                this.segmentsOut.computeIfAbsent(segmentId, ignore -> new ArrayList<>());
                this.parsingTextState = this.parsingTextState.newSegment(segmentId);
                break;
            }

            case "black": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.BLACK);
                break;
            }
            case "darkblue":
            case "dark-blue":
            case "dark_blue": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.DARK_BLUE);
                break;
            }
            case "darkgreen":
            case "dark-green":
            case "dark_green": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.DARK_GREEN);
                break;
            }
            case "darkaqua":
            case "dark-aqua":
            case "dark_aqua": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.DARK_AQUA);
                break;
            }
            case "darkred":
            case "dark-red":
            case "dark_red": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.DARK_RED);
                break;
            }
            case "darkpurple":
            case "dark-purple":
            case "dark_purple": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.DARK_PURPLE);
                break;
            }
            case "gold": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.GOLD);
                break;
            }
            case "grey":
            case "gray": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.GRAY);
                break;
            }
            case "darkgrey":
            case "dark-grey":
            case "dark_grey":
            case "darkgray":
            case "dark-gray":
            case "dark_gray": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.DARK_GRAY);
                break;
            }
            case "blue": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.BLUE);
                break;
            }
            case "green": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.GREEN);
                break;
            }
            case "aqua": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.AQUA);
                break;
            }
            case "red": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.RED);
                break;
            }
            case "lightpurple":
            case "light-purple":
            case "light_purple":
            case "magenta": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.LIGHT_PURPLE);
                break;
            }
            case "yellow": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.YELLOW);
                break;
            }
            case "white": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setColor(TextColor.WHITE);
                break;
            }
            case "bold": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setBold(true);
                break;
            }
            case "italic": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setItalic(true);
                break;
            }
            case "underline": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setUnderlined(true);
                break;
            }
            case "strike":
            case "strikethrough": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setStrikethrough(true);
                break;
            }
            case "obfuscate": {
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setObfuscated(true);
                break;
            }

            case "click": {
                if (this.parsingTextState.getSegmentId() != null) {
                    throw new UnsupportedOperationException("Segments cannot contain click elements.");
                }

                String clickUri = event.getAttributeByName(new QName("uri")).getValue();
                Matcher uriMatcher = XMLTextContext.clickUriPattern.matcher(clickUri);
                if (!uriMatcher.matches()) {
                    throw new UnsupportedOperationException("The click URI has no protocol specified. (" + clickUri + ")");
                }

                this.parsingTextState = this.parsingTextState.newChild();
                String value = uriMatcher.group(2);
                switch (uriMatcher.group(1).toLowerCase()) {
                    case "file": {
                        this.parsingTextState.setClick(new Click.OpenFile(clickUri));
                        break;
                    }
                    case "run": {
                        this.parsingTextState.setClick(new Click.RunCommand(value));
                        break;
                    }
                    case "suggest": {
                        this.parsingTextState.setClick(new Click.Suggest(value));
                        break;
                    }
                    case "page": {
                        this.parsingTextState.setClick(new Click.ChangePage(value));
                        break;
                    }
                    default: {
                        this.parsingTextState.setClick(new Click.Url(clickUri));
                        break;
                    }
                }
                break;
            }
            case "insert": {
                if (this.parsingTextState.getSegmentId() != null) {
                    throw new UnsupportedOperationException("Segments cannot contain insert elements.");
                }

                String value = event.getAttributeByName(new QName("value")).getValue();
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setInsertion(value);
                break;
            }
            case "hover": {
                if (this.parsingTextState.getSegmentId() != null) {
                    throw new UnsupportedOperationException("Segments cannot contain hover elements.");
                }

                String type = event.getAttributeByName(new QName("type")).getValue();
                String value = event.getAttributeByName(new QName("value")).getValue();

                this.parsingTextState = this.parsingTextState.newChild();
                switch (type) {
                    case "text": {
                        this.parsingTextState.setHover(new Hover.Text(value));
                        break;
                    }
                    case "item": {
                        this.parsingTextState.setHover(new Hover.Item(value));
                        break;
                    }
                    case "entity": {
                        this.parsingTextState.setHover(new Hover.Entity(value));
                        break;
                    }
                    case "achievement": {
                        this.parsingTextState.setHover(new Hover.Achievement(value));
                        break;
                    }
                    case "segment": {
                        this.parsingTextState.setHover(new Hover.Segment(this.segmentsOut.get(value)));
                        break;
                    }
                    case "stat": {
                        this.parsingTextState.setHover(new Hover.Stat(value));
                    }
                }
                break;
            }
            case "selector": {
                throw new UnsupportedOperationException("The selector text component type is not possible in a bungee environment.");
            }
            case "score": {
                throw new UnsupportedOperationException("The score text component is not possible in a bungee environment.");
            }
            case "font": {
                this.parsingTextState = this.parsingTextState.newChild();
                String fontId = event.getAttributeByName(new QName("id")).getValue();
                this.parsingTextState = this.parsingTextState.newChild();
                this.parsingTextState.setFont(fontId);
                break;
            }
            default: {
                CecerMCLib.get(LoggerModule.class).getChannel(XMLTextContext.class).log("Unknown XMLText element: %s", name); // TODO: A better logging system
                break;
            }
        }
    }

    private void characters(Characters event) {
        this.parsingText.append(event.getData());
    }

    private void endElement(EndElement event) {
        this.processContents();

        String name = event.getName().getLocalPart().toLowerCase();
        if (!name.equals("root")) {
            this.parsingTextState = this.parsingTextState.getParent();
        }
    }
}