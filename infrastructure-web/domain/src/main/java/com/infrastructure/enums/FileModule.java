package com.infrastructure.enums;

/**
 * 文件模块
 *
 * @author tyq
 * @data 2016/1/14
 */
public enum FileModule implements IDescription {

    Avatar("头像", "/avatar/"),
    Attachment("附件", "/attach/"),
    CloudDisk("云盘", "/cloud/"),
    Document("公文", "/document/"),
    Editor("编辑器", "/editor/"),
    Upload("默认", "/upload/");

    /**
     * 描述
     */
    private final String description;

    /**
     * 路径
     */
    private final String path;

    FileModule(String description, String path) {
        this.description = description;
        this.path = path;
    }

    /**
     * 根据名字获取类型
     *
     * @param name
     * @return
     */
    public static final FileModule get(String name) {
        if (name == null || "".equals(name))
            return FileModule.Upload;
        for (FileModule e : FileModule.values())
            if (e.name().toLowerCase().equals(name.toLowerCase()))
                return e;
        return FileModule.Upload;
    }

    /**
     * 获取描述信息
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 获取存储路径
     *
     * @return
     */
    public String getPath() {
        return path;
    }
}
