package net.avenwu.yoyogithub.bean;

import org.simpleframework.xml.Element;

public class Author {
    @Element
    public String name;
    @Element
    public String email;
    @Element
    public String uri;
}