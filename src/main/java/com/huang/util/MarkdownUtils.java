package com.huang.util;


import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * MarkdownUtils格式转换工具类
 * @author guangtou
 * @create 2020--02--11--22:24
 */
public class MarkdownUtils {
    /**
     * markdown格式转换成HTML格式
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown){
        Parser parser = Parser.builder().build();
        Node document =parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtension(String markdown){
//        h标签生成id
        Set<Extension> headingAnchchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
//        转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                })
                .build();
        return renderer.render(document);
    }

    static  class CustomAttributeProvider implements AttributeProvider{
        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
//            改变a标签的target属性为_blank
            if (node instanceof Link){
                map.put("target","_blank");
            }
//
            if (node instanceof TableBlock){
                map.put("class","ui celled table");
            }
        }
    }

}
