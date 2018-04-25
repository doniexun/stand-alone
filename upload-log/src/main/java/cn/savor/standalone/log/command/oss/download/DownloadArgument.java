package cn.savor.standalone.log.command.oss.download;

import cn.savor.standalone.log.command.oss.upload.UploadArgument;
import net.lizhaoweb.common.util.argument.model.AbstractArgument;
import net.lizhaoweb.common.util.argument.model.IArgument;

/**
 * <h1> title </h1>
 * Created by Administrator on 2017-10-11 下午 4:50.
 */
public class DownloadArgument extends AbstractArgument {
//    public static final IArgument FilePath = new DownloadArgument("filePath", null, null);
    public static final IArgument LogCompressionPackageTo = new UploadArgument("downloadDir", null, null);
    public static final IArgument TempPath = new UploadArgument("tempDir", null, null);
    public static final IArgument BackupPath = new UploadArgument("backupDir", null, null);
    public static final IArgument OSSBucketName = new DownloadArgument("bucketName", null, null);
    public static final IArgument OSSKeyPrefix = new DownloadArgument("keyPrefix", null, null);
    public static final IArgument AreaUrl = new DownloadArgument("areaUrl", null, null);

    public DownloadArgument(String name, String nullValue, String[] nullArray) {
        super(name, nullValue, nullArray);
    }

}
