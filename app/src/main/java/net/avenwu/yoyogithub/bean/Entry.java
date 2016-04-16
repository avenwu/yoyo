package net.avenwu.yoyogithub.bean;

import org.simpleframework.xml.Element;

public class Entry {
    @Element
    public String id;
    @Element
    public String published;
    @Element
    public String updated;
    @Element
    public Link link;
    @Element
    public String title;
    @Element
    public Author author;
    @Element
    public Thumbnail thumbnail;
    @Element
    public String content;
}