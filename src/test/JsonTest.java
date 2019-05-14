package test;

import back.Parser;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static back.Parser.DATA_URL;

public class JsonTest {

    @Test
    public void getJson() throws IOException {
        JSONObject object = Parser.getJsonFromUrl(DATA_URL);

        Assert.assertNotNull(object);

        System.out.println(object.toJSONString());
    }

    @Test
    public void getRatesMap() throws IOException {
        JSONObject object = Parser.getJsonFromUrl(DATA_URL);

        Map<String, Double> map = Parser.getRates(object);

        Assert.assertNotNull(map);

        System.out.println(map.toString());
    }
}
