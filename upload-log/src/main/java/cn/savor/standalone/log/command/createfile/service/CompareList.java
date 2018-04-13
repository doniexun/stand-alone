package cn.savor.standalone.log.command.createfile.service;


import cn.savor.standalone.log.command.createfile.bean.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2017/11/1.
 */
public class CompareList {

    public static List<Item> compareList(List<Item> menu_lists, List<Item> last_menus) {

        List<Item> different = getRemove(menu_lists, last_menus);
        return different;
    }

    private static List<Item> getRemove(List<Item> prelist, List<Item> curlist) {
        List<Item> diff = new ArrayList<Item>();
        Map<Item, Integer> map = new HashMap<Item, Integer>(curlist.size());
        for (Item stu : curlist) {
            map.put(stu, 1);
        }
        for (Item stu : prelist) {
            if (map.get(stu) != null) {
                map.put(stu, 2);
                continue;
            }
        }
        for (Map.Entry<Item, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }
}
