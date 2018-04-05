package cn.savor.standalone.log.createfile.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>数据模型 - 内容</h1>
 *
 * @author savor_wangzhan
 * @version 1.0.0.0.1
 * @notes Created on 2016年12月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    /**
     * 媒体id
     */
    private String id;

    /**
     * 媒体名称
     */
    private String name;

    /**
     * 媒体类型
     */
    private String type;

    /**
     * 媒体类型
     */
    private String cnaName;


    //重写equals方法只要cnaName相等，我们就认为对象两个相等
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item st = (Item) obj;
            return (cnaName.equals(st.cnaName));
        } else {
            return super.equals(obj);

        }
    }

    @Override
    public int hashCode() {
        return cnaName.hashCode();
    }
}
