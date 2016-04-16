package net.avenwu.yoyogithub.bean;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by aven on 4/15/16.
 */
@Root(name = "feed", strict = false)
public class XmlFeedTimeline {
    @Element
    public String title;
    @Element
    public String updated;
    @ElementList(inline = true)
    public List<Entry> list;
}
