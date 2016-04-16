package net.avenwu.yoyogithub.bean;
/*
    <media:thumbnail height="30" width="30" url="https://avatars1.githubusercontent.com/u/1622234?v=3&amp;s=30"/>
 */

import org.simpleframework.xml.Attribute;

/**
 * Created by aven on 4/16/16.
 */
public class Thumbnail {
    @Attribute(required = false)
    public int height;
    @Attribute(required = false)
    public int width;
    @Attribute
    public String url;
}
