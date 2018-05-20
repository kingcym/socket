package com.example.socket.tcp;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/5/20 14:35
 */
@RestController
public class T {


    public static void main(String[] args) throws DocumentException {
        createXMl();
    }

    private static void createXMl() throws DocumentException {
        // 创建Document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element rss = document.addElement("rss");
        //为rss根节点添加属性
        rss.addAttribute("version", "2.0");
        // 创建title子节点
        Element title = rss.addElement("title");
        // 设置title节点的值
        title.setText("<上海移动互联网产业促进中心正式揭牌>");

        String documentStr = document.asXML();

        System.out.println(documentStr);


        Document document1 = DocumentHelper.parseText(documentStr);
        Element rootElement = document1.getRootElement();
        System.out.println(rootElement.getName());
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            System.out.println(element.getText());
        }
    }
}
