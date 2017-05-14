package api.infrastucture.elasticSearch.queryDSL.mappers;

public class FilterTextWrapper {

    private String text;

    FilterTextWrapper(String text) {
        this.text = text;
    }

    private String filter() {
        return this.text.replaceAll("\n", "");
    }

    public static String Filter(String text) {

        return new FilterTextWrapper(text).filter();
    }
}
