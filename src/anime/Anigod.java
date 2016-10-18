package anime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anigod {

    private static Document connect(String url) {

        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .referrer("http://sh.st/")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;

    }

    private static List<Item> list(String url) {

        Document doc = connect(url);

        List<Item> items = new LinkedList<>();

        Elements elements = doc.select(".table-image-container");
        elements.forEach(element -> {
            Item item = new Item();
            item.setTitle(element.attr("title"));
            item.setUrl(element.attr("abs:href"));
            item.setSrc(element.select(".lazy").first().attr("data-original"));
            items.add(item);
        });

        return items;

    }

    public static List<Item> lists(String url) {

        List<Item> items = new LinkedList<>();
        List<Item> tempItems;
        int page = 1;
        while((tempItems = list(url + "/" + page)).size() != 0) {
            items.addAll(tempItems);
            page++;
        }
        return items;

    }

    public static List<Item> search(String keyword) {

        String keywordEscaped = keyword.replace(" ", "%20");
        return list("https://anigod.com/animations/search/" + keywordEscaped);

    }
    
    public static List<Item> now() {
    	
    	// Main
        Document doc = connect("https://anigod.com/");
        List<Item> items = new LinkedList<>();
        Elements elements = doc.select(".index-image-container");
        elements.forEach(element -> {
            Item item = new Item();
            item.setTitle(element.attr("title"));
            item.setUrl(element.attr("abs:href"));
            item.setSrc(element.select(".index-image").attr("src"));
            items.add(item);
        });
        
        // Blacklist
        String[] blacklist = {"완결 애니메이션", "애니24 (폐쇄)", "게시판"};
        List<Item> blackitems = new LinkedList<>();
        for(String black : blacklist) {
        	items.forEach(item -> {
            	if(item.getTitle().equals(black)) {
            		blackitems.add(item);
            	}
            });
        }
        items.removeAll(blackitems);
        
        return items;
    }

    public static List<Item> all() {

    	List<Item> items = now();
        items.addAll(lists("https://anigod.com/animations/finale/title/asc")); // 완결
        return items;

    }

    public static Item video(String url) {

       Document doc = connect(url);
        
       Element element = doc.select("img[src]").first();
       String title = element.attr("alt");
       String src = element.attr("src");

        String html = doc.outerHtml();

        String videoID = "";
        Pattern pattern = Pattern.compile("var videoID = '(.*?)'");
        Matcher matcher = pattern.matcher(html);
        if(matcher.find()) {
            videoID = matcher.group(1);
        }

        String videoIDEscaped = null;
        try {
            videoIDEscaped = URLDecoder.decode(videoID, "UTF-8")
                    .replace("\\x", "%")
                    .replace("\\", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String _url = "https://anigod.com/video?id=" + videoIDEscaped + "&ts=" + System.currentTimeMillis();

        return new Item(title, _url, src);
        
    }

}