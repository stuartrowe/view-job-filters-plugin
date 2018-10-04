package hudson.views;

import hudson.model.TopLevelItem;
import org.junit.Test;

import java.util.List;

import static hudson.views.AbstractIncludeExcludeJobFilter.IncludeExcludeType.*;
import static hudson.views.test.JobMocker.freeStyleProject;
import static hudson.views.test.JobMocker.jobOfType;
import static hudson.views.test.JobType.FREE_STYLE_PROJECT;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AbstractIncludeExcludeJobFilterTest extends AbstractHudsonTest {

    private class TestIncludeExcludeJobFilter extends AbstractIncludeExcludeJobFilter {
        public TestIncludeExcludeJobFilter(String includeExcludeTypeString) {
            super(includeExcludeTypeString);
        }

        @Override
        public boolean matches(TopLevelItem item) {
            return item.getName().endsWith("-matched");
        }
    }

    @Test
    public void testIncludeMatched() {
        TestIncludeExcludeJobFilter filter = new TestIncludeExcludeJobFilter(includeMatched.name());
        List<TopLevelItem> all = asList(
            freeStyleProject().withName("0-matched").asItem(),
            freeStyleProject().withName("1-matched").asItem(),
            freeStyleProject().withName("2-matched").asItem(),
            freeStyleProject().withName("3-matched").asItem(),
            freeStyleProject().withName("4-unmatched").asItem(),
            freeStyleProject().withName("5-unmatched").asItem(),
            freeStyleProject().withName("6-unmatched").asItem(),
            freeStyleProject().withName("7-unmatched").asItem()
        );
        List<TopLevelItem> added = asList(
            all.get(0),
            all.get(1),
            all.get(5)
        );
        List<TopLevelItem> expected = asList(
            all.get(0),
            all.get(1),
            all.get(2),
            all.get(3),
            all.get(5)
        );

        List<TopLevelItem> filtered = filter.filter(added, all, null);
        assertThat(filtered, is(expected));
    }

    @Test
    public void testIncludeUnmatched() {
        TestIncludeExcludeJobFilter filter = new TestIncludeExcludeJobFilter(includeUnmatched.name());
        List<TopLevelItem> all = asList(
            freeStyleProject().withName("0-matched").asItem(),
            freeStyleProject().withName("1-matched").asItem(),
            freeStyleProject().withName("2-matched").asItem(),
            freeStyleProject().withName("3-matched").asItem(),
            freeStyleProject().withName("4-unmatched").asItem(),
            freeStyleProject().withName("5-unmatched").asItem(),
            freeStyleProject().withName("6-unmatched").asItem(),
            freeStyleProject().withName("7-unmatched").asItem()
        );
        List<TopLevelItem> added = asList(
            all.get(0),
            all.get(1),
            all.get(5)
        );
        List<TopLevelItem> expected = asList(
            all.get(0),
            all.get(1),
            all.get(4),
            all.get(5),
            all.get(6),
            all.get(7)
        );

        List<TopLevelItem> filtered = filter.filter(added, all, null);
        assertThat(filtered, is(expected));
    }

    @Test
    public void testExcludeMatched() {
        TestIncludeExcludeJobFilter filter = new TestIncludeExcludeJobFilter(excludeMatched.name());
        List<TopLevelItem> all = asList(
            freeStyleProject().withName("0-matched").asItem(),
            freeStyleProject().withName("1-matched").asItem(),
            freeStyleProject().withName("2-matched").asItem(),
            freeStyleProject().withName("3-matched").asItem(),
            freeStyleProject().withName("4-unmatched").asItem(),
            freeStyleProject().withName("5-unmatched").asItem(),
            freeStyleProject().withName("6-unmatched").asItem(),
            freeStyleProject().withName("7-unmatched").asItem()
        );
        List<TopLevelItem> added = asList(
            all.get(0),
            all.get(1),
            all.get(3),
            all.get(4),
            all.get(5),
            all.get(7)
        );
        List<TopLevelItem> expected = asList(
            all.get(4),
            all.get(5),
            all.get(7)
        );

        List<TopLevelItem> filtered = filter.filter(added, all, null);
        assertThat(filtered, is(expected));
    }

    @Test
    public void testExcludeUnmatched() {
        TestIncludeExcludeJobFilter filter = new TestIncludeExcludeJobFilter(excludeUnmatched.name());
        List<TopLevelItem> all = asList(
            freeStyleProject().withName("0-matched").asItem(),
            freeStyleProject().withName("1-matched").asItem(),
            freeStyleProject().withName("2-matched").asItem(),
            freeStyleProject().withName("3-matched").asItem(),
            freeStyleProject().withName("4-unmatched").asItem(),
            freeStyleProject().withName("5-unmatched").asItem(),
            freeStyleProject().withName("6-unmatched").asItem(),
            freeStyleProject().withName("7-unmatched").asItem()
        );
        List<TopLevelItem> added = asList(
            all.get(0),
            all.get(1),
            all.get(3),
            all.get(4),
            all.get(5),
            all.get(7)
        );
        List<TopLevelItem> expected = asList(
            all.get(0),
            all.get(1),
            all.get(3)
        );

        List<TopLevelItem> filtered = filter.filter(added, all, null);
        assertThat(filtered, is(expected));
    }


}
