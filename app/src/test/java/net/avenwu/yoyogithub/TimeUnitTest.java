package net.avenwu.yoyogithub;

import net.avenwu.yoyogithub.util.TimeUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TimeUnitTest {

    @Test
    public void testJoinTime() throws Exception {
        String time = TimeUtils.formatTimeASJoined("2012-04-08T00:58:02Z");
        assertEquals("Apr 8, 2012", time);
    }
}