package cn.savor.standalone.log.download;

import net.lizhaoweb.common.util.argument.model.AbstractArgument;
import net.lizhaoweb.common.util.argument.model.IArgument;

/**
 * <h1> title </h1>
 * Created by Administrator on 2017-10-11 下午 4:50.
 */
public class B_LogDownloadArgument extends AbstractArgument {
    public static final IArgument FilePath = new B_LogDownloadArgument("filePath", null, null);
    public static final IArgument OSSBucketName = new B_LogDownloadArgument("bucketName", null, null);
    public static final IArgument OSSKeyPrefix = new B_LogDownloadArgument("keyPrefix", null, null);
    public static final IArgument AreaUrl = new B_LogDownloadArgument("areaUrl", null, null);

    public B_LogDownloadArgument(String name, String nullValue, String[] nullArray) {
        super(name, nullValue, nullArray);
    }

}
