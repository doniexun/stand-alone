package cn.savor.standalone.log.createfile;

import cn.savor.standalone.log.ICommand;
import cn.savor.standalone.log.createfile.bean.B_CreateArgument;
import cn.savor.standalone.log.createfile.bean.Item;
import cn.savor.standalone.log.createfile.service.CompareList;
import cn.savor.standalone.log.createfile.service.CreateFile;
import cn.savor.standalone.log.createfile.service.GetItemList;
import net.lizhaoweb.common.util.argument.ArgumentFactory;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2017/11/1.
 */
public class CommandCreateBoxFile implements ICommand {

    private static List<Item> playList;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws IOException {
        String filePath = ArgumentFactory.getParameterValue(B_CreateArgument.FilePath);
//        String filePath ="E:\\U";
        ArgumentFactory.printInputArgument(B_CreateArgument.FilePath, filePath, false);

        String hotelId = ArgumentFactory.getParameterValue(B_CreateArgument.HotelId);
//        String hotelId ="493";
        ArgumentFactory.printInputArgument(B_CreateArgument.HotelId, hotelId, false);

        String ossBucketName = ArgumentFactory.getParameterValue(B_CreateArgument.OSSBucketName);
//        String ossBucketName ="redian-produce";
        ArgumentFactory.printInputArgument(B_CreateArgument.OSSBucketName, ossBucketName, false);

        String ossKeyPrefix = ArgumentFactory.getParameterValue(B_CreateArgument.OSSKeyPrefixMedia);
//        String ossKeyPrefix ="media/stand_alone/";
        ArgumentFactory.printInputArgument(B_CreateArgument.OSSKeyPrefixMedia, ossKeyPrefix, false);

        String url = ArgumentFactory.getParameterValue(B_CreateArgument.ItemListUrl);
//        String url ="http://mobile.littlehotspot.com/small/FlashMemery/getMenuList?hotel_id";
        ArgumentFactory.printInputArgument(B_CreateArgument.ItemListUrl, url, false);

        //通过接口获取该酒楼信息和节目单
        JSONObject itemList = GetItemList.getItemList(hotelId, url);

        //比较节目单

        List<Item> menu_lists = new ArrayList<>();

        List<Item> last_menus = new ArrayList<>();
        Map hotel = null;
        Map menu = null;
        try {

            Map<String, Object> result = (Map<String, Object>) itemList.get("result");
            hotel = (Map) result.get("hotel_info");
            menu = (Map) result.get("menu_list");
            List<Map<String, Object>> menu_list = (List<Map<String, Object>>) menu.get("list");
            Map last = (Map) result.get("last_menu");
            List<Map<String, Object>> last_menu = (List<Map<String, Object>>) last.get("list");

            for (Map o : menu_list) {
                Item item = new Item();
                item.setId((String) o.get("id"));
                item.setName((String) o.get("name"));
                item.setType((String) o.get("type"));
                item.setCnaName((String) o.get("chinese_name"));
                menu_lists.add(item);
            }
            for (Map o : last_menu) {
                Item item = new Item();
                item.setId((String) o.get("media_id"));
                item.setName((String) o.get("name"));
                item.setType((String) o.get("type"));
                item.setCnaName((String) o.get("chinese_name"));
                last_menus.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(menu_lists);
            System.out.println(last_menus);
            System.out.println("节目单获取失败！请重试！");
        }

        //生成机顶盒media目录
        List<Item> adds = CompareList.compareList(last_menus, menu_lists);
        List<Item> removes = CompareList.compareList(menu_lists, last_menus);
        CreateFile.createFile(removes, adds, menu_lists, filePath, ossBucketName, ossKeyPrefix, (String) hotel.get("name"), (String) menu.get("menu_name"));

    }

    @Override
    public String getInformation() {
        return null;
    }
}
