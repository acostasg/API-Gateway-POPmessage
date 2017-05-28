package api.infrastucture.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.inject.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class ConfigRepository implements api.domain.infrastructure.ConfigRepository {

    private static final String PATH_FILE_CONFIG = "config/api.xml";
    private Document parser;

    @Singleton
    public ConfigRepository() {
        if (null == this.parser) {
            try {
                File fXmlFile = new File(PATH_FILE_CONFIG);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                this.parser = dBuilder.parse(fXmlFile);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String get(String key) {
        return this.parser.getElementsByTagName(key).item(0).getTextContent();
    }
}
