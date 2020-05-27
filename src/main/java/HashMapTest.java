import java.util.HashMap;
import java.util.Map;

/**
 * Created by clm43897 on 2019/10/12.
 */
public class HashMapTest {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        System.out.println(map);
    }
}
