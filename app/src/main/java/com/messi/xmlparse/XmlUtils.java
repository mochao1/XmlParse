package com.messi.xmlparse;

import android.util.Xml;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by Messi.mo on 2017/6/6.
 */

public class XmlUtils {
  public static List<Student> dom(InputStream inputStream) throws Exception {
    List<Student> list = new ArrayList<>();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(inputStream);
    NodeList stulist = document.getElementsByTagName("student");
    for (int i = 0; i < stulist.getLength(); i++) {
      Node stu = stulist.item(i);
      NodeList childNodes = stu.getChildNodes();
      Student student = new Student();
      for (int j = 0; j < childNodes.getLength(); j++) {
        Node childNode = childNodes.item(j);
        if ("name".equals(childNode.getNodeName())) {
          String name = childNode.getTextContent();
          student.setName(name);
          //获取name的属性
          NamedNodeMap nnm = childNode.getAttributes();
          //获取sex属性，由于只有一个属性，所以取0
          Node n = nnm.item(0);
          student.setSex(n.getTextContent());
        } else if ("nickName".equals(childNode.getNodeName())) {
          String nickName = childNode.getTextContent();
          student.setNickName(nickName);
        }
      }
      list.add(student);
    }
    return list;
  }

  public static List<Student> sax(InputStream inputStream) throws Exception {
    SAXParserFactory saxf = SAXParserFactory.newInstance();
    SAXParser saxp = saxf.newSAXParser();
    MyHandler myHandler = new MyHandler();
    saxp.parse(inputStream, myHandler);
    return myHandler.getList();
  }

  public static class MyHandler extends DefaultHandler {
    private List<Student> list;
    private Student student;
    private String tempString;

    @Override public void startDocument() throws SAXException {
      list = new ArrayList<>();
      super.startDocument();
    }

    @Override public void endDocument() throws SAXException {
      super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
      if ("student".equals(localName)) {
        student = new Student();
      } else if ("name".equals(localName)) {
        String sex = attributes.getValue("sex");
        student.setSex(sex);
      }
      super.startElement(uri, localName, qName, attributes);
    }

    @Override public void endElement(String uri, String localName, String qName)
        throws SAXException {
      if ("student".equals(localName)) {
        list.add(student);
      }
      else if ("name".equals(localName)) {
        student.setName(tempString);
      } else if ("nickName".equals(localName)) {
        student.setNickName(tempString);
      }

      super.endElement(uri, localName, qName);
    }

    @Override public void characters(char[] ch, int start, int length) throws SAXException {
      tempString = new String(ch, start, length);
      super.characters(ch, start, length);
    }

    public List<Student> getList() {
      return list;
    }
  }

  public static List<Student> pull(InputStream inputStream) throws Exception {
    List<Student> list = null;
    Student student = null;
    XmlPullParser parser = Xml.newPullParser();
    parser.setInput(inputStream, "utf-8");
    int type = parser.getEventType();
    while (type != XmlPullParser.END_DOCUMENT) {
      switch (type) {
        case XmlPullParser.START_DOCUMENT:

          list = new ArrayList<>();

          break;
        case XmlPullParser.START_TAG:
          if ("student".equals(parser.getName())) {
            student = new Student();
          } else if ("name".equals(parser.getName())) {
            String sex = parser.getAttributeValue(null, "sex");
            String name = parser.nextText();
            student.setName(name);
            student.setSex(sex);
          } else if ("nickName".equals(parser.getName())) {
            String nickName = parser.nextText();
            student.setNickName(nickName);
          }
          break;
        case XmlPullParser.END_TAG:
          if ("student".equals(parser.getName())) {
            list.add(student);
          }
          break;
      }
      type = parser.next();
    }
    return list;
  }
}
