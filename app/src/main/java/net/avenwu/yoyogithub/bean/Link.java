package net.avenwu.yoyogithub.bean;

import org.simpleframework.xml.Attribute;

public class Link {
    @Attribute
    public String type;
    @Attribute(required = false)
    String rel;
    @Attribute
    public String href;
}
